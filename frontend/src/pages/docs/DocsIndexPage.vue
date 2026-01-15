<script setup lang="ts">
import DocsLayout from '@/layouts/DocsLayout.vue'
import DocsSidebar from '@/components/DocsSidebar.vue'
import { docsSidebar } from '@/content/docs'
import {
  DocumentTextIcon,
  ArrowRightIcon,
} from '@heroicons/vue/24/outline'
</script>

<template>
  <DocsLayout>
    <template #sidebar>
      <DocsSidebar />
    </template>

    <div class="max-w-3xl">
      <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-4">
        Documentation
      </h1>
      <p class="text-lg text-gray-600 dark:text-gray-400 mb-8">
        Welcome to the Dev Website documentation. Learn how to integrate our APIs and build amazing applications.
      </p>

      <!-- Documentation sections -->
      <div class="space-y-8">
        <section v-for="section in docsSidebar" :key="section.title">
          <h2 class="text-xl font-semibold text-gray-900 dark:text-white mb-4">
            {{ section.title }}
          </h2>
          <div class="grid gap-4">
            <RouterLink
              v-for="item in section.items"
              :key="item.slug"
              :to="`/docs/${item.slug}`"
              class="flex items-start gap-4 p-4 bg-white dark:bg-gray-800 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors group"
            >
              <DocumentTextIcon class="w-6 h-6 text-gray-400 group-hover:text-blue-500 flex-shrink-0 mt-0.5" />
              <div class="flex-1 min-w-0">
                <h3 class="font-medium text-gray-900 dark:text-white group-hover:text-blue-600 dark:group-hover:text-blue-400">
                  {{ item.title }}
                </h3>
                <p class="text-sm text-gray-600 dark:text-gray-400 mt-1">
                  {{ item.description }}
                </p>
              </div>
              <ArrowRightIcon class="w-5 h-5 text-gray-400 group-hover:text-blue-500 flex-shrink-0 opacity-0 group-hover:opacity-100 transition-opacity" />
            </RouterLink>
          </div>
        </section>
      </div>

      <!-- Help section -->
      <div class="mt-12 p-6 bg-gray-50 dark:bg-gray-800/50 rounded-xl border border-gray-200 dark:border-gray-700">
        <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">
          Need help?
        </h2>
        <p class="text-gray-600 dark:text-gray-400 mb-4">
          Can't find what you're looking for? Check out our FAQ or join our community.
        </p>
        <div class="flex flex-wrap gap-3">
          <RouterLink to="/docs/faq" class="btn btn-secondary text-sm">
            View FAQ
          </RouterLink>
          <RouterLink to="/community" class="btn btn-secondary text-sm">
            Join Community
          </RouterLink>
        </div>
      </div>
    </div>
  </DocsLayout>
</template>
