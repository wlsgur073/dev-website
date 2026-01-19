import { get, post, patch, del } from './http'

export interface Release {
  id: number
  version: string
  title: string
  content: string
  releasedAt: string
  releaseType: 'MAJOR' | 'MINOR' | 'PATCH' | 'HOTFIX'
  createdAt: string
  updatedAt: string
}

export interface ReleasesResponse {
  content: Release[]
  totalElements: number
  totalPages: number
  page: number
  size: number
}

export interface ReleasesParams {
  page?: number
  size?: number
  type?: string
}

export interface CreateReleaseRequest {
  version: string
  title: string
  content: string
  releasedAt: string
  releaseType: Release['releaseType']
}

export interface UpdateReleaseRequest {
  version?: string
  title?: string
  content?: string
  releasedAt?: string
  releaseType?: Release['releaseType']
}

// Public APIs
export async function getReleases(params?: ReleasesParams): Promise<ReleasesResponse> {
  const apiParams = params ? {
    ...params,
    type: params.type?.toUpperCase(),
  } : undefined
  return get<ReleasesResponse>('/releases', { params: apiParams })
}

export async function getRelease(id: number): Promise<Release> {
  return get<Release>(`/releases/${id}`)
}

export async function getLatestReleases(limit = 3): Promise<Release[]> {
  const response = await get<ReleasesResponse>('/releases', {
    params: { page: 0, size: limit },
  })
  return response.content
}

// Helper to convert date string to LocalDateTime format
function toLocalDateTime(dateStr: string): string {
  // If already has time component, return as is
  if (dateStr.includes('T')) {
    return dateStr
  }
  // Add time component for LocalDateTime
  return `${dateStr}T00:00:00`
}

// Admin APIs
export async function createRelease(data: CreateReleaseRequest): Promise<Release> {
  const apiData = {
    ...data,
    releasedAt: toLocalDateTime(data.releasedAt),
  }
  return post<Release>('/admin/releases', apiData)
}

export async function updateRelease(id: number, data: UpdateReleaseRequest): Promise<Release> {
  const apiData = {
    ...data,
    releasedAt: data.releasedAt ? toLocalDateTime(data.releasedAt) : undefined,
  }
  return patch<Release>(`/admin/releases/${id}`, apiData)
}

export async function deleteRelease(id: number): Promise<void> {
  return del<void>(`/admin/releases/${id}`)
}
