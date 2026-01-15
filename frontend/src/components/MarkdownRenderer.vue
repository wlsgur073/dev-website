<script setup lang="ts">
import { ref, watch, onMounted, nextTick } from 'vue'
import { renderMarkdown, type TocItem } from '@/utils/markdown'

const props = defineProps<{
  content: string
}>()

const emit = defineEmits<{
  (e: 'toc', items: TocItem[]): void
}>()

const html = ref('')
const isLoading = ref(true)
const containerRef = ref<HTMLElement | null>(null)

async function render() {
  if (!props.content) {
    html.value = ''
    emit('toc', [])
    isLoading.value = false
    return
  }

  isLoading.value = true
  try {
    const result = await renderMarkdown(props.content)
    html.value = result.html
    emit('toc', result.toc)
    await nextTick()
    addCopyButtons()
  } catch (error) {
    console.error('Failed to render markdown:', error)
    html.value = `<p class="text-red-500">Failed to render content</p>`
  } finally {
    isLoading.value = false
  }
}

function addCopyButtons() {
  if (!containerRef.value) return

  const codeBlocks = containerRef.value.querySelectorAll('pre')
  codeBlocks.forEach((pre) => {
    // Skip if already has copy button
    if (pre.querySelector('.copy-button')) return

    const wrapper = document.createElement('div')
    wrapper.className = 'relative group'
    pre.parentNode?.insertBefore(wrapper, pre)
    wrapper.appendChild(pre)

    const button = document.createElement('button')
    button.type = 'button'
    button.className = 'copy-button absolute top-2 right-2 p-2 rounded-md bg-gray-100 dark:bg-gray-700 opacity-0 group-hover:opacity-100 transition-opacity text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white'
    button.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-4 h-4 copy-icon"><path stroke-linecap="round" stroke-linejoin="round" d="M15.666 3.888A2.25 2.25 0 0 0 13.5 2.25h-3c-1.03 0-1.9.693-2.166 1.638m7.332 0c.055.194.084.4.084.612v0a.75.75 0 0 1-.75.75H9a.75.75 0 0 1-.75-.75v0c0-.212.03-.418.084-.612m7.332 0c.646.049 1.288.11 1.927.184 1.1.128 1.907 1.077 1.907 2.185V19.5a2.25 2.25 0 0 1-2.25 2.25H6.75A2.25 2.25 0 0 1 4.5 19.5V6.257c0-1.108.806-2.057 1.907-2.185a48.208 48.208 0 0 1 1.927-.184" /></svg><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-4 h-4 check-icon hidden"><path stroke-linecap="round" stroke-linejoin="round" d="m4.5 12.75 6 6 9-13.5" /></svg>`
    button.setAttribute('aria-label', 'Copy code')

    button.addEventListener('click', async () => {
      const code = pre.querySelector('code')?.textContent || pre.textContent || ''
      try {
        await navigator.clipboard.writeText(code)
        const copyIcon = button.querySelector('.copy-icon')
        const checkIcon = button.querySelector('.check-icon')
        copyIcon?.classList.add('hidden')
        checkIcon?.classList.remove('hidden')
        setTimeout(() => {
          copyIcon?.classList.remove('hidden')
          checkIcon?.classList.add('hidden')
        }, 2000)
      } catch {
        console.error('Failed to copy code')
      }
    })

    wrapper.appendChild(button)
  })
}

watch(() => props.content, render, { immediate: true })

onMounted(() => {
  render()
})
</script>

<template>
  <div ref="containerRef">
    <div v-if="isLoading" class="animate-pulse space-y-4">
      <div class="h-8 bg-gray-200 dark:bg-gray-700 rounded w-3/4"></div>
      <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded"></div>
      <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-5/6"></div>
      <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-4/5"></div>
    </div>
    <div
      v-else
      class="prose prose-gray dark:prose-invert max-w-none
             prose-headings:scroll-mt-20
             prose-a:text-blue-600 dark:prose-a:text-blue-400
             prose-pre:bg-gray-50 dark:prose-pre:bg-gray-900
             prose-pre:border prose-pre:border-gray-200 dark:prose-pre:border-gray-700
             prose-code:before:content-none prose-code:after:content-none
             prose-code:bg-gray-100 dark:prose-code:bg-gray-800
             prose-code:px-1 prose-code:py-0.5 prose-code:rounded
             prose-code:text-gray-800 dark:prose-code:text-gray-200
             prose-pre:p-0
             [&_pre_code]:bg-transparent [&_pre_code]:p-4 [&_pre_code]:block"
      v-html="html"
    />
  </div>
</template>

<style>
/* Shiki dual theme support */
.shiki,
.shiki span {
  color: var(--shiki-light) !important;
  background-color: var(--shiki-light-bg) !important;
}

.dark .shiki,
.dark .shiki span {
  color: var(--shiki-dark) !important;
  background-color: var(--shiki-dark-bg) !important;
}

/* Code block styling */
.prose pre {
  margin: 0;
  border-radius: 0.5rem;
  overflow-x: auto;
}

.prose pre code {
  font-size: 0.875rem;
  line-height: 1.5;
}
</style>
