<script setup lang="ts">
import { ref } from 'vue'
import DefaultLayout from '@/layouts/DefaultLayout.vue'
import { MagnifyingGlassIcon } from '@heroicons/vue/24/outline'

const searchQuery = ref('')
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-16">
      <div class="max-w-2xl mx-auto">
        <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-8 text-center">Search</h1>

        <div class="relative">
          <MagnifyingGlassIcon class="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
          <input
            v-model="searchQuery"
            type="search"
            placeholder="Search documentation, announcements, releases..."
            class="w-full pl-12 pr-4 py-3 bg-white dark:bg-gray-800 border border-gray-300 dark:border-gray-700 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-gray-900 dark:text-white placeholder-gray-500"
          />
        </div>

        <div v-if="searchQuery" class="mt-8">
          <p class="text-gray-600 dark:text-gray-400">
            Search results for "{{ searchQuery }}" will be implemented in Phase 2.
          </p>
        </div>

        <div v-else class="mt-8 text-center">
          <p class="text-gray-500 dark:text-gray-400">
            Enter a search term to find documentation, announcements, and releases.
          </p>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
