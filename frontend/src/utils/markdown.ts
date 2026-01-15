import MarkdownIt from 'markdown-it'
import { createHighlighter, type Highlighter, type BundledLanguage } from 'shiki'

let highlighter: Highlighter | null = null

// Languages to support
const supportedLanguages: BundledLanguage[] = [
  'javascript',
  'typescript',
  'json',
  'bash',
  'shell',
  'html',
  'css',
  'python',
  'java',
  'go',
  'rust',
  'sql',
  'yaml',
  'markdown',
  'vue',
  'jsx',
  'tsx',
]

export async function initHighlighter(): Promise<void> {
  if (highlighter) return

  highlighter = await createHighlighter({
    themes: ['github-light', 'github-dark'],
    langs: supportedLanguages,
  })
}

function escapeHtml(str: string): string {
  return str
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
}

export function createMarkdownRenderer(): MarkdownIt {
  const md: MarkdownIt = new MarkdownIt({
    html: false, // Disable HTML for security
    linkify: true,
    typographer: true,
    breaks: false,
    highlight: (code: string, lang: string): string => {
      if (!highlighter) {
        return `<pre><code class="language-${lang}">${escapeHtml(code)}</code></pre>`
      }

      const language = supportedLanguages.includes(lang as BundledLanguage)
        ? (lang as BundledLanguage)
        : 'text'

      try {
        return highlighter.codeToHtml(code, {
          lang: language,
          themes: {
            light: 'github-light',
            dark: 'github-dark',
          },
        })
      } catch {
        return `<pre><code class="language-${lang}">${escapeHtml(code)}</code></pre>`
      }
    },
  })

  // Add target="_blank" to external links
  const defaultRender = md.renderer.rules.link_open

  md.renderer.rules.link_open = (tokens, idx, options, env, self) => {
    const href = tokens[idx].attrGet('href')
    if (href && (href.startsWith('http://') || href.startsWith('https://'))) {
      tokens[idx].attrSet('target', '_blank')
      tokens[idx].attrSet('rel', 'noopener noreferrer')
    }
    if (defaultRender) {
      return defaultRender(tokens, idx, options, env, self)
    }
    return self.renderToken(tokens, idx, options)
  }

  return md
}

export interface TocItem {
  id: string
  text: string
  level: number
}

export function extractToc(html: string): TocItem[] {
  const toc: TocItem[] = []
  const regex = /<h([2-4])[^>]*id="([^"]*)"[^>]*>([^<]*)<\/h[2-4]>/g
  let match

  while ((match = regex.exec(html)) !== null) {
    toc.push({
      level: parseInt(match[1]),
      id: match[2],
      text: match[3].trim(),
    })
  }

  return toc
}

export function addHeadingIds(md: MarkdownIt): MarkdownIt {
  const originalHeadingOpen = md.renderer.rules.heading_open

  md.renderer.rules.heading_open = (tokens, idx, options, env, self) => {
    const token = tokens[idx]
    const nextToken = tokens[idx + 1]

    if (nextToken && nextToken.type === 'inline' && nextToken.content) {
      const id = nextToken.content
        .toLowerCase()
        .replace(/[^a-z0-9가-힣]+/g, '-')
        .replace(/^-|-$/g, '')

      token.attrSet('id', id)
    }

    if (originalHeadingOpen) {
      return originalHeadingOpen(tokens, idx, options, env, self)
    }
    return self.renderToken(tokens, idx, options)
  }

  return md
}

let mdInstance: MarkdownIt | null = null

export async function renderMarkdown(content: string): Promise<{ html: string; toc: TocItem[] }> {
  await initHighlighter()

  if (!mdInstance) {
    mdInstance = createMarkdownRenderer()
    addHeadingIds(mdInstance)
  }

  const html = mdInstance.render(content)
  const toc = extractToc(html)

  return { html, toc }
}
