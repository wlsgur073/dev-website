import axios from 'axios'
import { post, get } from './http'
import type { User } from '@/stores/auth'

// Request/Response types
export interface LoginRequest {
  email: string
  password: string
}

export interface RegisterRequest {
  name: string
  email: string
  password: string
}

export interface AuthResponse {
  accessToken: string
  user: User
}

export interface RefreshResponse {
  accessToken: string
}

// Auth API functions
export async function login(data: LoginRequest): Promise<AuthResponse> {
  return post<AuthResponse>('/auth/login', data)
}

export async function register(data: RegisterRequest): Promise<AuthResponse> {
  return post<AuthResponse>('/auth/register', data)
}

export async function logout(): Promise<void> {
  return post<void>('/auth/logout')
}

// Use axios directly to bypass the 401 interceptor
// The refresh endpoint itself should not trigger token refresh logic
export async function refresh(): Promise<RefreshResponse> {
  const response = await axios.post<RefreshResponse>('/api/v1/auth/refresh', {}, {
    withCredentials: true,
  })
  return response.data
}

export async function getCurrentUser(): Promise<User> {
  return get<User>('/me')
}
