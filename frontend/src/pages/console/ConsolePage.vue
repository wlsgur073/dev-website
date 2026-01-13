<script setup lang="ts">
import ConsoleLayout from '@/layouts/ConsoleLayout.vue'
</script>

<template>
  <ConsoleLayout>
    <div>
      <h1 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">Dashboard</h1>

      <div class="grid md:grid-cols-3 gap-6 mb-8">
        <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
          <p class="text-sm text-gray-600 dark:text-gray-400">API Calls (This Month)</p>
          <p class="text-3xl font-bold text-gray-900 dark:text-white mt-2">0</p>
        </div>
        <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
          <p class="text-sm text-gray-600 dark:text-gray-400">Active API Keys</p>
          <p class="text-3xl font-bold text-gray-900 dark:text-white mt-2">0</p>
        </div>
        <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
          <p class="text-sm text-gray-600 dark:text-gray-400">Current Plan</p>
          <p class="text-3xl font-bold text-gray-900 dark:text-white mt-2">Free</p>
        </div>
      </div>

      <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
        <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">Quick Actions</h2>
        <div class="flex flex-wrap gap-4">
          <RouterLink to="/console/api-keys" class="btn btn-primary">
            Manage API Keys
          </RouterLink>
          <RouterLink to="/console/billing" class="btn btn-secondary">
            View Billing
          </RouterLink>
          <RouterLink to="/docs" class="btn btn-secondary">
            Read Documentation
          </RouterLink>
        </div>
      </div>
    </div>
  </ConsoleLayout>
</template>
