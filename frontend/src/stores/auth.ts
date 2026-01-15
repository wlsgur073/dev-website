import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import * as authApi from '@/api/auth'
import type { LoginRequest, RegisterRequest } from '@/api/auth'

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
  const error = ref<string | null>(null)
  const isInitialized = ref(false)

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

  function setError(err: string | null) {
    error.value = err
  }

  async function login(data: LoginRequest): Promise<boolean> {
    isLoading.value = true
    error.value = null
    try {
      const response = await authApi.login(data)
      accessToken.value = response.accessToken
      user.value = response.user
      return true
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Login failed'
      return false
    } finally {
      isLoading.value = false
    }
  }

  async function register(data: RegisterRequest): Promise<boolean> {
    isLoading.value = true
    error.value = null
    try {
      const response = await authApi.register(data)
      accessToken.value = response.accessToken
      user.value = response.user
      return true
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Registration failed'
      return false
    } finally {
      isLoading.value = false
    }
  }

  async function logout(): Promise<void> {
    try {
      await authApi.logout()
    } catch {
      // Ignore errors - we'll clear local state anyway
    } finally {
      clearAuth()
    }
  }

  async function initAuth(): Promise<void> {
    if (isInitialized.value) return

    isLoading.value = true
    try {
      // Try to refresh token on app start (if refresh token cookie exists)
      const response = await authApi.refresh()
      accessToken.value = response.accessToken
      // Fetch user info after successful refresh
      const userData = await authApi.getCurrentUser()
      user.value = userData
    } catch {
      // No valid session - that's ok, user will need to login
      clearAuth()
    } finally {
      isLoading.value = false
      isInitialized.value = true
    }
  }

  async function fetchCurrentUser(): Promise<void> {
    if (!accessToken.value) return

    try {
      const userData = await authApi.getCurrentUser()
      user.value = userData
    } catch {
      // If fetching user fails, clear auth
      clearAuth()
    }
  }

  return {
    // State
    accessToken,
    user,
    isLoading,
    error,
    isInitialized,
    // Getters
    isAuthenticated,
    isAdmin,
    // Actions
    setAccessToken,
    setUser,
    clearAuth,
    setLoading,
    setError,
    login,
    register,
    logout,
    initAuth,
    fetchCurrentUser,
  }
})
