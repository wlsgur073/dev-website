<script setup lang="ts">
import { ref } from 'vue'
import { RouterLink } from 'vue-router'
import AuthLayout from '@/layouts/AuthLayout.vue'

const email = ref('')
const password = ref('')
const isLoading = ref(false)

async function handleSubmit() {
  isLoading.value = true
  // Login logic will be implemented in Phase 1
  console.log('Login:', { email: email.value, password: password.value })
  isLoading.value = false
}
</script>

<template>
  <AuthLayout>
    <div class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-8">
      <h1 class="text-2xl font-bold text-gray-900 dark:text-white mb-2">Welcome back</h1>
      <p class="text-gray-600 dark:text-gray-400 mb-6">Sign in to your account</p>

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
            class="w-full px-4 py-2 bg-white dark:bg-gray-900 border border-gray-300 dark:border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-900 dark:text-white"
            placeholder="••••••••"
          />
        </div>

        <button
          type="submit"
          :disabled="isLoading"
          class="w-full btn btn-primary"
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
