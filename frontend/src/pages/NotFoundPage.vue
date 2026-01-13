<script setup lang="ts">
import DefaultLayout from '@/layouts/DefaultLayout.vue'
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-16">
      <div class="text-center">
        <h1 class="text-9xl font-bold text-gray-200 dark:text-gray-800">404</h1>
        <h2 class="text-2xl font-bold text-gray-900 dark:text-white mt-4 mb-2">Page Not Found</h2>
        <p class="text-gray-600 dark:text-gray-400 mb-8">
          The page you're looking for doesn't exist or has been moved.
        </p>
        <div class="flex justify-center gap-4">
          <RouterLink to="/" class="btn btn-primary">
            Go Home
          </RouterLink>
          <RouterLink to="/docs" class="btn btn-secondary">
            View Documentation
          </RouterLink>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
