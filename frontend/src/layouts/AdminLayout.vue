<script setup lang="ts">
import { RouterLink, useRoute } from 'vue-router'
import {
  MegaphoneIcon,
  RocketLaunchIcon,
} from '@heroicons/vue/24/outline'

const route = useRoute()

const navItems = [
  { name: 'Announcements', to: '/admin/announcements', icon: MegaphoneIcon },
  { name: 'Releases', to: '/admin/releases', icon: RocketLaunchIcon },
]

function isActive(path: string): boolean {
  return route.path === path
}
</script>

<template>
  <div class="min-h-screen bg-gray-50 dark:bg-gray-900">
    <!-- Top navbar -->
    <header class="bg-red-600 dark:bg-red-800">
      <div class="container-custom">
        <div class="flex items-center justify-between h-16">
          <div class="flex items-center gap-3">
            <RouterLink to="/" class="text-xl font-bold text-white">
              Dev Website
            </RouterLink>
            <span class="px-2 py-1 text-xs font-semibold bg-red-700 dark:bg-red-900 text-white rounded">
              ADMIN
            </span>
          </div>
          <RouterLink
            to="/"
            class="text-sm text-red-100 hover:text-white"
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
                ? 'bg-red-50 text-red-700 dark:bg-red-900/50 dark:text-red-300'
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
