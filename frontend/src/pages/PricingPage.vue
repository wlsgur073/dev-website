<script setup lang="ts">
import DefaultLayout from '@/layouts/DefaultLayout.vue'
import { CheckIcon } from '@heroicons/vue/24/outline'

const plans = [
  {
    name: 'Free',
    price: '$0',
    description: 'Perfect for getting started',
    features: ['1,000 API calls/month', 'Basic support', 'Community access'],
    cta: 'Get Started',
    highlighted: false,
  },
  {
    name: 'Pro',
    price: '$29',
    description: 'For growing projects',
    features: ['50,000 API calls/month', 'Priority support', 'Advanced analytics', 'Custom integrations'],
    cta: 'Start Free Trial',
    highlighted: true,
  },
  {
    name: 'Enterprise',
    price: 'Custom',
    description: 'For large-scale applications',
    features: ['Unlimited API calls', 'Dedicated support', 'SLA guarantee', 'Custom contracts'],
    cta: 'Contact Sales',
    highlighted: false,
  },
]
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-16">
      <div class="text-center mb-12">
        <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-4">
          Simple, Transparent Pricing
        </h1>
        <p class="text-gray-600 dark:text-gray-400 max-w-2xl mx-auto">
          Choose the plan that works best for you. All plans include core features.
        </p>
      </div>

      <div class="grid md:grid-cols-3 gap-8 max-w-5xl mx-auto">
        <div
          v-for="plan in plans"
          :key="plan.name"
          class="relative bg-white dark:bg-gray-800 rounded-2xl border p-8"
          :class="[
            plan.highlighted
              ? 'border-blue-500 shadow-lg shadow-blue-500/10'
              : 'border-gray-200 dark:border-gray-700'
          ]"
        >
          <div v-if="plan.highlighted" class="absolute -top-4 left-1/2 -translate-x-1/2">
            <span class="bg-blue-600 text-white text-sm font-medium px-3 py-1 rounded-full">
              Most Popular
            </span>
          </div>

          <h2 class="text-xl font-bold text-gray-900 dark:text-white">{{ plan.name }}</h2>
          <p class="text-gray-600 dark:text-gray-400 mt-2">{{ plan.description }}</p>

          <div class="mt-6">
            <span class="text-4xl font-bold text-gray-900 dark:text-white">{{ plan.price }}</span>
            <span v-if="plan.price !== 'Custom'" class="text-gray-600 dark:text-gray-400">/month</span>
          </div>

          <ul class="mt-8 space-y-4">
            <li v-for="feature in plan.features" :key="feature" class="flex items-center gap-3">
              <CheckIcon class="w-5 h-5 text-green-500 flex-shrink-0" />
              <span class="text-gray-600 dark:text-gray-400">{{ feature }}</span>
            </li>
          </ul>

          <button
            class="w-full mt-8 btn"
            :class="plan.highlighted ? 'btn-primary' : 'btn-secondary'"
          >
            {{ plan.cta }}
          </button>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
