<script setup lang="ts">
import { useRoute } from 'vue-router'
import DefaultLayout from '@/layouts/DefaultLayout.vue'

const route = useRoute()
const id = route.params.id
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-16">
      <RouterLink to="/announcements" class="text-blue-600 dark:text-blue-400 hover:underline mb-4 inline-block">
        ← Back to Announcements
      </RouterLink>
      <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-4">
        Announcement #{{ id }}
      </h1>
      <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
        <p class="text-gray-500 dark:text-gray-400">
          Announcement details will be loaded from API in Phase 2.
        </p>
      </div>
    </div>
  </DefaultLayout>
</template>
