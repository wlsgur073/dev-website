<script setup lang="ts">
import { RouterLink, useRoute } from 'vue-router'
import {
  HomeIcon,
  KeyIcon,
  CreditCardIcon,
} from '@heroicons/vue/24/outline'

const route = useRoute()

const navItems = [
  { name: 'Dashboard', to: '/console', icon: HomeIcon },
  { name: 'API Keys', to: '/console/api-keys', icon: KeyIcon },
  { name: 'Billing', to: '/console/billing', icon: CreditCardIcon },
]

function isActive(path: string): boolean {
  return route.path === path
}
</script>

<template>
  <div class="min-h-screen bg-gray-50 dark:bg-gray-900">
    <!-- Top navbar -->
    <header class="bg-white dark:bg-gray-800 border-b border-gray-200 dark:border-gray-700">
      <div class="container-custom">
        <div class="flex items-center justify-between h-16">
          <RouterLink to="/" class="text-xl font-bold text-gray-900 dark:text-white">
            Dev Website
          </RouterLink>
          <RouterLink
            to="/"
            class="text-sm text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white"
          >
            Back to Home
          </RouterLink>
        </div>
      </div>
    </header>

    <div class="flex">
      <!-- Sidebar -->
      <aside class="w-64 bg-white dark:bg-gray-800 border-r border-gray-200 dark:border-gray-700 min-h-[calc(100vh-4rem)]">
        <nav class="p-4 space-y-1">
          <RouterLink
            v-for="item in navItems"
            :key="item.to"
            :to="item.to"
            class="flex items-center gap-3 px-3 py-2 rounded-lg text-sm font-medium transition-colors"
            :class="[
              isActive(item.to)
                ? 'bg-blue-50 text-blue-700 dark:bg-blue-900/50 dark:text-blue-300'
                : 'text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700'
            ]"
          >
            <component :is="item.icon" class="w-5 h-5" />
            {{ item.name }}
          </RouterLink>
        </nav>
      </aside>

      <!-- Main content -->
      <main class="flex-1 p-6">
        <slot />
      </main>
    </div>
  </div>
</template>
