import { get, post, put, del } from './http'

export interface Release {
  id: number
  version: string
  title: string
  content: string
  summary?: string
  releaseDate: string
  type: 'major' | 'minor' | 'patch' | 'hotfix'
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
  summary?: string
  releaseDate: string
  type: Release['type']
}

export interface UpdateReleaseRequest {
  version?: string
  title?: string
  content?: string
  summary?: string
  releaseDate?: string
  type?: Release['type']
}

// Public APIs
export async function getReleases(params?: ReleasesParams): Promise<ReleasesResponse> {
  return get<ReleasesResponse>('/releases', { params })
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

// Admin APIs
export async function createRelease(data: CreateReleaseRequest): Promise<Release> {
  return post<Release>('/admin/releases', data)
}

export async function updateRelease(id: number, data: UpdateReleaseRequest): Promise<Release> {
  return put<Release>(`/admin/releases/${id}`, data)
}

export async function deleteRelease(id: number): Promise<void> {
  return del<void>(`/admin/releases/${id}`)
}
