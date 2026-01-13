import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export interface User {
  id: number
  email: string
  name: string
  roles: string[]
}

export const useAuthStore = defineStore('auth', () => {
  // State - accessToken is stored only in memory (not localStorage)
  const accessToken = ref<string | null>(null)
  const user = ref<User | null>(null)
  const isLoading = ref(false)

  // Getters
  const isAuthenticated = computed(() => !!accessToken.value)
  const isAdmin = computed(() => user.value?.roles.includes('ROLE_ADMIN') ?? false)

  // Actions
  function setAccessToken(token: string | null) {
    accessToken.value = token
  }

  function setUser(userData: User | null) {
    user.value = userData
  }

  function clearAuth() {
    accessToken.value = null
    user.value = null
  }

  function setLoading(loading: boolean) {
    isLoading.value = loading
  }

  return {
    // State
    accessToken,
    user,
    isLoading,
    // Getters
    isAuthenticated,
    isAdmin,
    // Actions
    setAccessToken,
    setUser,
    clearAuth,
    setLoading,
  }
})
