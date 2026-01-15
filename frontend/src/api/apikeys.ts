import { get, post, del } from './http'

export interface ApiKey {
  id: number
  name: string
  keyPrefix: string // Only first 8 chars shown (e.g., "sk-abc123...")
  createdAt: string
  lastUsedAt: string | null
  expiresAt: string | null
}

export interface ApiKeyWithSecret extends ApiKey {
  key: string // Full key, only shown once on creation
}

export interface CreateApiKeyRequest {
  name: string
  expiresAt?: string | null
}

export interface ApiKeysResponse {
  content: ApiKey[]
  totalElements: number
}

// Get all API keys for current user
export async function getApiKeys(): Promise<ApiKeysResponse> {
  return get<ApiKeysResponse>('/api-keys')
}

// Create a new API key
export async function createApiKey(data: CreateApiKeyRequest): Promise<ApiKeyWithSecret> {
  return post<ApiKeyWithSecret>('/api-keys', data)
}

// Delete an API key
export async function deleteApiKey(id: number): Promise<void> {
  return del<void>(`/api-keys/${id}`)
}

// Regenerate an API key (delete old, create new with same name)
export async function regenerateApiKey(id: number): Promise<ApiKeyWithSecret> {
  return post<ApiKeyWithSecret>(`/api-keys/${id}/regenerate`)
}
