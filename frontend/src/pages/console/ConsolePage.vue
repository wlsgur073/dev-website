<script setup lang="ts">
import { ref, onMounted } from 'vue'
import ConsoleLayout from '@/layouts/ConsoleLayout.vue'
import { useAuthStore } from '@/stores/auth'
import { getApiKeys } from '@/api/apikeys'
import { getUsageStats, type UsageStats } from '@/api/subscription'
import {
  ChartBarIcon,
  KeyIcon,
  CreditCardIcon,
  DocumentTextIcon,
  ArrowTrendingUpIcon,
} from '@heroicons/vue/24/outline'

const authStore = useAuthStore()

const apiKeyCount = ref(0)
const usageStats = ref<UsageStats | null>(null)
const isLoading = ref(true)

async function loadDashboardData() {
  isLoading.value = true

  try {
    // Load API keys count
    const keysResponse = await getApiKeys()
    apiKeyCount.value = keysResponse.totalElements
  } catch {
    // API not available
  }

  try {
    // Load usage stats
    usageStats.value = await getUsageStats()
  } catch {
    // API not available
  }

  isLoading.value = false
}

function formatNumber(num: number): string {
  if (num >= 1000000) {
    return (num / 1000000).toFixed(1) + 'M'
  }
  if (num >= 1000) {
    return (num / 1000).toFixed(1) + 'K'
  }
  return num.toString()
}

function getUsagePercentage(used: number, limit: number): number {
  if (limit === 0) return 0
  return Math.min(100, Math.round((used / limit) * 100))
}

onMounted(loadDashboardData)
</script>

