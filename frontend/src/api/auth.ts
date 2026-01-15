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

export async function refresh(): Promise<RefreshResponse> {
  return post<RefreshResponse>('/auth/refresh')
}

export async function getCurrentUser(): Promise<User> {
  return get<User>('/auth/me')
}
