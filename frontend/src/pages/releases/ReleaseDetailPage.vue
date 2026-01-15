<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute } from 'vue-router'
import DefaultLayout from '@/layouts/DefaultLayout.vue'
import MarkdownRenderer from '@/components/MarkdownRenderer.vue'
import { getRelease, type Release } from '@/api/releases'
import {
  ArrowLeftIcon,
  RocketLaunchIcon,
  TagIcon,
} from '@heroicons/vue/24/outline'

const route = useRoute()

const release = ref<Release | null>(null)
const isLoading = ref(true)
const error = ref<string | null>(null)

const id = computed(() => parseInt(route.params.id as string))

function getReleaseTypeColor(type: Release['type']): string {
  const colors = {
    major: 'bg-purple-100 dark:bg-purple-900 text-purple-700 dark:text-purple-300',
    minor: 'bg-blue-100 dark:bg-blue-900 text-blue-700 dark:text-blue-300',
    patch: 'bg-green-100 dark:bg-green-900 text-green-700 dark:text-green-300',
    hotfix: 'bg-red-100 dark:bg-red-900 text-red-700 dark:text-red-300',
  }
  return colors[type] || colors.patch
}

function formatDate(dateString: string): string {
  return new Date(dateString).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  })
}

async function loadRelease() {
  isLoading.value = true
  error.value = null

  try {
    release.value = await getRelease(id.value)
  } catch (e) {
    error.value = 'Failed to load release'
  } finally {
    isLoading.value = false
  }
}

onMounted(loadRelease)
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-12">
      <!-- Back link -->
      <RouterLink
        to="/releases"
        class="inline-flex items-center gap-2 text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-white mb-8"
      >
        <ArrowLeftIcon class="w-4 h-4" />
        Back to Releases
      </RouterLink>

      <!-- Loading state -->
      <div v-if="isLoading" class="max-w-3xl animate-pulse">
        <div class="flex items-center gap-2 mb-4">
          <div class="h-6 bg-gray-200 dark:bg-gray-700 rounded w-16"></div>
          <div class="h-6 bg-gray-200 dark:bg-gray-700 rounded w-24"></div>
        </div>
        <div class="h-10 bg-gray-200 dark:bg-gray-700 rounded w-3/4 mb-4"></div>
        <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-32 mb-8"></div>
        <div class="space-y-3">
          <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded"></div>
          <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-5/6"></div>
          <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-4/5"></div>
        </div>
      </div>

      <!-- Error state -->
      <div v-else-if="error" class="text-center py-12">
        <RocketLaunchIcon class="w-16 h-16 mx-auto text-gray-400 mb-4" />
        <p class="text-gray-600 dark:text-gray-400 mb-4">{{ error }}</p>
        <div class="flex justify-center gap-4">
          <button
            type="button"
            class="btn btn-primary"
            @click="loadRelease"
          >
            Try Again
          </button>
          <RouterLink to="/releases" class="btn btn-secondary">
            Back to Releases
          </RouterLink>
        </div>
      </div>

      <!-- Content -->
      <article v-else-if="release" class="max-w-3xl">
        <header class="mb-8">
          <div class="flex items-center gap-3 mb-4">
            <span :class="['text-sm font-medium px-3 py-1 rounded', getReleaseTypeColor(release.type)]">
              {{ release.type }}
            </span>
            <span class="flex items-center gap-1 text-gray-500 dark:text-gray-400">
              <TagIcon class="w-5 h-5" />
              v{{ release.version }}
            </span>
          </div>

          <h1 class="text-3xl md:text-4xl font-bold text-gray-900 dark:text-white mb-4">
            {{ release.title }}
          </h1>

          <time class="text-gray-500 dark:text-gray-400">
            Released on {{ formatDate(release.releaseDate) }}
          </time>
        </header>

        <div class="prose prose-gray dark:prose-invert max-w-none">
          <MarkdownRenderer :content="release.content" />
        </div>
      </article>
    </div>
  </DefaultLayout>
</template>
