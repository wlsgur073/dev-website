<script setup lang="ts">
import { ref, onMounted, onUnmounted, watch } from 'vue'
import type { TocItem } from '@/utils/markdown'

const props = defineProps<{
  items: TocItem[]
}>()

const activeId = ref<string | null>(null)

function handleScroll() {
  if (!props.items.length) return

  const headings = props.items
    .map(item => document.getElementById(item.id))
    .filter((el): el is HTMLElement => el !== null)

  const scrollTop = window.scrollY + 100

  for (let i = headings.length - 1; i >= 0; i--) {
    const heading = headings[i]
    if (heading.offsetTop <= scrollTop) {
      activeId.value = heading.id
      return
    }
  }

  activeId.value = props.items[0]?.id || null
}

function scrollToHeading(id: string) {
  const element = document.getElementById(id)
  if (element) {
    const offset = 80
    const top = element.offsetTop - offset
    window.scrollTo({ top, behavior: 'smooth' })
  }
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll, { passive: true })
  handleScroll()
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})

watch(
  () => props.items,
  () => {
    handleScroll()
  },
  { immediate: true }
)
</script>

<template>
  <nav v-if="items.length > 0" class="sticky top-20">
    <h4 class="text-sm font-semibold text-gray-900 dark:text-white mb-3">
      On this page
    </h4>
    <ul class="space-y-2">
      <li
        v-for="item in items"
        :key="item.id"
        :style="{ paddingLeft: `${(item.level - 2) * 0.75}rem` }"
      >
        <button
          type="button"
          :class="[
            'text-left text-sm transition-colors w-full',
            activeId === item.id
              ? 'text-blue-600 dark:text-blue-400 font-medium'
              : 'text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-white'
          ]"
          @click="scrollToHeading(item.id)"
        >
          {{ item.text }}
        </button>
      </li>
    </ul>
  </nav>
</template>
