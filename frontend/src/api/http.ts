import axios, { type AxiosError, type AxiosRequestConfig, type InternalAxiosRequestConfig } from 'axios'
import { useAuthStore } from '@/stores/auth'

// Extended config type to include retry flag
interface ExtendedAxiosRequestConfig extends InternalAxiosRequestConfig {
  _retry?: boolean
}

// Create axios instance
const http = axios.create({
  baseURL: '/api',
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true, // Required for HttpOnly cookies (refresh token)
})

// Refresh token lock and queue for handling concurrent 401s
let isRefreshing = false
let refreshSubscribers: Array<(token: string) => void> = []

function subscribeTokenRefresh(callback: (token: string) => void) {
  refreshSubscribers.push(callback)
}

function onTokenRefreshed(token: string) {
  refreshSubscribers.forEach((callback) => callback(token))
  refreshSubscribers = []
}

function onRefreshFailed() {
  refreshSubscribers = []
}

// Request interceptor - attach access token
http.interceptors.request.use(
  (config) => {
    const authStore = useAuthStore()
    if (authStore.accessToken) {
      config.headers.Authorization = `Bearer ${authStore.accessToken}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor - handle 401 errors
http.interceptors.response.use(
  (response) => response,
  async (error: AxiosError) => {
    const originalRequest = error.config as ExtendedAxiosRequestConfig | undefined

    // If no config or already retried, reject
    if (!originalRequest) {
      return Promise.reject(error)
    }

    // Only handle 401 errors
    if (error.response?.status !== 401) {
      return Promise.reject(error)
    }

    // Prevent infinite loop - if already retried, redirect to login
    if (originalRequest._retry) {
      const authStore = useAuthStore()
      authStore.clearAuth()
      window.location.href = '/login'
      return Promise.reject(error)
    }

    // If refresh is already in progress, queue this request
    if (isRefreshing) {
      return new Promise((resolve, reject) => {
        subscribeTokenRefresh((token: string) => {
          originalRequest.headers.Authorization = `Bearer ${token}`
          resolve(http(originalRequest))
        })
        // If refresh fails, the promise will never resolve
        // We need to handle this by checking if refresh failed
        setTimeout(() => {
          reject(error)
        }, 30000) // Timeout after 30 seconds
      })
    }

    // Mark as retrying and start refresh
    originalRequest._retry = true
    isRefreshing = true

    try {
      // Call refresh endpoint - refresh token is sent via HttpOnly cookie
      const response = await axios.post('/api/auth/refresh', {}, {
        withCredentials: true,
      })

      const { accessToken } = response.data
      const authStore = useAuthStore()
      authStore.setAccessToken(accessToken)

      // Update authorization header
      originalRequest.headers.Authorization = `Bearer ${accessToken}`

      // Notify queued requests
      onTokenRefreshed(accessToken)
      isRefreshing = false

      // Retry original request
      return http(originalRequest)
    } catch (refreshError) {
      // Refresh failed - clear auth and redirect to login
      isRefreshing = false
      onRefreshFailed()

      const authStore = useAuthStore()
      authStore.clearAuth()
      window.location.href = '/login'

      return Promise.reject(refreshError)
    }
  }
)

// Export typed request methods
export async function get<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
  const response = await http.get<T>(url, config)
  return response.data
}

export async function post<T>(url: string, data?: unknown, config?: AxiosRequestConfig): Promise<T> {
  const response = await http.post<T>(url, data, config)
  return response.data
}

export async function put<T>(url: string, data?: unknown, config?: AxiosRequestConfig): Promise<T> {
  const response = await http.put<T>(url, data, config)
  return response.data
}

export async function patch<T>(url: string, data?: unknown, config?: AxiosRequestConfig): Promise<T> {
  const response = await http.patch<T>(url, data, config)
  return response.data
}

export async function del<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
  const response = await http.delete<T>(url, config)
  return response.data
}

export default http
