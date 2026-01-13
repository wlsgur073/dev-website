<script setup lang="ts">
import ConsoleLayout from '@/layouts/ConsoleLayout.vue'
import { PlusIcon } from '@heroicons/vue/24/outline'
</script>

<template>
  <ConsoleLayout>
    <div>
      <div class="flex items-center justify-between mb-6">
        <h1 class="text-2xl font-bold text-gray-900 dark:text-white">API Keys</h1>
        <button class="btn btn-primary inline-flex items-center gap-2">
          <PlusIcon class="w-5 h-5" />
          Create New Key
        </button>
      </div>

      <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700">
        <div class="p-6">
          <p class="text-gray-500 dark:text-gray-400">
            API key management will be implemented in Phase 3.
          </p>
        </div>
      </div>
    </div>
  </ConsoleLayout>
</template>
