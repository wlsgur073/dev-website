<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import {
  SunIcon,
  MoonIcon,
  ComputerDesktopIcon,
} from '@heroicons/vue/24/outline'
import { useThemeStore, type ThemeMode } from '@/stores/theme'

const themeStore = useThemeStore()
const isOpen = ref(false)

const options: { value: ThemeMode; label: string; icon: typeof SunIcon }[] = [
  { value: 'light', label: 'Light', icon: SunIcon },
  { value: 'dark', label: 'Dark', icon: MoonIcon },
  { value: 'system', label: 'System', icon: ComputerDesktopIcon },
]

function selectTheme(mode: ThemeMode) {
  themeStore.setMode(mode)
  isOpen.value = false
}

function handleClickOutside(event: MouseEvent) {
  const target = event.target as HTMLElement
  if (!target.closest('.theme-toggle')) {
    isOpen.value = false
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
  themeStore.initThemeListener()
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<template>
  <div class="relative theme-toggle">
    <button
      type="button"
      class="p-2 text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800"
      @click="isOpen = !isOpen"
      aria-label="Toggle theme"
      :aria-expanded="isOpen"
    >
      <SunIcon v-if="themeStore.mode === 'light'" class="w-5 h-5" />
      <MoonIcon v-else-if="themeStore.mode === 'dark'" class="w-5 h-5" />
      <ComputerDesktopIcon v-else class="w-5 h-5" />
    </button>

    <div
      v-if="isOpen"
      class="absolute right-0 mt-2 w-36 bg-white dark:bg-gray-800 rounded-lg shadow-lg border border-gray-200 dark:border-gray-700 py-1 z-50"
      role="menu"
    >
      <button
        v-for="option in options"
        :key="option.value"
        type="button"
        class="w-full flex items-center gap-2 px-3 py-2 text-sm text-left hover:bg-gray-100 dark:hover:bg-gray-700"
        :class="[
          themeStore.mode === option.value
            ? 'text-blue-600 dark:text-blue-400'
            : 'text-gray-700 dark:text-gray-300'
        ]"
        role="menuitem"
        @click="selectTheme(option.value)"
      >
        <component :is="option.icon" class="w-4 h-4" />
        {{ option.label }}
      </button>
    </div>
  </div>
</template>