<template>
  <ConsoleLayout>
    <div>
      <!-- Welcome header -->
      <div class="mb-8">
        <h1 class="text-2xl font-bold text-gray-900 dark:text-white">
          Welcome back{{ authStore.user?.name ? `, ${authStore.user.name}` : '' }}
        </h1>
        <p class="text-gray-600 dark:text-gray-400 mt-1">
          Here's an overview of your API usage and account.
        </p>
      </div>

      <!-- Stats cards -->
      <div class="grid md:grid-cols-3 gap-6 mb-8">
        <!-- API Calls -->
        <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
          <div class="flex items-center justify-between mb-4">
            <p class="text-sm font-medium text-gray-600 dark:text-gray-400">API Calls (Today)</p>
            <ChartBarIcon class="w-5 h-5 text-gray-400" />
          </div>
          <div v-if="isLoading" class="animate-pulse">
            <div class="h-8 bg-gray-200 dark:bg-gray-700 rounded w-20"></div>
          </div>
          <template v-else>
            <p class="text-3xl font-bold text-gray-900 dark:text-white">
              {{ formatNumber(usageStats?.requestsToday ?? 0) }}
            </p>
            <div v-if="usageStats" class="mt-3">
              <div class="flex items-center justify-between text-xs text-gray-500 dark:text-gray-400 mb-1">
                <span>Daily limit</span>
                <span>{{ formatNumber(usageStats.requestsToday) }} / {{ formatNumber(usageStats.dailyLimit) }}</span>
              </div>
              <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-1.5">
                <div
                  class="bg-blue-600 h-1.5 rounded-full transition-all"
                  :style="{ width: `${getUsagePercentage(usageStats.requestsToday, usageStats.dailyLimit)}%` }"
                ></div>
              </div>
            </div>
          </template>
        </div>

        <!-- Active API Keys -->
        <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
          <div class="flex items-center justify-between mb-4">
            <p class="text-sm font-medium text-gray-600 dark:text-gray-400">Active API Keys</p>
            <KeyIcon class="w-5 h-5 text-gray-400" />
          </div>
          <div v-if="isLoading" class="animate-pulse">
            <div class="h-8 bg-gray-200 dark:bg-gray-700 rounded w-12"></div>
          </div>
          <template v-else>
            <p class="text-3xl font-bold text-gray-900 dark:text-white">
              {{ apiKeyCount }}
            </p>
            <p class="text-sm text-gray-500 dark:text-gray-400 mt-2">
              <RouterLink to="/console/api-keys" class="text-blue-600 dark:text-blue-400 hover:underline">
                Manage keys →
              </RouterLink>
            </p>
          </template>
        </div>

        <!-- Current Plan -->
        <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
          <div class="flex items-center justify-between mb-4">
            <p class="text-sm font-medium text-gray-600 dark:text-gray-400">Current Plan</p>
            <CreditCardIcon class="w-5 h-5 text-gray-400" />
          </div>
          <p class="text-3xl font-bold text-gray-900 dark:text-white">Free</p>
          <p class="text-sm text-gray-500 dark:text-gray-400 mt-2">
            <RouterLink to="/console/billing" class="text-blue-600 dark:text-blue-400 hover:underline">
              Upgrade plan →
            </RouterLink>
          </p>
        </div>
      </div>

      <!-- Monthly usage -->
      <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6 mb-8">
        <div class="flex items-center gap-2 mb-4">
          <ArrowTrendingUpIcon class="w-5 h-5 text-gray-400" />
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white">Monthly Usage</h2>
        </div>

        <div v-if="isLoading" class="animate-pulse space-y-3">
          <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-full"></div>
          <div class="h-2 bg-gray-200 dark:bg-gray-700 rounded w-full"></div>
        </div>

        <div v-else-if="usageStats">
          <div class="flex items-center justify-between mb-2">
            <span class="text-sm text-gray-600 dark:text-gray-400">
              {{ formatNumber(usageStats.requestsThisMonth) }} of {{ formatNumber(usageStats.monthlyLimit) }} requests used
            </span>
            <span class="text-sm font-medium text-gray-900 dark:text-white">
              {{ getUsagePercentage(usageStats.requestsThisMonth, usageStats.monthlyLimit) }}%
            </span>
          </div>
          <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-3">
            <div
              :class="[
                'h-3 rounded-full transition-all',
                getUsagePercentage(usageStats.requestsThisMonth, usageStats.monthlyLimit) >= 90
                  ? 'bg-red-500'
                  : getUsagePercentage(usageStats.requestsThisMonth, usageStats.monthlyLimit) >= 75
                  ? 'bg-yellow-500'
                  : 'bg-blue-600'
              ]"
              :style="{ width: `${getUsagePercentage(usageStats.requestsThisMonth, usageStats.monthlyLimit)}%` }"
            ></div>
          </div>
        </div>

        <div v-else class="text-center py-4 text-gray-500 dark:text-gray-400">
          No usage data available
        </div>
      </div>

      <!-- Quick Actions -->
      <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
        <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">Quick Actions</h2>
        <div class="grid sm:grid-cols-2 md:grid-cols-4 gap-4">
          <RouterLink
            to="/console/api-keys"
            class="flex items-center gap-3 p-4 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors"
          >
            <div class="w-10 h-10 bg-blue-100 dark:bg-blue-900 rounded-lg flex items-center justify-center">
              <KeyIcon class="w-5 h-5 text-blue-600 dark:text-blue-400" />
            </div>
            <div>
              <p class="font-medium text-gray-900 dark:text-white">API Keys</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">Manage your keys</p>
            </div>
          </RouterLink>

          <RouterLink
            to="/console/billing"
            class="flex items-center gap-3 p-4 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors"
          >
            <div class="w-10 h-10 bg-green-100 dark:bg-green-900 rounded-lg flex items-center justify-center">
              <CreditCardIcon class="w-5 h-5 text-green-600 dark:text-green-400" />
            </div>
            <div>
              <p class="font-medium text-gray-900 dark:text-white">Billing</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">Manage subscription</p>
            </div>
          </RouterLink>

          <RouterLink
            to="/docs"
            class="flex items-center gap-3 p-4 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors"
          >
            <div class="w-10 h-10 bg-purple-100 dark:bg-purple-900 rounded-lg flex items-center justify-center">
              <DocumentTextIcon class="w-5 h-5 text-purple-600 dark:text-purple-400" />
            </div>
            <div>
              <p class="font-medium text-gray-900 dark:text-white">Documentation</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">Read the docs</p>
            </div>
          </RouterLink>

          <RouterLink
            to="/community"
            class="flex items-center gap-3 p-4 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 dark:hover:border-blue-500 transition-colors"
          >
            <div class="w-10 h-10 bg-orange-100 dark:bg-orange-900 rounded-lg flex items-center justify-center">
              <ChartBarIcon class="w-5 h-5 text-orange-600 dark:text-orange-400" />
            </div>
            <div>
              <p class="font-medium text-gray-900 dark:text-white">Community</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">Get help</p>
            </div>
          </RouterLink>
        </div>
      </div>
    </div>
  </ConsoleLayout>
</template>
