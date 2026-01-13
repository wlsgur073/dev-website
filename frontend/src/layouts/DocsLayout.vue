<script setup lang="ts">
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
</script>

<template>
  <div class="min-h-screen flex flex-col">
    <AppHeader />
    <div class="flex-1 container-custom py-8">
      <div class="flex gap-8">
        <!-- Left sidebar -->
        <aside class="hidden lg:block w-64 flex-shrink-0">
          <slot name="sidebar" />
        </aside>

        <!-- Main content -->
        <main class="flex-1 min-w-0">
          <slot />
        </main>

        <!-- Right TOC -->
        <aside class="hidden xl:block w-56 flex-shrink-0">
          <slot name="toc" />
        </aside>
      </div>
    </div>
    <AppFooter />
  </div>
</template>
