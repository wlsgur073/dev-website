<script setup lang="ts">
import ConsoleLayout from '@/layouts/ConsoleLayout.vue'
import {
  CheckIcon,
  SparklesIcon,
  CreditCardIcon,
} from '@heroicons/vue/24/outline'

// Prototype plans data (will be fetched from API in future)
const plans = [
  {
    id: 'free',
    name: 'Free',
    price: 0,
    description: 'For hobby projects and experimentation',
    features: [
      '1,000 API requests/month',
      '60 requests/minute',
      '1 API key',
      'Community support',
    ],
    current: true,
  },
  {
    id: 'starter',
    name: 'Starter',
    price: 29,
    description: 'For small teams and growing projects',
    features: [
      '10,000 API requests/month',
      '300 requests/minute',
      '5 API keys',
      'Email support',
      'Usage analytics',
    ],
    popular: true,
  },
  {
    id: 'pro',
    name: 'Pro',
    price: 99,
    description: 'For professional teams and high-traffic apps',
    features: [
      '100,000 API requests/month',
      '1,000 requests/minute',
      'Unlimited API keys',
      'Priority support',
      'Advanced analytics',
      'Custom integrations',
    ],
  },
]
</script>

<template>
  <ConsoleLayout>
    <div>
      <!-- Header -->
      <div class="mb-8">
        <h1 class="text-2xl font-bold text-gray-900 dark:text-white">Billing</h1>
        <p class="text-gray-600 dark:text-gray-400 mt-1">
          Manage your subscription and billing information.
        </p>
      </div>

      <!-- Current Plan -->
      <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6 mb-8">
        <div class="flex items-center justify-between">
          <div>
            <h2 class="text-lg font-semibold text-gray-900 dark:text-white">Current Plan</h2>
            <div class="flex items-center gap-2 mt-2">
              <span class="text-3xl font-bold text-gray-900 dark:text-white">Free</span>
              <span class="text-sm px-2 py-1 bg-green-100 dark:bg-green-900 text-green-700 dark:text-green-300 rounded">
                Active
              </span>
            </div>
            <p class="text-gray-600 dark:text-gray-400 mt-2">
              1,000 API requests per month
            </p>
          </div>
          <div class="text-right">
            <p class="text-sm text-gray-500 dark:text-gray-400">Next billing date</p>
            <p class="text-lg font-medium text-gray-900 dark:text-white">N/A (Free plan)</p>
          </div>
        </div>
      </div>

      <!-- Usage -->
      <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6 mb-8">
        <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">Current Usage</h2>
        <div class="space-y-4">
          <div>
            <div class="flex items-center justify-between mb-2">
              <span class="text-sm text-gray-600 dark:text-gray-400">API Requests (This Month)</span>
              <span class="text-sm font-medium text-gray-900 dark:text-white">0 / 1,000</span>
            </div>
            <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-2">
              <div class="bg-blue-600 h-2 rounded-full" style="width: 0%"></div>
            </div>
          </div>
        </div>
      </div>

      <!-- Available Plans -->
      <div class="mb-8">
        <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">Available Plans</h2>
        <div class="grid md:grid-cols-3 gap-6">
          <div
            v-for="plan in plans"
            :key="plan.id"
            :class="[
              'bg-white dark:bg-gray-800 rounded-xl border-2 p-6 relative',
              plan.popular
                ? 'border-blue-500'
                : plan.current
                ? 'border-green-500'
                : 'border-gray-200 dark:border-gray-700'
            ]"
          >
            <!-- Popular badge -->
            <div
              v-if="plan.popular"
              class="absolute -top-3 left-1/2 -translate-x-1/2 px-3 py-1 bg-blue-600 text-white text-xs font-medium rounded-full flex items-center gap-1"
            >
              <SparklesIcon class="w-3 h-3" />
              Popular
            </div>

            <!-- Current badge -->
            <div
              v-if="plan.current"
              class="absolute -top-3 left-1/2 -translate-x-1/2 px-3 py-1 bg-green-600 text-white text-xs font-medium rounded-full"
            >
              Current Plan
            </div>

            <h3 class="text-xl font-bold text-gray-900 dark:text-white">{{ plan.name }}</h3>
            <div class="mt-2">
              <span class="text-3xl font-bold text-gray-900 dark:text-white">${{ plan.price }}</span>
              <span class="text-gray-500 dark:text-gray-400">/month</span>
            </div>
            <p class="text-sm text-gray-600 dark:text-gray-400 mt-2">{{ plan.description }}</p>

            <ul class="mt-6 space-y-3">
              <li
                v-for="feature in plan.features"
                :key="feature"
                class="flex items-start gap-2 text-sm"
              >
                <CheckIcon class="w-5 h-5 text-green-500 flex-shrink-0" />
                <span class="text-gray-600 dark:text-gray-400">{{ feature }}</span>
              </li>
            </ul>

            <button
              v-if="plan.current"
              type="button"
              class="w-full mt-6 btn btn-secondary"
              disabled
            >
              Current Plan
            </button>
            <button
              v-else
              type="button"
              class="w-full mt-6 btn"
              :class="plan.popular ? 'btn-primary' : 'btn-secondary'"
              disabled
            >
              Coming Soon
            </button>
          </div>
        </div>
      </div>

      <!-- Payment Method (Prototype) -->
      <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6 mb-8">
        <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">Payment Method</h2>
        <div class="flex items-center justify-center py-8 text-center">
          <div>
            <CreditCardIcon class="w-12 h-12 mx-auto text-gray-400 mb-3" />
            <p class="text-gray-600 dark:text-gray-400">
              No payment method on file.
            </p>
            <p class="text-sm text-gray-500 dark:text-gray-500 mt-1">
              Payment functionality coming soon.
            </p>
          </div>
        </div>
      </div>

      <!-- Billing History (Prototype) -->
      <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
        <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">Billing History</h2>
        <div class="text-center py-8">
          <p class="text-gray-600 dark:text-gray-400">No billing history available.</p>
          <p class="text-sm text-gray-500 dark:text-gray-500 mt-1">
            Your invoices will appear here once you upgrade to a paid plan.
          </p>
        </div>
      </div>
    </div>
  </ConsoleLayout>
</template>
