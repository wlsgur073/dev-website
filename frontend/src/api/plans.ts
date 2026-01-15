import { get } from './http'

export interface Plan {
  id: string
  name: string
  description: string
  price: number // Monthly price in cents
  currency: string
  features: string[]
  limits: {
    requestsPerDay: number
    requestsPerMonth: number
    apiKeys: number
  }
  popular?: boolean
}

// Get all available plans (placeholder for prototype)
export async function getPlans(): Promise<Plan[]> {
  return get<Plan[]>('/plans')
}

// Get plan by ID (placeholder for prototype)
export async function getPlan(id: string): Promise<Plan> {
  return get<Plan>(`/plans/${id}`)
}
