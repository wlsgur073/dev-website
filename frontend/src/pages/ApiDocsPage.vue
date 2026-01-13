<script setup lang="ts">
import DefaultLayout from '@/layouts/DefaultLayout.vue'
import { ArrowTopRightOnSquareIcon } from '@heroicons/vue/24/outline'
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-16">
      <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-4">API Documentation</h1>
      <p class="text-gray-600 dark:text-gray-400 mb-8">
        Explore our API using the interactive Swagger documentation.
      </p>

      <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-8">
        <h2 class="text-xl font-semibold text-gray-900 dark:text-white mb-4">
          Interactive API Explorer
        </h2>
        <p class="text-gray-600 dark:text-gray-400 mb-6">
          Our API documentation is powered by Swagger/OpenAPI. You can explore all available endpoints,
          see request/response schemas, and try out API calls directly.
        </p>
        <a
          href="/api/swagger-ui.html"
          target="_blank"
          rel="noopener noreferrer"
          class="inline-flex items-center gap-2 btn btn-primary"
        >
          Open Swagger UI
          <ArrowTopRightOnSquareIcon class="w-4 h-4" />
        </a>
      </div>

      <div class="mt-8 grid md:grid-cols-2 gap-6">
        <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
          <h3 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Base URL</h3>
          <code class="text-sm bg-gray-100 dark:bg-gray-700 px-2 py-1 rounded">
            https://api.example.com/api
          </code>
        </div>
        <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
          <h3 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Authentication</h3>
          <p class="text-gray-600 dark:text-gray-400 text-sm">
            Bearer token required for protected endpoints
          </p>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
