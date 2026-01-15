<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import DefaultLayout from '@/layouts/DefaultLayout.vue'
import { allDocs } from '@/content/docs'
import {
  MagnifyingGlassIcon,
  DocumentTextIcon,
  MegaphoneIcon,
  RocketLaunchIcon,
} from '@heroicons/vue/24/outline'

const route = useRoute()
const router = useRouter()

const searchQuery = ref('')

// Initialize from URL query
if (route.query.q) {
  searchQuery.value = route.query.q as string
}

// Search result types
interface SearchResult {
  type: 'doc' | 'announcement' | 'release'
  title: string
  description: string
  url: string
  icon: typeof DocumentTextIcon
}

// Client-side search through docs
const searchResults = computed<SearchResult[]>(() => {
  if (!searchQuery.value.trim()) {
    return []
  }

  const query = searchQuery.value.toLowerCase()
  const results: SearchResult[] = []

  // Search docs
  for (const doc of allDocs) {
    if (
      doc.title.toLowerCase().includes(query) ||
      doc.description.toLowerCase().includes(query) ||
      doc.slug.toLowerCase().includes(query)
    ) {
      results.push({
        type: 'doc',
        title: doc.title,
        description: doc.description,
        url: `/docs/${doc.slug}`,
        icon: DocumentTextIcon,
      })
    }
  }

  return results
})

// Popular searches for when there's no query
const popularSearches = [
  'quickstart',
  'authentication',
  'api',
  'rate limits',
  'errors',
]

function search(query: string) {
  searchQuery.value = query
  router.replace({ query: { q: query || undefined } })
}

function handleSubmit() {
  router.replace({ query: { q: searchQuery.value || undefined } })
}

function getResultTypeLabel(type: SearchResult['type']): string {
  const labels = {
    doc: 'Documentation',
    announcement: 'Announcement',
    release: 'Release',
  }
  return labels[type]
}

function getResultTypeColor(type: SearchResult['type']): string {
  const colors = {
    doc: 'bg-blue-100 dark:bg-blue-900 text-blue-700 dark:text-blue-300',
    announcement: 'bg-yellow-100 dark:bg-yellow-900 text-yellow-700 dark:text-yellow-300',
    release: 'bg-purple-100 dark:bg-purple-900 text-purple-700 dark:text-purple-300',
  }
  return colors[type]
}

// Update URL when query changes
watch(searchQuery, (value) => {
  router.replace({ query: { q: value || undefined } })
})
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-12">
      <div class="max-w-2xl mx-auto">
        <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-8 text-center">
          Search
        </h1>

        <!-- Search input -->
        <form @submit.prevent="handleSubmit" class="relative">
          <MagnifyingGlassIcon class="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
          <input
            v-model="searchQuery"
            type="search"
            placeholder="Search documentation, announcements, releases..."
            class="w-full pl-12 pr-4 py-4 bg-white dark:bg-gray-800 border border-gray-300 dark:border-gray-700 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-gray-900 dark:text-white placeholder-gray-500 text-lg"
            autofocus
          />
        </form>

        <!-- Popular searches -->
        <div v-if="!searchQuery" class="mt-6">
          <p class="text-sm text-gray-500 dark:text-gray-400 mb-3">Popular searches:</p>
          <div class="flex flex-wrap gap-2">
            <button
              v-for="term in popularSearches"
              :key="term"
              type="button"
              class="px-3 py-1.5 text-sm bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700 transition-colors"
              @click="search(term)"
            >
              {{ term }}
            </button>
          </div>
        </div>

        <!-- Search results -->
        <div v-if="searchQuery" class="mt-8">
          <div v-if="searchResults.length > 0">
            <p class="text-sm text-gray-500 dark:text-gray-400 mb-4">
              {{ searchResults.length }} result{{ searchResults.length !== 1 ? 's' : '' }} for "{{ searchQuery }}"
            </p>

            <div class="space-y-3">
              <RouterLink
                v-for="result in searchResults"
                :key="result.url"
                :to="result.url"
                class="block p-4 bg-white dark:bg-gray-800 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors"
              >
                <div class="flex items-start gap-3">
                  <component
                    :is="result.icon"
                    class="w-5 h-5 text-gray-400 flex-shrink-0 mt-0.5"
                  />
                  <div class="flex-1 min-w-0">
                    <div class="flex items-center gap-2 mb-1">
                      <span :class="['text-xs font-medium px-2 py-0.5 rounded', getResultTypeColor(result.type)]">
                        {{ getResultTypeLabel(result.type) }}
                      </span>
                    </div>
                    <h3 class="font-medium text-gray-900 dark:text-white">
                      {{ result.title }}
                    </h3>
                    <p class="text-sm text-gray-600 dark:text-gray-400 mt-1 line-clamp-2">
                      {{ result.description }}
                    </p>
                  </div>
                </div>
              </RouterLink>
            </div>
          </div>

          <!-- No results -->
          <div v-else class="text-center py-12">
            <MagnifyingGlassIcon class="w-16 h-16 mx-auto text-gray-400 mb-4" />
            <h2 class="text-xl font-semibold text-gray-900 dark:text-white mb-2">
              No results found
            </h2>
            <p class="text-gray-600 dark:text-gray-400 mb-4">
              We couldn't find anything matching "{{ searchQuery }}"
            </p>
            <p class="text-sm text-gray-500 dark:text-gray-400">
              Try searching for something else or browse our
              <RouterLink to="/docs" class="text-blue-600 dark:text-blue-400 hover:underline">
                documentation
              </RouterLink>
            </p>
          </div>
        </div>

        <!-- Quick links when no search -->
        <div v-else class="mt-12">
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">
            Browse by category
          </h2>
          <div class="grid gap-4">
            <RouterLink
              to="/docs"
              class="flex items-center gap-4 p-4 bg-white dark:bg-gray-800 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors"
            >
              <div class="w-10 h-10 bg-blue-100 dark:bg-blue-900 rounded-lg flex items-center justify-center">
                <DocumentTextIcon class="w-5 h-5 text-blue-600 dark:text-blue-400" />
              </div>
              <div>
                <h3 class="font-medium text-gray-900 dark:text-white">Documentation</h3>
                <p class="text-sm text-gray-600 dark:text-gray-400">Guides, tutorials, and references</p>
              </div>
            </RouterLink>

            <RouterLink
              to="/announcements"
              class="flex items-center gap-4 p-4 bg-white dark:bg-gray-800 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors"
            >
              <div class="w-10 h-10 bg-yellow-100 dark:bg-yellow-900 rounded-lg flex items-center justify-center">
                <MegaphoneIcon class="w-5 h-5 text-yellow-600 dark:text-yellow-400" />
              </div>
              <div>
                <h3 class="font-medium text-gray-900 dark:text-white">Announcements</h3>
                <p class="text-sm text-gray-600 dark:text-gray-400">Updates and news from our team</p>
              </div>
            </RouterLink>

            <RouterLink
              to="/releases"
              class="flex items-center gap-4 p-4 bg-white dark:bg-gray-800 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors"
            >
              <div class="w-10 h-10 bg-purple-100 dark:bg-purple-900 rounded-lg flex items-center justify-center">
                <RocketLaunchIcon class="w-5 h-5 text-purple-600 dark:text-purple-400" />
              </div>
              <div>
                <h3 class="font-medium text-gray-900 dark:text-white">Releases</h3>
                <p class="text-sm text-gray-600 dark:text-gray-400">Product updates and changelogs</p>
              </div>
            </RouterLink>
          </div>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
