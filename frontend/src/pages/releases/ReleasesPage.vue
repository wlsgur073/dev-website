<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import DefaultLayout from '@/layouts/DefaultLayout.vue'
import { getReleases, type Release } from '@/api/releases'
import {
  RocketLaunchIcon,
  ChevronLeftIcon,
  ChevronRightIcon,
  TagIcon,
} from '@heroicons/vue/24/outline'

const route = useRoute()
const router = useRouter()

const releases = ref<Release[]>([])
const isLoading = ref(true)
const error = ref<string | null>(null)
const pagination = ref({
  page: 0,
  totalPages: 0,
  totalElements: 0,
})

const releaseTypes = ['all', 'major', 'minor', 'patch', 'hotfix'] as const
const selectedType = ref<string>('all')

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

async function loadReleases() {
  isLoading.value = true
  error.value = null

  try {
    const page = parseInt(route.query.page as string) || 0
    const type = selectedType.value === 'all' ? undefined : selectedType.value

    const response = await getReleases({ page, size: 10, type })
    releases.value = response.content
    pagination.value = {
      page: response.page,
      totalPages: response.totalPages,
      totalElements: response.totalElements,
    }
  } catch (e) {
    error.value = 'Failed to load releases'
  } finally {
    isLoading.value = false
  }
}

function changePage(page: number) {
  router.push({ query: { ...route.query, page: page.toString() } })
}

function changeType(type: string) {
  selectedType.value = type
  router.push({ query: { type: type === 'all' ? undefined : type, page: '0' } })
}

watch(() => route.query, loadReleases)

onMounted(() => {
  selectedType.value = (route.query.type as string) || 'all'
  loadReleases()
})
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-12">
      <!-- Header -->
      <div class="max-w-3xl mb-8">
        <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-4">
          Releases
        </h1>
        <p class="text-lg text-gray-600 dark:text-gray-400">
          Stay up to date with the latest product updates, new features, and improvements.
        </p>
      </div>

      <!-- Type filter -->
      <div class="flex flex-wrap gap-2 mb-8">
        <button
          v-for="type in releaseTypes"
          :key="type"
          type="button"
          :class="[
            'px-4 py-2 text-sm font-medium rounded-lg transition-colors capitalize',
            selectedType === type
              ? 'bg-blue-600 text-white'
              : 'bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-700'
          ]"
          @click="changeType(type)"
        >
          {{ type }}
        </button>
      </div>

      <!-- Loading state -->
      <div v-if="isLoading" class="space-y-4">
        <div v-for="i in 5" :key="i" class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6 animate-pulse">
          <div class="flex items-center gap-2 mb-3">
            <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-16"></div>
            <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-20"></div>
          </div>
          <div class="h-6 bg-gray-200 dark:bg-gray-700 rounded w-3/4 mb-2"></div>
          <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-full"></div>
        </div>
      </div>

      <!-- Error state -->
      <div v-else-if="error" class="text-center py-12">
        <RocketLaunchIcon class="w-16 h-16 mx-auto text-gray-400 mb-4" />
        <p class="text-gray-600 dark:text-gray-400">{{ error }}</p>
        <button
          type="button"
          class="mt-4 btn btn-primary"
          @click="loadReleases"
        >
          Try Again
        </button>
      </div>

      <!-- Empty state -->
      <div v-else-if="releases.length === 0" class="text-center py-12">
        <RocketLaunchIcon class="w-16 h-16 mx-auto text-gray-400 mb-4" />
        <h2 class="text-xl font-semibold text-gray-900 dark:text-white mb-2">
          No releases yet
        </h2>
        <p class="text-gray-600 dark:text-gray-400">
          Check back soon for product updates and new features.
        </p>
      </div>

      <!-- Releases list -->
      <div v-else class="space-y-4">
        <RouterLink
          v-for="release in releases"
          :key="release.id"
          :to="`/releases/${release.id}`"
          class="block bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6 hover:border-blue-500 dark:hover:border-blue-500 transition-colors"
        >
          <div class="flex items-start justify-between gap-4">
            <div class="flex-1 min-w-0">
              <div class="flex items-center gap-2 mb-2">
                <span :class="['text-xs font-medium px-2 py-0.5 rounded', getReleaseTypeColor(release.type)]">
                  {{ release.type }}
                </span>
                <span class="flex items-center gap-1 text-sm text-gray-500 dark:text-gray-400">
                  <TagIcon class="w-4 h-4" />
                  v{{ release.version }}
                </span>
              </div>
              <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">
                {{ release.title }}
              </h2>
              <p class="text-gray-600 dark:text-gray-400 line-clamp-2">
                {{ release.summary || release.content.substring(0, 150) }}
              </p>
            </div>
            <time class="text-sm text-gray-500 dark:text-gray-400 whitespace-nowrap">
              {{ formatDate(release.releaseDate) }}
            </time>
          </div>
        </RouterLink>
      </div>

      <!-- Pagination -->
      <nav
        v-if="pagination.totalPages > 1"
        class="mt-8 flex items-center justify-center gap-2"
      >
        <button
          type="button"
          :disabled="pagination.page === 0"
          class="p-2 rounded-lg border border-gray-200 dark:border-gray-700 hover:bg-gray-100 dark:hover:bg-gray-800 disabled:opacity-50 disabled:cursor-not-allowed"
          @click="changePage(pagination.page - 1)"
        >
          <ChevronLeftIcon class="w-5 h-5" />
        </button>

        <span class="px-4 py-2 text-sm text-gray-600 dark:text-gray-400">
          Page {{ pagination.page + 1 }} of {{ pagination.totalPages }}
        </span>

        <button
          type="button"
          :disabled="pagination.page >= pagination.totalPages - 1"
          class="p-2 rounded-lg border border-gray-200 dark:border-gray-700 hover:bg-gray-100 dark:hover:bg-gray-800 disabled:opacity-50 disabled:cursor-not-allowed"
          @click="changePage(pagination.page + 1)"
        >
          <ChevronRightIcon class="w-5 h-5" />
        </button>
      </nav>
    </div>
  </DefaultLayout>
</template>
