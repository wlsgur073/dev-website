<script setup lang="ts">
import { ref } from 'vue'
import { RouterLink } from 'vue-router'
import {
  Bars3Icon,
  XMarkIcon,
  MagnifyingGlassIcon,
} from '@heroicons/vue/24/outline'
import ThemeToggle from './ThemeToggle.vue'
import { useAuthStore } from '@/stores'

const authStore = useAuthStore()
const isMobileMenuOpen = ref(false)

const navLinks = [
  { name: 'Docs', to: '/docs' },
  { name: 'API', to: '/api' },
  { name: 'Releases', to: '/releases' },
  { name: 'Announcements', to: '/announcements' },
  { name: 'Pricing', to: '/pricing' },
  { name: 'Community', to: '/community' },
]

function toggleMobileMenu() {
  isMobileMenuOpen.value = !isMobileMenuOpen.value
}
</script>

<template>
  <header class="sticky top-0 z-50 bg-white/80 dark:bg-gray-900/80 backdrop-blur-md border-b border-gray-200 dark:border-gray-800">
    <div class="container-custom">
      <div class="flex items-center justify-between h-16">
        <!-- Logo -->
        <RouterLink to="/" class="text-xl font-bold text-gray-900 dark:text-white">
          Dev Website
        </RouterLink>

        <!-- Desktop Navigation -->
        <nav class="hidden md:flex items-center gap-6">
          <RouterLink
            v-for="link in navLinks"
            :key="link.to"
            :to="link.to"
            class="text-sm font-medium text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white transition-colors"
          >
            {{ link.name }}
          </RouterLink>
        </nav>

        <!-- Right side actions -->
        <div class="flex items-center gap-4">
          <RouterLink
            to="/search"
            class="p-2 text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200"
            aria-label="Search"
          >
            <MagnifyingGlassIcon class="w-5 h-5" />
          </RouterLink>

          <ThemeToggle />

          <template v-if="authStore.isAuthenticated">
            <RouterLink
              to="/console"
              class="hidden sm:inline-flex btn btn-secondary text-sm"
            >
              Console
            </RouterLink>
          </template>
          <template v-else>
            <RouterLink
              to="/login"
              class="hidden sm:inline-flex text-sm font-medium text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white"
            >
              Login
            </RouterLink>
            <RouterLink
              to="/register"
              class="hidden sm:inline-flex btn btn-primary text-sm"
            >
              Get Started
            </RouterLink>
          </template>

          <!-- Mobile menu button -->
          <button
            type="button"
            class="md:hidden p-2 text-gray-500 dark:text-gray-400"
            @click="toggleMobileMenu"
            :aria-expanded="isMobileMenuOpen"
            aria-label="Toggle menu"
          >
            <Bars3Icon v-if="!isMobileMenuOpen" class="w-6 h-6" />
            <XMarkIcon v-else class="w-6 h-6" />
          </button>
        </div>
      </div>
    </div>

    <!-- Mobile Navigation -->
    <div
      v-if="isMobileMenuOpen"
      class="md:hidden border-t border-gray-200 dark:border-gray-800"
    >
      <nav class="container-custom py-4 space-y-2">
        <RouterLink
          v-for="link in navLinks"
          :key="link.to"
          :to="link.to"
          class="block px-3 py-2 rounded-lg text-base font-medium text-gray-600 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-800"
          @click="isMobileMenuOpen = false"
        >
          {{ link.name }}
        </RouterLink>
        <hr class="border-gray-200 dark:border-gray-700" />
        <template v-if="authStore.isAuthenticated">
          <RouterLink
            to="/console"
            class="block px-3 py-2 rounded-lg text-base font-medium text-blue-600 dark:text-blue-400"
            @click="isMobileMenuOpen = false"
          >
            Console
          </RouterLink>
        </template>
        <template v-else>
          <RouterLink
            to="/login"
            class="block px-3 py-2 rounded-lg text-base font-medium text-gray-600 dark:text-gray-300"
            @click="isMobileMenuOpen = false"
          >
            Login
          </RouterLink>
          <RouterLink
            to="/register"
            class="block px-3 py-2 rounded-lg text-base font-medium text-blue-600 dark:text-blue-400"
            @click="isMobileMenuOpen = false"
          >
            Get Started
          </RouterLink>
        </template>
      </nav>
    </div>
  </header>
</template>
