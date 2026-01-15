<script setup lang="ts">
import { ref, onMounted } from 'vue'
import DefaultLayout from '@/layouts/DefaultLayout.vue'
import { getLatestAnnouncements, type Announcement } from '@/api/announcements'
import { getLatestReleases, type Release } from '@/api/releases'
import {
  RocketLaunchIcon,
  KeyIcon,
  CodeBracketIcon,
  DocumentTextIcon,
  ShieldCheckIcon,
  CommandLineIcon,
  QuestionMarkCircleIcon,
} from '@heroicons/vue/24/outline'

const announcements = ref<Announcement[]>([])
const releases = ref<Release[]>([])
const isLoadingAnnouncements = ref(true)
const isLoadingReleases = ref(true)

const popularDocs = [
  { title: 'Quickstart Guide', description: 'Get started in 5 minutes', slug: 'quickstart', icon: RocketLaunchIcon },
  { title: 'Authentication', description: 'Learn about API authentication', slug: 'authentication', icon: ShieldCheckIcon },
  { title: 'API Usage', description: 'Complete API reference', slug: 'api-usage', icon: CommandLineIcon },
  { title: 'FAQ', description: 'Frequently asked questions', slug: 'faq', icon: QuestionMarkCircleIcon },
]

function formatDate(dateString: string): string {
  return new Date(dateString).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
  })
}

function getCategoryColor(category: Announcement['category']): string {
  const colors = {
    general: 'bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300',
    update: 'bg-blue-100 dark:bg-blue-900 text-blue-700 dark:text-blue-300',
    maintenance: 'bg-yellow-100 dark:bg-yellow-900 text-yellow-700 dark:text-yellow-300',
    security: 'bg-red-100 dark:bg-red-900 text-red-700 dark:text-red-300',
  }
  return colors[category] || colors.general
}

function getReleaseTypeColor(type: Release['type']): string {
  const colors = {
    major: 'bg-purple-100 dark:bg-purple-900 text-purple-700 dark:text-purple-300',
    minor: 'bg-blue-100 dark:bg-blue-900 text-blue-700 dark:text-blue-300',
    patch: 'bg-green-100 dark:bg-green-900 text-green-700 dark:text-green-300',
    hotfix: 'bg-red-100 dark:bg-red-900 text-red-700 dark:text-red-300',
  }
  return colors[type] || colors.patch
}

