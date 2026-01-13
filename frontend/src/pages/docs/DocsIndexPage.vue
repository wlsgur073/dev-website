<script setup lang="ts">
import DefaultLayout from '@/layouts/DefaultLayout.vue'
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-16">
      <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-4">Documentation</h1>
      <p class="text-gray-600 dark:text-gray-400 mb-8">
        Welcome to the documentation. Choose a topic to get started.
      </p>

      <div class="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        <RouterLink
          to="/docs/quickstart"
          class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 hover:border-blue-500 transition-colors"
        >
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Quickstart</h2>
          <p class="text-gray-600 dark:text-gray-400">Get up and running in minutes.</p>
        </RouterLink>

        <RouterLink
          to="/docs/authentication"
          class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 hover:border-blue-500 transition-colors"
        >
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Authentication</h2>
          <p class="text-gray-600 dark:text-gray-400">Learn about API authentication methods.</p>
        </RouterLink>

        <RouterLink
          to="/docs/api-usage"
          class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 hover:border-blue-500 transition-colors"
        >
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">API Usage</h2>
          <p class="text-gray-600 dark:text-gray-400">Complete API reference and examples.</p>
        </RouterLink>

        <RouterLink
          to="/docs/faq"
          class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 hover:border-blue-500 transition-colors"
        >
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">FAQ</h2>
          <p class="text-gray-600 dark:text-gray-400">Frequently asked questions.</p>
        </RouterLink>
      </div>
    </div>
  </DefaultLayout>
</template>
