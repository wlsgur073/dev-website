<script setup lang="ts">
import { useRoute } from 'vue-router'
import DefaultLayout from '@/layouts/DefaultLayout.vue'

const route = useRoute()
const slug = route.params.slug as string
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-16">
      <div class="max-w-3xl">
        <p class="text-sm text-blue-600 dark:text-blue-400 mb-2">Documentation</p>
        <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-4 capitalize">
          {{ slug?.replace(/-/g, ' ') || 'Document' }}
        </h1>
        <div class="prose dark:prose-invert max-w-none">
          <p class="text-gray-600 dark:text-gray-400">
            Documentation content for "{{ slug }}" will be rendered here using markdown-it in Phase 2.
          </p>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
