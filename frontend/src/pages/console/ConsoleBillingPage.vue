<script setup lang="ts">
import ConsoleLayout from '@/layouts/ConsoleLayout.vue'
</script>

<template>
  <ConsoleLayout>
    <div>
      <h1 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">Billing</h1>

      <div class="grid md:grid-cols-2 gap-6 mb-8">
        <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">Current Plan</h2>
          <p class="text-3xl font-bold text-gray-900 dark:text-white">Free</p>
          <p class="text-gray-600 dark:text-gray-400 mt-2">1,000 API calls/month</p>
          <RouterLink to="/pricing" class="btn btn-primary mt-4 inline-block">
            Upgrade Plan
          </RouterLink>
        </div>

        <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">Usage This Month</h2>
          <p class="text-3xl font-bold text-gray-900 dark:text-white">0 / 1,000</p>
          <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-2 mt-4">
            <div class="bg-blue-600 h-2 rounded-full" style="width: 0%"></div>
          </div>
        </div>
      </div>

      <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
        <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">Billing History</h2>
        <p class="text-gray-500 dark:text-gray-400">
          Billing history will be implemented in Phase 3.
        </p>
      </div>
    </div>
  </ConsoleLayout>
</template>
