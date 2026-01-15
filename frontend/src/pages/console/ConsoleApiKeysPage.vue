<script setup lang="ts">
import { ref, onMounted } from 'vue'
import ConsoleLayout from '@/layouts/ConsoleLayout.vue'
import {
  getApiKeys,
  createApiKey,
  deleteApiKey,
  type ApiKey,
  type ApiKeyWithSecret,
} from '@/api/apikeys'
import {
  PlusIcon,
  TrashIcon,
  ClipboardIcon,
  CheckIcon,
  KeyIcon,
  ExclamationTriangleIcon,
  XMarkIcon,
} from '@heroicons/vue/24/outline'

const apiKeys = ref<ApiKey[]>([])
const isLoading = ref(true)
const error = ref<string | null>(null)

// Modal state
const showCreateModal = ref(false)
const showDeleteModal = ref(false)
const showSecretModal = ref(false)

// Form state
const newKeyName = ref('')
const isCreating = ref(false)
const createError = ref<string | null>(null)

// Delete state
const keyToDelete = ref<ApiKey | null>(null)
const isDeleting = ref(false)

// New key secret (shown only once)
const newKeySecret = ref<ApiKeyWithSecret | null>(null)
const copied = ref(false)

async function loadApiKeys() {
  isLoading.value = true
  error.value = null

  try {
    const response = await getApiKeys()
    apiKeys.value = response.content
  } catch {
    error.value = 'Failed to load API keys'
  } finally {
    isLoading.value = false
  }
}

async function handleCreateKey() {
  if (!newKeyName.value.trim()) {
    createError.value = 'Please enter a name for the API key'
    return
  }

  isCreating.value = true
  createError.value = null

  try {
    const newKey = await createApiKey({ name: newKeyName.value.trim() })
    newKeySecret.value = newKey
    showCreateModal.value = false
    showSecretModal.value = true
    newKeyName.value = ''
    await loadApiKeys()
  } catch {
    createError.value = 'Failed to create API key'
  } finally {
    isCreating.value = false
  }
}

async function handleDeleteKey() {
  if (!keyToDelete.value) return

  isDeleting.value = true

  try {
    await deleteApiKey(keyToDelete.value.id)
    showDeleteModal.value = false
    keyToDelete.value = null
    await loadApiKeys()
  } catch {
    // Error handling
  } finally {
    isDeleting.value = false
  }
}

function openDeleteModal(key: ApiKey) {
  keyToDelete.value = key
  showDeleteModal.value = true
}

function closeModals() {
  showCreateModal.value = false
  showDeleteModal.value = false
  showSecretModal.value = false
  keyToDelete.value = null
  newKeySecret.value = null
  newKeyName.value = ''
  createError.value = null
  copied.value = false
}

async function copyToClipboard(text: string) {
  try {
    await navigator.clipboard.writeText(text)
    copied.value = true
    setTimeout(() => {
      copied.value = false
    }, 2000)
  } catch {
    // Clipboard API not available
  }
}

function formatDate(dateString: string): string {
  return new Date(dateString).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
  })
}

onMounted(loadApiKeys)
</script>

