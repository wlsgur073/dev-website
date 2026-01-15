import { watchEffect, onUnmounted, ref } from 'vue'

export interface SeoMetaOptions {
  title?: string
  description?: string
  keywords?: string
  ogTitle?: string
  ogDescription?: string
  ogImage?: string
  ogUrl?: string
  ogType?: 'website' | 'article'
  twitterCard?: 'summary' | 'summary_large_image'
  twitterTitle?: string
  twitterDescription?: string
  twitterImage?: string
  canonical?: string
  noindex?: boolean
}

const SITE_NAME = 'Dev Website'
const DEFAULT_DESCRIPTION = 'A modern developer platform for building, deploying, and scaling your applications.'
const DEFAULT_OG_IMAGE = '/og-image.png'

// Store created meta tags for cleanup
const createdTags = ref<HTMLElement[]>([])

function getOrCreateMeta(name: string, isProperty = false): HTMLMetaElement {
  const attr = isProperty ? 'property' : 'name'
  let meta = document.querySelector(`meta[${attr}="${name}"]`) as HTMLMetaElement | null

  if (!meta) {
    meta = document.createElement('meta')
    meta.setAttribute(attr, name)
    document.head.appendChild(meta)
    createdTags.value.push(meta)
  }

  return meta
}

function getOrCreateLink(rel: string): HTMLLinkElement {
  let link = document.querySelector(`link[rel="${rel}"]`) as HTMLLinkElement | null

  if (!link) {
    link = document.createElement('link')
    link.setAttribute('rel', rel)
    document.head.appendChild(link)
    createdTags.value.push(link)
  }

  return link
}

export function useSeoMeta(options: SeoMetaOptions) {
  const cleanup = () => {
    // Remove dynamically created tags on unmount
    createdTags.value.forEach(tag => {
      if (tag.parentNode) {
        tag.parentNode.removeChild(tag)
      }
    })
    createdTags.value = []
  }

  watchEffect(() => {
    const {
      title,
      description = DEFAULT_DESCRIPTION,
      keywords,
      ogTitle,
      ogDescription,
      ogImage = DEFAULT_OG_IMAGE,
      ogUrl,
      ogType = 'website',
      twitterCard = 'summary_large_image',
      twitterTitle,
      twitterDescription,
      twitterImage,
      canonical,
      noindex = false,
    } = options

    // Title
    if (title) {
      document.title = `${title} | ${SITE_NAME}`
    }

    // Basic meta tags
    const descMeta = getOrCreateMeta('description')
    descMeta.content = description

    if (keywords) {
      const keywordsMeta = getOrCreateMeta('keywords')
      keywordsMeta.content = keywords
    }

    // Robots
    const robotsMeta = getOrCreateMeta('robots')
    robotsMeta.content = noindex ? 'noindex, nofollow' : 'index, follow'

    // Open Graph
    const ogTitleMeta = getOrCreateMeta('og:title', true)
    ogTitleMeta.content = ogTitle || title || SITE_NAME

    const ogDescMeta = getOrCreateMeta('og:description', true)
    ogDescMeta.content = ogDescription || description

    const ogImageMeta = getOrCreateMeta('og:image', true)
    ogImageMeta.content = ogImage

    const ogTypeMeta = getOrCreateMeta('og:type', true)
    ogTypeMeta.content = ogType

    const ogSiteNameMeta = getOrCreateMeta('og:site_name', true)
    ogSiteNameMeta.content = SITE_NAME

    if (ogUrl) {
      const ogUrlMeta = getOrCreateMeta('og:url', true)
      ogUrlMeta.content = ogUrl
    }

    // Twitter Card
    const twitterCardMeta = getOrCreateMeta('twitter:card')
    twitterCardMeta.content = twitterCard

    const twitterTitleMeta = getOrCreateMeta('twitter:title')
    twitterTitleMeta.content = twitterTitle || ogTitle || title || SITE_NAME

    const twitterDescMeta = getOrCreateMeta('twitter:description')
    twitterDescMeta.content = twitterDescription || ogDescription || description

    const twitterImageMeta = getOrCreateMeta('twitter:image')
    twitterImageMeta.content = twitterImage || ogImage

    // Canonical URL
    if (canonical) {
      const canonicalLink = getOrCreateLink('canonical')
      canonicalLink.href = canonical
    }
  })

  onUnmounted(cleanup)

  return { cleanup }
}

// Helper function for dynamic title updates
export function usePageTitle(title: string) {
  watchEffect(() => {
    document.title = `${title} | ${SITE_NAME}`
  })
}
