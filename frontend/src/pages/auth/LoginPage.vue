<script setup lang="ts">
import { ref, computed } from 'vue'
import { RouterLink, useRouter, useRoute } from 'vue-router'
import AuthLayout from '@/layouts/AuthLayout.vue'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

const email = ref('')
const password = ref('')

const isLoading = computed(() => authStore.isLoading)
const error = computed(() => authStore.error)

async function handleSubmit() {
  const success = await authStore.login({
    email: email.value,
    password: password.value,
  })

  if (success) {
    // Redirect to intended page or console
    const redirect = route.query.redirect as string | undefined
    router.push(redirect || '/console')
  }
}
</script>

<template>
  <AuthLayout>
    <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-8">
      <h1 class="text-2xl font-bold text-gray-900 dark:text-white mb-2">Welcome back</h1>
      <p class="text-gray-600 dark:text-gray-400 mb-6">Sign in to your account</p>

      <div
        v-if="error"
        class="mb-4 p-3 bg-red-50 dark:bg-red-900/50 border border-red-200 dark:border-red-800 rounded-lg text-red-700 dark:text-red-300 text-sm"
        role="alert"
      >
        {{ error }}
      </div>

      <form @submit.prevent="handleSubmit" class="space-y-4">
        <div>
          <label for="email" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            Email
          </label>
          <input
            id="email"
            v-model="email"
            type="email"
            required
            autocomplete="email"
            class="w-full px-4 py-2 bg-white dark:bg-gray-900 border border-gray-300 dark:border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-900 dark:text-white"
            placeholder="you@example.com"
          />
        </div>

        <div>
          <label for="password" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            Password
          </label>
          <input
            id="password"
            v-model="password"
            type="password"
            required
            autocomplete="current-password"
            class="w-full px-4 py-2 bg-white dark:bg-gray-900 border border-gray-300 dark:border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-900 dark:text-white"
            placeholder="••••••••"
          />
        </div>

        <button
          type="submit"
          :disabled="isLoading"
          class="w-full btn btn-primary disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {{ isLoading ? 'Signing in...' : 'Sign in' }}
        </button>
      </form>

      <p class="mt-6 text-center text-sm text-gray-600 dark:text-gray-400">
        Don't have an account?
        <RouterLink to="/register" class="text-blue-600 dark:text-blue-400 hover:underline">
          Sign up
        </RouterLink>
      </p>
    </div>
  </AuthLayout>
</template>
