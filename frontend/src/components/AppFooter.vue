<script setup lang="ts">
import { RouterLink } from 'vue-router'

const footerLinks = {
  product: [
    { name: 'Documentation', to: '/docs' },
    { name: 'API Reference', to: '/api' },
    { name: 'Pricing', to: '/pricing' },
    { name: 'Releases', to: '/releases' },
  ],
  company: [
    { name: 'Announcements', to: '/announcements' },
    { name: 'Community', to: '/community' },
  ],
  legal: [
    { name: 'Privacy Policy', to: '#' },
    { name: 'Terms of Service', to: '#' },
  ],
}

const currentYear = new Date().getFullYear()
</script>

<template>
  <footer class="bg-gray-50 dark:bg-gray-900 border-t border-gray-200 dark:border-gray-800">
    <div class="container-custom py-12">
      <div class="grid grid-cols-2 md:grid-cols-4 gap-8">
        <!-- Brand -->
        <div class="col-span-2 md:col-span-1">
          <RouterLink to="/" class="text-xl font-bold text-gray-900 dark:text-white">
            Dev Website
          </RouterLink>
          <p class="mt-4 text-sm text-gray-600 dark:text-gray-400">
            Build amazing products with our powerful API.
          </p>
        </div>

        <!-- Product links -->
        <div>
          <h3 class="text-sm font-semibold text-gray-900 dark:text-white uppercase tracking-wider">
            Product
          </h3>
          <ul class="mt-4 space-y-3">
            <li v-for="link in footerLinks.product" :key="link.to">
              <RouterLink
                :to="link.to"
                class="text-sm text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-white"
              >
                {{ link.name }}
              </RouterLink>
            </li>
          </ul>
        </div>

        <!-- Company links -->
        <div>
          <h3 class="text-sm font-semibold text-gray-900 dark:text-white uppercase tracking-wider">
            Company
          </h3>
          <ul class="mt-4 space-y-3">
            <li v-for="link in footerLinks.company" :key="link.to">
              <RouterLink
                :to="link.to"
                class="text-sm text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-white"
              >
                {{ link.name }}
              </RouterLink>
            </li>
          </ul>
        </div>

        <!-- Legal links -->
        <div>
          <h3 class="text-sm font-semibold text-gray-900 dark:text-white uppercase tracking-wider">
            Legal
          </h3>
          <ul class="mt-4 space-y-3">
            <li v-for="link in footerLinks.legal" :key="link.name">
              <RouterLink
                :to="link.to"
                class="text-sm text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-white"
              >
                {{ link.name }}
              </RouterLink>
            </li>
          </ul>
        </div>
      </div>

      <div class="mt-12 pt-8 border-t border-gray-200 dark:border-gray-800">
        <p class="text-sm text-gray-500 dark:text-gray-400 text-center">
          &copy; {{ currentYear }} Dev Website. All rights reserved.
        </p>
      </div>
    </div>
  </footer>
</template>
