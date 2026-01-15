<script setup lang="ts">
import { RouterLink } from 'vue-router'
</script>

<template>
  <div class="min-h-screen flex flex-col items-center justify-center bg-gray-50 dark:bg-gray-900 px-4">
    <a href="#main-content" class="skip-link">
      Skip to main content
    </a>
    <RouterLink to="/" class="mb-8 text-2xl font-bold text-gray-900 dark:text-white">
      Dev Website
    </RouterLink>
    <main id="main-content" class="w-full max-w-md" tabindex="-1">
      <slot />
    </main>
  </div>
</template>
