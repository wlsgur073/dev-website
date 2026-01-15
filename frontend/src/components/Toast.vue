<script setup lang="ts">
import { watch } from 'vue'
import {
  CheckCircleIcon,
  XCircleIcon,
  ExclamationTriangleIcon,
  InformationCircleIcon,
  XMarkIcon,
} from '@heroicons/vue/24/outline'

export interface ToastMessage {
  id: number
  type: 'success' | 'error' | 'warning' | 'info'
  message: string
  duration?: number
}

const props = defineProps<{
  messages: ToastMessage[]
}>()

const emit = defineEmits<{
  remove: [id: number]
}>()

const icons = {
  success: CheckCircleIcon,
  error: XCircleIcon,
  warning: ExclamationTriangleIcon,
  info: InformationCircleIcon,
}

const colors = {
  success: 'bg-green-50 dark:bg-green-900/30 border-green-200 dark:border-green-800 text-green-800 dark:text-green-200',
  error: 'bg-red-50 dark:bg-red-900/30 border-red-200 dark:border-red-800 text-red-800 dark:text-red-200',
  warning: 'bg-yellow-50 dark:bg-yellow-900/30 border-yellow-200 dark:border-yellow-800 text-yellow-800 dark:text-yellow-200',
  info: 'bg-blue-50 dark:bg-blue-900/30 border-blue-200 dark:border-blue-800 text-blue-800 dark:text-blue-200',
}

const iconColors = {
  success: 'text-green-500',
  error: 'text-red-500',
  warning: 'text-yellow-500',
  info: 'text-blue-500',
}

// Auto-remove after duration
watch(() => props.messages, (newMessages) => {
  newMessages.forEach((msg) => {
    if (msg.duration !== 0) {
      setTimeout(() => {
        emit('remove', msg.id)
      }, msg.duration || 5000)
    }
  })
}, { deep: true })
</script>

<template>
  <Teleport to="body">
    <div class="fixed top-4 right-4 z-50 space-y-2 max-w-sm">
      <TransitionGroup name="toast">
        <div
          v-for="msg in messages"
          :key="msg.id"
          :class="[
            'flex items-start gap-3 p-4 rounded-lg border shadow-lg',
            colors[msg.type]
          ]"
        >
          <component
            :is="icons[msg.type]"
            :class="['w-5 h-5 flex-shrink-0', iconColors[msg.type]]"
          />
          <p class="flex-1 text-sm">{{ msg.message }}</p>
          <button
            type="button"
            class="flex-shrink-0 hover:opacity-70"
            @click="emit('remove', msg.id)"
          >
            <XMarkIcon class="w-5 h-5" />
          </button>
        </div>
      </TransitionGroup>
    </div>
  </Teleport>
</template>

<style scoped>
.toast-enter-active,
.toast-leave-active {
  transition: all 0.3s ease;
}

.toast-enter-from {
  opacity: 0;
  transform: translateX(100%);
}

.toast-leave-to {
  opacity: 0;
  transform: translateX(100%);
}
</style>
