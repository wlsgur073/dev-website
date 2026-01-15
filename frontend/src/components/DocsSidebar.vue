<script setup lang="ts">
import { useRoute } from 'vue-router'
import { docsSidebar } from '@/content/docs'
import {
  ChevronRightIcon,
  Bars3BottomLeftIcon,
  XMarkIcon,
} from '@heroicons/vue/24/outline'
import { ref, computed } from 'vue'

const route = useRoute()
const isMobileOpen = ref(false)

const currentSlug = computed(() => route.params.slug as string | undefined)

function isActive(slug: string): boolean {
  return currentSlug.value === slug
}

function toggleMobile() {
  isMobileOpen.value = !isMobileOpen.value
}

function closeMobile() {
  isMobileOpen.value = false
}
</script>

<template>
  <!-- Mobile toggle button -->
  <button
    type="button"
    class="lg:hidden fixed bottom-4 left-4 z-50 p-3 bg-blue-600 text-white rounded-full shadow-lg"
    @click="toggleMobile"
    aria-label="Toggle docs menu"
  >
    <Bars3BottomLeftIcon v-if="!isMobileOpen" class="w-6 h-6" />
    <XMarkIcon v-else class="w-6 h-6" />
  </button>

  <!-- Mobile overlay -->
  <div
    v-if="isMobileOpen"
    class="lg:hidden fixed inset-0 bg-black/50 z-40"
    @click="closeMobile"
  />

  <!-- Sidebar content -->
  <nav
    :class="[
      'fixed lg:sticky top-0 lg:top-20 left-0 h-full lg:h-auto w-64 bg-white dark:bg-gray-900 lg:bg-transparent z-50 lg:z-auto',
      'transform transition-transform duration-200 ease-in-out',
      'overflow-y-auto lg:overflow-visible',
      'border-r border-gray-200 dark:border-gray-800 lg:border-0',
      'p-4 lg:p-0',
      isMobileOpen ? 'translate-x-0' : '-translate-x-full lg:translate-x-0'
    ]"
  >
    <!-- Mobile close button -->
    <div class="lg:hidden flex justify-end mb-4">
      <button
        type="button"
        class="p-2 text-gray-500 hover:text-gray-700 dark:hover:text-gray-300"
        @click="closeMobile"
        aria-label="Close menu"
      >
        <XMarkIcon class="w-5 h-5" />
      </button>
    </div>

    <div class="space-y-6">
      <div v-for="section in docsSidebar" :key="section.title">
        <h3 class="text-sm font-semibold text-gray-900 dark:text-white mb-2">
          {{ section.title }}
        </h3>
        <ul class="space-y-1">
          <li v-for="item in section.items" :key="item.slug">
            <RouterLink
              :to="`/docs/${item.slug}`"
              :class="[
                'flex items-center gap-2 px-3 py-2 text-sm rounded-lg transition-colors',
                isActive(item.slug)
                  ? 'bg-blue-50 dark:bg-blue-900/50 text-blue-700 dark:text-blue-300 font-medium'
                  : 'text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-white hover:bg-gray-100 dark:hover:bg-gray-800'
              ]"
              @click="closeMobile"
            >
              <ChevronRightIcon
                v-if="isActive(item.slug)"
                class="w-4 h-4 flex-shrink-0"
              />
              <span>{{ item.title }}</span>
            </RouterLink>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>
