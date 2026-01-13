<script setup lang="ts">
import { RouterLink } from 'vue-router'
</script>

<template>
  <div class="min-h-screen flex flex-col items-center justify-center bg-gray-50 dark:bg-gray-900 px-4">
    <RouterLink to="/" class="mb-8 text-2xl font-bold text-gray-900 dark:text-white">
      Dev Website
    </RouterLink>
    <div class="w-full max-w-md">
      <slot />
    </div>
  </div>
</template>
