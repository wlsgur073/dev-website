import { get, post, put, del } from './http'

export interface Announcement {
  id: number
  title: string
  content: string
  summary?: string
  category: 'general' | 'update' | 'maintenance' | 'security'
  pinned: boolean
  createdAt: string
  updatedAt: string
}

export interface AnnouncementsResponse {
  content: Announcement[]
  totalElements: number
  totalPages: number
  page: number
  size: number
}

export interface AnnouncementsParams {
  page?: number
  size?: number
  category?: string
}

export interface CreateAnnouncementRequest {
  title: string
  content: string
  summary?: string
  category: Announcement['category']
  pinned?: boolean
}

export interface UpdateAnnouncementRequest {
  title?: string
  content?: string
  summary?: string
  category?: Announcement['category']
  pinned?: boolean
}

// Public APIs
export async function getAnnouncements(params?: AnnouncementsParams): Promise<AnnouncementsResponse> {
  return get<AnnouncementsResponse>('/announcements', { params })
}

export async function getAnnouncement(id: number): Promise<Announcement> {
  return get<Announcement>(`/announcements/${id}`)
}

export async function getLatestAnnouncements(limit = 3): Promise<Announcement[]> {
  const response = await get<AnnouncementsResponse>('/announcements', {
    params: { page: 0, size: limit },
  })
  return response.content
}

// Admin APIs
export async function createAnnouncement(data: CreateAnnouncementRequest): Promise<Announcement> {
  return post<Announcement>('/admin/announcements', data)
}

export async function updateAnnouncement(id: number, data: UpdateAnnouncementRequest): Promise<Announcement> {
  return put<Announcement>(`/admin/announcements/${id}`, data)
}

export async function deleteAnnouncement(id: number): Promise<void> {
  return del<void>(`/admin/announcements/${id}`)
}