<template>
  <ConsoleLayout>
    <div>
      <!-- Header -->
      <div class="flex items-center justify-between mb-6">
        <div>
          <h1 class="text-2xl font-bold text-gray-900 dark:text-white">API Keys</h1>
          <p class="text-gray-600 dark:text-gray-400 mt-1">
            Manage your API keys for authentication.
          </p>
        </div>
        <button
          type="button"
          class="btn btn-primary inline-flex items-center gap-2"
          @click="showCreateModal = true"
        >
          <PlusIcon class="w-5 h-5" />
          Create New Key
        </button>
      </div>

      <!-- Loading state -->
      <div v-if="isLoading" class="space-y-4">
        <div v-for="i in 3" :key="i" class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6 animate-pulse">
          <div class="flex items-center justify-between">
            <div class="space-y-2">
              <div class="h-5 bg-gray-200 dark:bg-gray-700 rounded w-32"></div>
              <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-48"></div>
            </div>
            <div class="h-8 bg-gray-200 dark:bg-gray-700 rounded w-20"></div>
          </div>
        </div>
      </div>

      <!-- Error state -->
      <div v-else-if="error" class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-8 text-center">
        <KeyIcon class="w-12 h-12 mx-auto text-gray-400 mb-4" />
        <p class="text-gray-600 dark:text-gray-400 mb-4">{{ error }}</p>
        <button type="button" class="btn btn-primary" @click="loadApiKeys">
          Try Again
        </button>
      </div>

      <!-- Empty state -->
      <div v-else-if="apiKeys.length === 0" class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-8 text-center">
        <KeyIcon class="w-12 h-12 mx-auto text-gray-400 mb-4" />
        <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">No API keys yet</h2>
        <p class="text-gray-600 dark:text-gray-400 mb-4">
          Create your first API key to start using the API.
        </p>
        <button
          type="button"
          class="btn btn-primary inline-flex items-center gap-2"
          @click="showCreateModal = true"
        >
          <PlusIcon class="w-5 h-5" />
          Create New Key
        </button>
      </div>

      <!-- API Keys list -->
      <div v-else class="space-y-4">
        <div
          v-for="key in apiKeys"
          :key="key.id"
          class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6"
        >
          <div class="flex items-center justify-between">
            <div class="flex-1 min-w-0">
              <div class="flex items-center gap-3">
                <h3 class="text-lg font-medium text-gray-900 dark:text-white">
                  {{ key.name }}
                </h3>
              </div>
              <div class="flex items-center gap-4 mt-2 text-sm text-gray-500 dark:text-gray-400">
                <code class="px-2 py-1 bg-gray-100 dark:bg-gray-700 rounded text-xs">
                  {{ key.keyPrefix }}...
                </code>
                <span>Created {{ formatDate(key.createdAt) }}</span>
                <span v-if="key.lastUsedAt">
                  Last used {{ formatDate(key.lastUsedAt) }}
                </span>
                <span v-else class="text-gray-400 dark:text-gray-500">
                  Never used
                </span>
              </div>
            </div>
            <button
              type="button"
              class="p-2 text-gray-400 hover:text-red-500 transition-colors"
              title="Delete API key"
              @click="openDeleteModal(key)"
            >
              <TrashIcon class="w-5 h-5" />
            </button>
          </div>
        </div>
      </div>

      <!-- Create Modal -->
      <Teleport to="body">
        <div
          v-if="showCreateModal"
          class="fixed inset-0 z-50 flex items-center justify-center p-4"
        >
          <div class="fixed inset-0 bg-black/50" @click="closeModals"></div>
          <div class="relative bg-white dark:bg-gray-800 rounded-xl shadow-xl max-w-md w-full p-6">
            <button
              type="button"
              class="absolute top-4 right-4 text-gray-400 hover:text-gray-600 dark:hover:text-gray-300"
              @click="closeModals"
            >
              <XMarkIcon class="w-5 h-5" />
            </button>

            <h2 class="text-xl font-bold text-gray-900 dark:text-white mb-4">
              Create New API Key
            </h2>

            <form @submit.prevent="handleCreateKey">
              <div class="mb-4">
                <label for="keyName" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  Key Name
                </label>
                <input
                  id="keyName"
                  v-model="newKeyName"
                  type="text"
                  placeholder="e.g., Production, Development"
                  class="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  :disabled="isCreating"
                />
                <p v-if="createError" class="mt-2 text-sm text-red-500">
                  {{ createError }}
                </p>
              </div>

              <div class="flex justify-end gap-3">
                <button
                  type="button"
                  class="btn btn-secondary"
                  :disabled="isCreating"
                  @click="closeModals"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  class="btn btn-primary"
                  :disabled="isCreating"
                >
                  {{ isCreating ? 'Creating...' : 'Create Key' }}
                </button>
              </div>
            </form>
          </div>
        </div>
      </Teleport>

      <!-- Secret Modal (shown after creating) -->
      <Teleport to="body">
        <div
          v-if="showSecretModal && newKeySecret"
          class="fixed inset-0 z-50 flex items-center justify-center p-4"
        >
          <div class="fixed inset-0 bg-black/50"></div>
          <div class="relative bg-white dark:bg-gray-800 rounded-xl shadow-xl max-w-lg w-full p-6">
            <div class="flex items-center gap-3 mb-4">
              <div class="w-10 h-10 bg-green-100 dark:bg-green-900 rounded-full flex items-center justify-center">
                <CheckIcon class="w-5 h-5 text-green-600 dark:text-green-400" />
              </div>
              <h2 class="text-xl font-bold text-gray-900 dark:text-white">
                API Key Created
              </h2>
            </div>

            <div class="bg-yellow-50 dark:bg-yellow-900/30 border border-yellow-200 dark:border-yellow-800 rounded-lg p-4 mb-4">
              <div class="flex items-start gap-2">
                <ExclamationTriangleIcon class="w-5 h-5 text-yellow-600 dark:text-yellow-400 flex-shrink-0 mt-0.5" />
                <p class="text-sm text-yellow-800 dark:text-yellow-200">
                  Make sure to copy your API key now. You won't be able to see it again!
                </p>
              </div>
            </div>

            <div class="mb-6">
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                Your API Key
              </label>
              <div class="flex items-center gap-2">
                <code class="flex-1 px-4 py-3 bg-gray-100 dark:bg-gray-700 rounded-lg text-sm font-mono break-all">
                  {{ newKeySecret.key }}
                </code>
                <button
                  type="button"
                  class="p-3 bg-gray-100 dark:bg-gray-700 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors"
                  :title="copied ? 'Copied!' : 'Copy to clipboard'"
                  @click="copyToClipboard(newKeySecret.key)"
                >
                  <CheckIcon v-if="copied" class="w-5 h-5 text-green-500" />
                  <ClipboardIcon v-else class="w-5 h-5 text-gray-600 dark:text-gray-400" />
                </button>
              </div>
            </div>

            <button
              type="button"
              class="btn btn-primary w-full"
              @click="closeModals"
            >
              Done
            </button>
          </div>
        </div>
      </Teleport>

      <!-- Delete Confirmation Modal -->
      <Teleport to="body">
        <div
          v-if="showDeleteModal && keyToDelete"
          class="fixed inset-0 z-50 flex items-center justify-center p-4"
        >
          <div class="fixed inset-0 bg-black/50" @click="closeModals"></div>
          <div class="relative bg-white dark:bg-gray-800 rounded-xl shadow-xl max-w-md w-full p-6">
            <div class="flex items-center gap-3 mb-4">
              <div class="w-10 h-10 bg-red-100 dark:bg-red-900 rounded-full flex items-center justify-center">
                <ExclamationTriangleIcon class="w-5 h-5 text-red-600 dark:text-red-400" />
              </div>
              <h2 class="text-xl font-bold text-gray-900 dark:text-white">
                Delete API Key
              </h2>
            </div>

            <p class="text-gray-600 dark:text-gray-400 mb-6">
              Are you sure you want to delete the API key "<strong>{{ keyToDelete.name }}</strong>"?
              This action cannot be undone, and any applications using this key will stop working.
            </p>

            <div class="flex justify-end gap-3">
              <button
                type="button"
                class="btn btn-secondary"
                :disabled="isDeleting"
                @click="closeModals"
              >
                Cancel
              </button>
              <button
                type="button"
                class="btn bg-red-600 text-white hover:bg-red-700"
                :disabled="isDeleting"
                @click="handleDeleteKey"
              >
                {{ isDeleting ? 'Deleting...' : 'Delete Key' }}
              </button>
            </div>
          </div>
        </div>
      </Teleport>
    </div>
  </ConsoleLayout>
</template>
