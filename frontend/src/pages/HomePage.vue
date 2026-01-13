<script setup lang="ts">
import DefaultLayout from '@/layouts/DefaultLayout.vue'
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-16">
      <!-- Hero Section -->
      <section class="text-center py-20">
        <h1 class="text-4xl md:text-6xl font-bold text-gray-900 dark:text-white mb-6">
          Build Amazing Products
        </h1>
        <p class="text-xl text-gray-600 dark:text-gray-400 mb-8 max-w-2xl mx-auto">
          Powerful APIs, comprehensive documentation, and developer-first tools to help you build faster.
        </p>
        <div class="flex flex-wrap justify-center gap-4">
          <RouterLink to="/docs/quickstart" class="btn btn-primary">
            Quickstart
          </RouterLink>
          <RouterLink to="/docs" class="btn btn-secondary">
            Documentation
          </RouterLink>
          <RouterLink to="/console" class="btn btn-secondary">
            Console
          </RouterLink>
        </div>
      </section>

      <!-- Quickstart Cards -->
      <section class="py-16">
        <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-8 text-center">
          Get Started in Minutes
        </h2>
        <div class="grid md:grid-cols-3 gap-6">
          <div class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700">
            <div class="w-12 h-12 bg-blue-100 dark:bg-blue-900 rounded-lg flex items-center justify-center mb-4">
              <span class="text-2xl">1</span>
            </div>
            <h3 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Create Account</h3>
            <p class="text-gray-600 dark:text-gray-400">Sign up for free and get your API key instantly.</p>
          </div>
          <div class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700">
            <div class="w-12 h-12 bg-blue-100 dark:bg-blue-900 rounded-lg flex items-center justify-center mb-4">
              <span class="text-2xl">2</span>
            </div>
            <h3 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Get API Key</h3>
            <p class="text-gray-600 dark:text-gray-400">Generate your API key from the console dashboard.</p>
          </div>
          <div class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700">
            <div class="w-12 h-12 bg-blue-100 dark:bg-blue-900 rounded-lg flex items-center justify-center mb-4">
              <span class="text-2xl">3</span>
            </div>
            <h3 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">Start Building</h3>
            <p class="text-gray-600 dark:text-gray-400">Use our SDKs and APIs to build your application.</p>
          </div>
        </div>
      </section>

      <!-- Popular Docs -->
      <section class="py-16">
        <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-8 text-center">
          Popular Documentation
        </h2>
        <div class="grid md:grid-cols-2 lg:grid-cols-4 gap-4">
          <RouterLink to="/docs/quickstart" class="p-4 bg-white dark:bg-gray-800 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 transition-colors">
            <h3 class="font-medium text-gray-900 dark:text-white">Quickstart Guide</h3>
            <p class="text-sm text-gray-600 dark:text-gray-400 mt-1">Get started in 5 minutes</p>
          </RouterLink>
          <RouterLink to="/docs/authentication" class="p-4 bg-white dark:bg-gray-800 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 transition-colors">
            <h3 class="font-medium text-gray-900 dark:text-white">Authentication</h3>
            <p class="text-sm text-gray-600 dark:text-gray-400 mt-1">Learn about API authentication</p>
          </RouterLink>
          <RouterLink to="/docs/api-usage" class="p-4 bg-white dark:bg-gray-800 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 transition-colors">
            <h3 class="font-medium text-gray-900 dark:text-white">API Usage</h3>
            <p class="text-sm text-gray-600 dark:text-gray-400 mt-1">Complete API reference</p>
          </RouterLink>
          <RouterLink to="/docs/faq" class="p-4 bg-white dark:bg-gray-800 rounded-lg border border-gray-200 dark:border-gray-700 hover:border-blue-500 transition-colors">
            <h3 class="font-medium text-gray-900 dark:text-white">FAQ</h3>
            <p class="text-sm text-gray-600 dark:text-gray-400 mt-1">Frequently asked questions</p>
          </RouterLink>
        </div>
      </section>

      <!-- Latest Releases -->
      <section class="py-16">
        <div class="flex items-center justify-between mb-8">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white">
            Latest Releases
          </h2>
          <RouterLink to="/releases" class="text-blue-600 dark:text-blue-400 hover:underline">
            View all →
          </RouterLink>
        </div>
        <div class="grid md:grid-cols-3 gap-6">
          <div class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700">
            <span class="text-sm text-gray-500 dark:text-gray-400">Coming soon</span>
            <h3 class="text-lg font-semibold text-gray-900 dark:text-white mt-2">Latest releases will appear here</h3>
          </div>
        </div>
      </section>

      <!-- Latest Announcements -->
      <section class="py-16">
        <div class="flex items-center justify-between mb-8">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white">
            Announcements
          </h2>
          <RouterLink to="/announcements" class="text-blue-600 dark:text-blue-400 hover:underline">
            View all →
          </RouterLink>
        </div>
        <div class="grid md:grid-cols-3 gap-6">
          <div class="p-6 bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700">
            <span class="text-sm text-gray-500 dark:text-gray-400">Coming soon</span>
            <h3 class="text-lg font-semibold text-gray-900 dark:text-white mt-2">Latest announcements will appear here</h3>
          </div>
        </div>
      </section>

      <!-- Pricing CTA -->
      <section class="py-16">
        <div class="bg-gradient-to-r from-blue-600 to-indigo-600 rounded-2xl p-8 md:p-12 text-center text-white">
          <h2 class="text-3xl font-bold mb-4">Ready to get started?</h2>
          <p class="text-blue-100 mb-8 max-w-xl mx-auto">
            Choose the plan that works best for you. Start free and scale as you grow.
          </p>
          <RouterLink to="/pricing" class="inline-flex btn bg-white text-blue-600 hover:bg-blue-50">
            View Pricing
          </RouterLink>
        </div>
      </section>

      <!-- Community CTA -->
      <section class="py-16 text-center">
        <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-4">
          Join Our Community
        </h2>
        <p class="text-gray-600 dark:text-gray-400 mb-8 max-w-xl mx-auto">
          Connect with other developers, share your projects, and get help from the community.
        </p>
        <RouterLink to="/community" class="btn btn-secondary">
          Join Community
        </RouterLink>
      </section>
    </div>
  </DefaultLayout>
</template>
