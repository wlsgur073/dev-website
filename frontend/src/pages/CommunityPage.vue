<script setup lang="ts">
import DefaultLayout from '@/layouts/DefaultLayout.vue'
import { ChatBubbleLeftRightIcon, CodeBracketIcon, BookOpenIcon } from '@heroicons/vue/24/outline'

const resources = [
  {
    title: 'Discord Community',
    description: 'Join our Discord server to chat with other developers and get help.',
    icon: ChatBubbleLeftRightIcon,
    link: '#',
    linkText: 'Join Discord',
  },
  {
    title: 'GitHub Discussions',
    description: 'Ask questions, share ideas, and discuss features on GitHub.',
    icon: CodeBracketIcon,
    link: '#',
    linkText: 'View Discussions',
  },
  {
    title: 'Blog',
    description: 'Read tutorials, case studies, and product updates from our team.',
    icon: BookOpenIcon,
    link: '#',
    linkText: 'Read Blog',
  },
]
</script>

<template>
  <DefaultLayout>
    <div class="container-custom py-16">
      <div class="text-center mb-12">
        <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-4">Community</h1>
        <p class="text-gray-600 dark:text-gray-400 max-w-2xl mx-auto">
          Connect with developers, share your projects, and learn from others.
        </p>
      </div>

      <div class="grid md:grid-cols-3 gap-8 max-w-4xl mx-auto">
        <div
          v-for="resource in resources"
          :key="resource.title"
          class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6 text-center"
        >
          <div class="w-12 h-12 bg-blue-100 dark:bg-blue-900 rounded-lg flex items-center justify-center mx-auto mb-4">
            <component :is="resource.icon" class="w-6 h-6 text-blue-600 dark:text-blue-400" />
          </div>
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">
            {{ resource.title }}
          </h2>
          <p class="text-gray-600 dark:text-gray-400 text-sm mb-4">
            {{ resource.description }}
          </p>
          <a :href="resource.link" class="text-blue-600 dark:text-blue-400 hover:underline text-sm font-medium">
            {{ resource.linkText }} →
          </a>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