onMounted(async () => {
  // Load announcements
  try {
    announcements.value = await getLatestAnnouncements(3)
  } catch {
    // API not available, show placeholder
  } finally {
    isLoadingAnnouncements.value = false
  }

  // Load releases
  try {
    releases.value = await getLatestReleases(3)
  } catch {
    // API not available, show placeholder
  } finally {
    isLoadingReleases.value = false
  }
})
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-8">
      <!-- Hero Section -->
      <section class="text-center py-16 md:py-24">
        <h1 class="text-4xl md:text-6xl font-bold text-gray-900 dark:text-white mb-6">
          Build Amazing Products
        </h1>
        <p class="text-xl text-gray-600 dark:text-gray-400 mb-8 max-w-2xl mx-auto">
          Powerful APIs, comprehensive documentation, and developer-first tools to help you build faster.
        </p>
        <div class="flex flex-wrap justify-center gap-4">
          <RouterLink to="/docs/quickstart" class="btn btn-primary">
            Quickstart
          </RouterLink>
          <RouterLink to="/docs" class="btn btn-secondary">
            Documentation
          </RouterLink>
          <RouterLink to="/console" class="btn btn-secondary">
            Console
          </RouterLink>
        </div>
      </section>

      <!-- Quickstart Cards -->
      <section class="py-16">
        <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-8 text-center">
          Get Started in Minutes
        </h2>
        <div class="grid md:grid-cols-3 gap-6">
          <div class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700">
            <div class="w-12 h-12 bg-blue-100 dark:bg-blue-900 rounded-lg flex items-center justify-center mb-4">
              <span class="text-2xl font-bold text-blue-600 dark:text-blue-400">1</span>
            </div>
            <h3 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Create Account</h3>
            <p class="text-gray-600 dark:text-gray-400">Sign up for free and get your API key instantly.</p>
          </div>
          <div class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700">
            <div class="w-12 h-12 bg-blue-100 dark:bg-blue-900 rounded-lg flex items-center justify-center mb-4">
              <KeyIcon class="w-6 h-6 text-blue-600 dark:text-blue-400" />
            </div>
            <h3 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Get API Key</h3>
            <p class="text-gray-600 dark:text-gray-400">Generate your API key from the console dashboard.</p>
          </div>
          <div class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700">
            <div class="w-12 h-12 bg-blue-100 dark:bg-blue-900 rounded-lg flex items-center justify-center mb-4">
              <CodeBracketIcon class="w-6 h-6 text-blue-600 dark:text-blue-400" />
            </div>
            <h3 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Start Building</h3>
            <p class="text-gray-600 dark:text-gray-400">Use our SDKs and APIs to build your application.</p>
          </div>
        </div>
      </section>

      <!-- Popular Docs -->
      <section class="py-16">
        <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-8 text-center">
          Popular Documentation
        </h2>
        <div class="grid md:grid-cols-2 lg:grid-cols-4 gap-4">
          <RouterLink
            v-for="doc in popularDocs"
            :key="doc.slug"
            :to="`/docs/${doc.slug}`"
            class="p-4 bg-white dark:bg-gray-800 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors group"
          >
            <div class="flex items-start gap-3">
              <component
                :is="doc.icon"
                class="w-5 h-5 text-gray-400 group-hover:text-blue-500 transition-colors flex-shrink-0 mt-0.5"
              />
              <div>
                <h3 class="font-medium text-gray-900 dark:text-white group-hover:text-blue-600 dark:group-hover:text-blue-400">
                  {{ doc.title }}
                </h3>
                <p class="text-sm text-gray-600 dark:text-gray-400 mt-1">{{ doc.description }}</p>
              </div>
            </div>
          </RouterLink>
        </div>
      </section>

      <!-- Latest Releases -->
      <section class="py-16">
        <div class="flex items-center justify-between mb-8">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white">
            Latest Releases
          </h2>
          <RouterLink to="/releases" class="text-blue-600 dark:text-blue-400 hover:underline">
            View all →
          </RouterLink>
        </div>
        <div class="grid md:grid-cols-3 gap-6">
          <template v-if="isLoadingReleases">
            <div v-for="i in 3" :key="i" class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 animate-pulse">
              <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-20 mb-3"></div>
              <div class="h-6 bg-gray-200 dark:bg-gray-700 rounded w-3/4 mb-2"></div>
              <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-full"></div>
            </div>
          </template>
          <template v-else-if="releases.length > 0">
            <RouterLink
              v-for="release in releases"
              :key="release.id"
              :to="`/releases/${release.id}`"
              class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors"
            >
              <div class="flex items-center gap-2 mb-2">
                <span :class="['text-xs font-medium px-2 py-0.5 rounded', getReleaseTypeColor(release.type)]">
                  {{ release.type }}
                </span>
                <span class="text-sm text-gray-500 dark:text-gray-400">
                  v{{ release.version }}
                </span>
              </div>
              <h3 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">
                {{ release.title }}
              </h3>
              <p class="text-sm text-gray-600 dark:text-gray-400 line-clamp-2">
                {{ release.summary || release.content.substring(0, 100) }}
              </p>
              <p class="text-xs text-gray-500 dark:text-gray-400 mt-3">
                {{ formatDate(release.releaseDate) }}
              </p>
            </RouterLink>
          </template>
          <template v-else>
            <div class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 col-span-full text-center">
              <DocumentTextIcon class="w-12 h-12 mx-auto text-gray-400 mb-3" />
              <p class="text-gray-600 dark:text-gray-400">No releases yet. Check back soon!</p>
            </div>
          </template>
        </div>
      </section>

      <!-- Latest Announcements -->
      <section class="py-16">
        <div class="flex items-center justify-between mb-8">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white">
            Announcements
          </h2>
          <RouterLink to="/announcements" class="text-blue-600 dark:text-blue-400 hover:underline">
            View all →
          </RouterLink>
        </div>
        <div class="grid md:grid-cols-3 gap-6">
          <template v-if="isLoadingAnnouncements">
            <div v-for="i in 3" :key="i" class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 animate-pulse">
              <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-20 mb-3"></div>
              <div class="h-6 bg-gray-200 dark:bg-gray-700 rounded w-3/4 mb-2"></div>
              <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-full"></div>
            </div>
          </template>
          <template v-else-if="announcements.length > 0">
            <RouterLink
              v-for="announcement in announcements"
              :key="announcement.id"
              :to="`/announcements/${announcement.id}`"
              class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors"
            >
              <span :class="['text-xs font-medium px-2 py-0.5 rounded', getCategoryColor(announcement.category)]">
                {{ announcement.category }}
              </span>
              <h3 class="text-lg font-semibold text-gray-900 dark:text-white mt-2 mb-2">
                {{ announcement.title }}
              </h3>
              <p class="text-sm text-gray-600 dark:text-gray-400 line-clamp-2">
                {{ announcement.summary || announcement.content.substring(0, 100) }}
              </p>
              <p class="text-xs text-gray-500 dark:text-gray-400 mt-3">
                {{ formatDate(announcement.createdAt) }}
              </p>
            </RouterLink>
          </template>
          <template v-else>
            <div class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 col-span-full text-center">
              <DocumentTextIcon class="w-12 h-12 mx-auto text-gray-400 mb-3" />
              <p class="text-gray-600 dark:text-gray-400">No announcements yet. Check back soon!</p>
            </div>
          </template>
        </div>
      </section>

      <!-- Pricing CTA -->
      <section class="py-16">
        <div class="bg-gradient-to-r from-blue-600 to-indigo-600 rounded-2xl p-8 md:p-12 text-center text-white">
          <h2 class="text-3xl font-bold mb-4">Ready to get started?</h2>
          <p class="text-blue-100 mb-8 max-w-xl mx-auto">
            Choose the plan that works best for you. Start free and scale as you grow.
          </p>
          <RouterLink to="/pricing" class="inline-flex btn bg-white text-blue-600 hover:bg-blue-50">
            View Pricing
          </RouterLink>
        </div>
      </section>

      <!-- Community CTA -->
      <section class="py-16 text-center">
        <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-4">
          Join Our Community
        </h2>
        <p class="text-gray-600 dark:text-gray-400 mb-8 max-w-xl mx-auto">
          Connect with other developers, share your projects, and get help from the community.
        </p>
        <RouterLink to="/community" class="btn btn-secondary">
          Join Community
        </RouterLink>
      </section>
    </div>
  </DefaultLayout>
</template>
