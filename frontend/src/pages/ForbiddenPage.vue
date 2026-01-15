<script setup lang="ts">
import DefaultLayout from '@/layouts/DefaultLayout.vue'
import { ShieldExclamationIcon } from '@heroicons/vue/24/outline'
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-16">
      <div class="text-center">
        <ShieldExclamationIcon class="w-24 h-24 mx-auto text-red-500 mb-6" />
        <h1 class="text-4xl font-bold text-gray-900 dark:text-white mb-2">403</h1>
        <h2 class="text-2xl font-bold text-gray-900 dark:text-white mt-4 mb-2">Access Denied</h2>
        <p class="text-gray-600 dark:text-gray-400 mb-8">
          You don't have permission to access this page.
        </p>
        <div class="flex justify-center gap-4">
          <RouterLink to="/" class="btn btn-primary">
            Go Home
          </RouterLink>
          <RouterLink to="/console" class="btn btn-secondary">
            Go to Console
          </RouterLink>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
