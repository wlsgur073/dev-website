import { ref } from 'vue'
import type { ToastMessage } from '@/components/Toast.vue'

const messages = ref<ToastMessage[]>([])
let nextId = 1

export function useToast() {
  function show(type: ToastMessage['type'], message: string, duration = 5000) {
    const id = nextId++
    messages.value.push({ id, type, message, duration })
    return id
  }

  function success(message: string, duration = 5000) {
    return show('success', message, duration)
  }

  function error(message: string, duration = 5000) {
    return show('error', message, duration)
  }

  function warning(message: string, duration = 5000) {
    return show('warning', message, duration)
  }

  function info(message: string, duration = 5000) {
    return show('info', message, duration)
  }

  function remove(id: number) {
    const index = messages.value.findIndex(m => m.id === id)
    if (index !== -1) {
      messages.value.splice(index, 1)
    }
  }

  function clear() {
    messages.value = []
  }

  return {
    messages,
    show,
    success,
    error,
    warning,
    info,
    remove,
    clear,
  }
}
