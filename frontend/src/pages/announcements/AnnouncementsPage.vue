<script setup lang="ts">
import DefaultLayout from '@/layouts/DefaultLayout.vue'
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-16">
      <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-4">Announcements</h1>
      <p class="text-gray-600 dark:text-gray-400 mb-8">
        Important updates and news from our team.
      </p>

      <div class="space-y-6">
        <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
          <p class="text-gray-500 dark:text-gray-400">
            Announcements will be loaded from API in Phase 2.
          </p>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
