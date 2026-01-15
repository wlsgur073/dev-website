<script setup lang="ts">
import { ref, watch, computed, watchEffect } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import DocsLayout from '@/layouts/DocsLayout.vue'
import DocsSidebar from '@/components/DocsSidebar.vue'
import DocsToc from '@/components/DocsToc.vue'
import MarkdownRenderer from '@/components/MarkdownRenderer.vue'
import { loadDocContent, getDocMeta, allDocs } from '@/content/docs'
import type { TocItem } from '@/utils/markdown'
import { useSeoMeta } from '@/composables/useSeoMeta'
import {
  ArrowLeftIcon,
  ArrowRightIcon,
} from '@heroicons/vue/24/outline'

const route = useRoute()
const router = useRouter()

const slug = computed(() => route.params.slug as string)
const content = ref<string | null>(null)
const isLoading = ref(true)
const toc = ref<TocItem[]>([])
const docMeta = computed(() => getDocMeta(slug.value))

// Dynamic SEO meta
watchEffect(() => {
  if (docMeta.value) {
    document.title = `${docMeta.value.title} | Documentation | Dev Website`
  }
})

useSeoMeta({
  description: 'Developer documentation and guides for Dev Website platform.',
  keywords: 'documentation, API, guide, tutorial, developer',
  ogType: 'article',
})

// Navigation
const currentIndex = computed(() => allDocs.findIndex(d => d.slug === slug.value))
const prevDoc = computed(() => currentIndex.value > 0 ? allDocs[currentIndex.value - 1] : null)
const nextDoc = computed(() => currentIndex.value < allDocs.length - 1 ? allDocs[currentIndex.value + 1] : null)

async function loadContent() {
  isLoading.value = true
  content.value = null
  toc.value = []

  const result = await loadDocContent(slug.value)

  if (result === null) {
    // Document not found, redirect to 404
    router.replace({ name: 'not-found' })
    return
  }

  content.value = result
  isLoading.value = false
}

function handleToc(items: TocItem[]) {
  toc.value = items
}

watch(slug, loadContent, { immediate: true })
</script>

<template>
  <DocsLayout>
    <template #sidebar>
      <DocsSidebar />
    </template>

    <template #toc>
      <DocsToc :items="toc" />
    </template>

    <article class="max-w-3xl">
      <!-- Breadcrumb -->
      <nav class="mb-6">
        <ol class="flex items-center gap-2 text-sm">
          <li>
            <RouterLink to="/docs" class="text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200">
              Docs
            </RouterLink>
          </li>
          <li class="text-gray-400">/</li>
          <li class="text-gray-900 dark:text-white font-medium">
            {{ docMeta?.title || slug }}
          </li>
        </ol>
      </nav>

      <!-- Content -->
      <div v-if="isLoading" class="animate-pulse space-y-4">
        <div class="h-10 bg-gray-200 dark:bg-gray-700 rounded w-2/3"></div>
        <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded"></div>
        <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-5/6"></div>
        <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-4/5"></div>
        <div class="h-32 bg-gray-200 dark:bg-gray-700 rounded mt-8"></div>
      </div>

      <MarkdownRenderer
        v-else-if="content"
        :content="content"
        @toc="handleToc"
      />

      <!-- Prev/Next navigation -->
      <nav
        v-if="!isLoading && content"
        class="mt-12 pt-8 border-t border-gray-200 dark:border-gray-700 flex justify-between gap-4"
      >
        <RouterLink
          v-if="prevDoc"
          :to="`/docs/${prevDoc.slug}`"
          class="flex-1 p-4 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors group"
        >
          <div class="flex items-center gap-2 text-sm text-gray-500 dark:text-gray-400 mb-1">
            <ArrowLeftIcon class="w-4 h-4" />
            Previous
          </div>
          <div class="font-medium text-gray-900 dark:text-white group-hover:text-blue-600 dark:group-hover:text-blue-400">
            {{ prevDoc.title }}
          </div>
        </RouterLink>
        <div v-else class="flex-1" />

        <RouterLink
          v-if="nextDoc"
          :to="`/docs/${nextDoc.slug}`"
          class="flex-1 p-4 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors group text-right"
        >
          <div class="flex items-center justify-end gap-2 text-sm text-gray-500 dark:text-gray-400 mb-1">
            Next
            <ArrowRightIcon class="w-4 h-4" />
          </div>
          <div class="font-medium text-gray-900 dark:text-white group-hover:text-blue-600 dark:group-hover:text-blue-400">
            {{ nextDoc.title }}
          </div>
        </RouterLink>
      </nav>
    </article>
  </DocsLayout>
</template>
