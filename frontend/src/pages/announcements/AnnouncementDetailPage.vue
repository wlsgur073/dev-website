<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute } from 'vue-router'
import DefaultLayout from '@/layouts/DefaultLayout.vue'
import MarkdownRenderer from '@/components/MarkdownRenderer.vue'
import { getAnnouncement, type Announcement } from '@/api/announcements'
import {
  ArrowLeftIcon,
  MegaphoneIcon,
} from '@heroicons/vue/24/outline'

const route = useRoute()

const announcement = ref<Announcement | null>(null)
const isLoading = ref(true)
const error = ref<string | null>(null)

const id = computed(() => parseInt(route.params.id as string))

function getCategoryColor(category: Announcement['category']): string {
  const colors = {
    general: 'bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300',
    update: 'bg-blue-100 dark:bg-blue-900 text-blue-700 dark:text-blue-300',
    maintenance: 'bg-yellow-100 dark:bg-yellow-900 text-yellow-700 dark:text-yellow-300',
    security: 'bg-red-100 dark:bg-red-900 text-red-700 dark:text-red-300',
  }
  return colors[category] || colors.general
}

function formatDate(dateString: string): string {
  return new Date(dateString).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  })
}

async function loadAnnouncement() {
  isLoading.value = true
  error.value = null

  try {
    announcement.value = await getAnnouncement(id.value)
  } catch (e) {
    error.value = 'Failed to load announcement'
  } finally {
    isLoading.value = false
  }
}

onMounted(loadAnnouncement)
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-12">
      <!-- Back link -->
      <RouterLink
        to="/announcements"
        class="inline-flex items-center gap-2 text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-white mb-8"
      >
        <ArrowLeftIcon class="w-4 h-4" />
        Back to Announcements
      </RouterLink>

      <!-- Loading state -->
      <div v-if="isLoading" class="max-w-3xl animate-pulse">
        <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-20 mb-4"></div>
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
        <MegaphoneIcon class="w-16 h-16 mx-auto text-gray-400 mb-4" />
        <p class="text-gray-600 dark:text-gray-400 mb-4">{{ error }}</p>
        <div class="flex justify-center gap-4">
          <button
            type="button"
            class="btn btn-primary"
            @click="loadAnnouncement"
          >
            Try Again
          </button>
          <RouterLink to="/announcements" class="btn btn-secondary">
            Back to Announcements
          </RouterLink>
        </div>
      </div>

      <!-- Content -->
      <article v-else-if="announcement" class="max-w-3xl">
        <header class="mb-8">
          <div class="flex items-center gap-2 mb-4">
            <span :class="['text-sm font-medium px-3 py-1 rounded', getCategoryColor(announcement.category)]">
              {{ announcement.category }}
            </span>
            <span v-if="announcement.pinned" class="text-sm font-medium px-3 py-1 rounded bg-blue-100 dark:bg-blue-900 text-blue-700 dark:text-blue-300">
              Pinned
            </span>
          </div>

          <h1 class="text-3xl md:text-4xl font-bold text-gray-900 dark:text-white mb-4">
            {{ announcement.title }}
          </h1>

          <time class="text-gray-500 dark:text-gray-400">
            {{ formatDate(announcement.createdAt) }}
          </time>
        </header>

        <div class="prose prose-gray dark:prose-invert max-w-none">
          <MarkdownRenderer :content="announcement.content" />
        </div>
      </article>
    </div>
  </DefaultLayout>
</template>
