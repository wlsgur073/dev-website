import { get } from './http'

export interface Subscription {
  id: number
  planId: string
  planName: string
  status: 'active' | 'canceled' | 'past_due' | 'trialing'
  currentPeriodStart: string
  currentPeriodEnd: string
  cancelAtPeriodEnd: boolean
}

export interface UsageStats {
  requestsToday: number
  requestsThisMonth: number
  dailyLimit: number
  monthlyLimit: number
  lastRequestAt: string | null
}

// Get current user's subscription (placeholder for prototype)
export async function getCurrentSubscription(): Promise<Subscription | null> {
  return get<Subscription | null>('/subscription')
}

// Get usage statistics (placeholder for prototype)
export async function getUsageStats(): Promise<UsageStats> {
  return get<UsageStats>('/subscription/usage')
}
