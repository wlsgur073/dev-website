// Document metadata and sidebar structure
export interface DocMeta {
  slug: string
  title: string
  description: string
  order: number
}

export interface DocSection {
  title: string
  items: DocMeta[]
}

// Sidebar structure
export const docsSidebar: DocSection[] = [
  {
    title: 'Getting Started',
    items: [
      {
        slug: 'quickstart',
        title: 'Quickstart Guide',
        description: 'Get started with Dev Website API in minutes',
        order: 1,
      },
      {
        slug: 'authentication',
        title: 'Authentication',
        description: 'Learn how to authenticate API requests',
        order: 2,
      },
    ],
  },
  {
    title: 'Core Concepts',
    items: [
      {
        slug: 'api-usage',
        title: 'API Usage',
        description: 'Complete guide to using the API',
        order: 3,
      },
      {
        slug: 'rate-limits',
        title: 'Rate Limits',
        description: 'Understanding and handling rate limits',
        order: 4,
      },
      {
        slug: 'errors',
        title: 'Error Handling',
        description: 'Guide to handling API errors',
        order: 5,
      },
    ],
  },
  {
    title: 'Resources',
    items: [
      {
        slug: 'faq',
        title: 'FAQ',
        description: 'Frequently asked questions',
        order: 6,
      },
    ],
  },
]

// Flat list of all docs for search
export const allDocs: DocMeta[] = docsSidebar.flatMap(section => section.items)

// Get doc meta by slug
export function getDocMeta(slug: string): DocMeta | undefined {
  return allDocs.find(doc => doc.slug === slug)
}

// Document content loader
const docModules = import.meta.glob<string>('./*.md', { query: '?raw', import: 'default' })

export async function loadDocContent(slug: string): Promise<string | null> {
  const path = `./${slug}.md`
  const loader = docModules[path]

  if (!loader) {
    return null
  }

  try {
    return await loader()
  } catch {
    return null
  }
}
