<script setup lang="ts">
import { ref, onMounted } from 'vue'
import AdminLayout from '@/layouts/AdminLayout.vue'
import Toast from '@/components/Toast.vue'
import { useToast } from '@/composables/useToast'
import {
  getReleases,
  createRelease,
  updateRelease,
  deleteRelease,
  type Release,
  type ReleasesResponse,
  type CreateReleaseRequest,
} from '@/api/releases'
import {
  PlusIcon,
  PencilIcon,
  TrashIcon,
  XMarkIcon,
  ExclamationTriangleIcon,
  RocketLaunchIcon,
} from '@heroicons/vue/24/outline'

const toast = useToast()

// List state
const releases = ref<Release[]>([])
const isLoading = ref(true)
const error = ref<string | null>(null)
const pagination = ref({
  page: 0,
  size: 10,
  totalElements: 0,
  totalPages: 0,
})

// Modal state
const showFormModal = ref(false)
const showDeleteModal = ref(false)
const isSubmitting = ref(false)
const editingRelease = ref<Release | null>(null)

// Form state
const form = ref<CreateReleaseRequest>({
  version: '',
  title: '',
  content: '',
  summary: '',
  releaseDate: new Date().toISOString().split('T')[0],
  type: 'minor',
})
const formErrors = ref<Record<string, string>>({})

// Delete state
const releaseToDelete = ref<Release | null>(null)

const releaseTypes = [
  { value: 'major', label: 'Major', color: 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-300' },
  { value: 'minor', label: 'Minor', color: 'bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-300' },
  { value: 'patch', label: 'Patch', color: 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-300' },
  { value: 'hotfix', label: 'Hotfix', color: 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-300' },
]

async function loadReleases() {
  isLoading.value = true
  error.value = null

  try {
    const response: ReleasesResponse = await getReleases({
      page: pagination.value.page,
      size: pagination.value.size,
    })
    releases.value = response.content
    pagination.value = {
      page: response.page,
      size: response.size,
      totalElements: response.totalElements,
      totalPages: response.totalPages,
    }
  } catch {
    error.value = 'Failed to load releases'
  } finally {
    isLoading.value = false
  }
}

function openCreateModal() {
  editingRelease.value = null
  form.value = {
    version: '',
    title: '',
    content: '',
    summary: '',
    releaseDate: new Date().toISOString().split('T')[0],
    type: 'minor',
  }
  formErrors.value = {}
  showFormModal.value = true
}

function openEditModal(release: Release) {
  editingRelease.value = release
  form.value = {
    version: release.version,
    title: release.title,
    content: release.content,
    summary: release.summary || '',
    releaseDate: release.releaseDate.split('T')[0],
    type: release.type,
  }
  formErrors.value = {}
  showFormModal.value = true
}

function openDeleteModal(release: Release) {
  releaseToDelete.value = release
  showDeleteModal.value = true
}

function closeModals() {
  showFormModal.value = false
  showDeleteModal.value = false
  editingRelease.value = null
  releaseToDelete.value = null
  formErrors.value = {}
}

function validateForm(): boolean {
  formErrors.value = {}

  if (!form.value.version.trim()) {
    formErrors.value.version = 'Version is required'
  } else if (!/^v?\d+\.\d+\.\d+/.test(form.value.version)) {
    formErrors.value.version = 'Version must be in format like 1.0.0 or v1.0.0'
  }
  if (!form.value.title.trim()) {
    formErrors.value.title = 'Title is required'
  }
  if (!form.value.content.trim()) {
    formErrors.value.content = 'Content is required'
  }
  if (!form.value.releaseDate) {
    formErrors.value.releaseDate = 'Release date is required'
  }

  return Object.keys(formErrors.value).length === 0
}

async function handleSubmit() {
  if (!validateForm()) return

  isSubmitting.value = true

  try {
    if (editingRelease.value) {
      await updateRelease(editingRelease.value.id, form.value)
      toast.success('Release updated successfully')
    } else {
      await createRelease(form.value)
      toast.success('Release created successfully')
    }
    closeModals()
    await loadReleases()
  } catch {
    toast.error(editingRelease.value ? 'Failed to update release' : 'Failed to create release')
  } finally {
    isSubmitting.value = false
  }
}

async function handleDelete() {
  if (!releaseToDelete.value) return

  isSubmitting.value = true

  try {
    await deleteRelease(releaseToDelete.value.id)
    toast.success('Release deleted successfully')
    closeModals()
    await loadReleases()
  } catch {
    toast.error('Failed to delete release')
  } finally {
    isSubmitting.value = false
  }
}

function formatDate(dateString: string): string {
  return new Date(dateString).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
  })
}

function getTypeBadgeClass(type: string): string {
  const found = releaseTypes.find(t => t.value === type)
  return found?.color || releaseTypes[1].color
}

function goToPage(page: number) {
  pagination.value.page = page
  loadReleases()
}

onMounted(loadReleases)
</script>

<template>
  <AdminLayout>
    <div>
      <!-- Toast -->
      <Toast :messages="toast.messages.value" @remove="toast.remove" />

      <!-- Header -->
      <div class="flex items-center justify-between mb-6">
        <h1 class="text-2xl font-bold text-gray-900 dark:text-white">Manage Releases</h1>
        <button
          type="button"
          class="btn btn-primary inline-flex items-center gap-2"
          @click="openCreateModal"
        >
          <PlusIcon class="w-5 h-5" />
          New Release
        </button>
      </div>

      <!-- Loading state -->
      <div v-if="isLoading" class="space-y-4">
        <div v-for="i in 5" :key="i" class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6 animate-pulse">
          <div class="flex items-center justify-between">
            <div class="space-y-2 flex-1">
              <div class="h-5 bg-gray-200 dark:bg-gray-700 rounded w-1/3"></div>
              <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-2/3"></div>
            </div>
            <div class="h-8 bg-gray-200 dark:bg-gray-700 rounded w-20"></div>
          </div>
        </div>
      </div>

      <!-- Error state -->
      <div v-else-if="error" class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-8 text-center">
        <RocketLaunchIcon class="w-12 h-12 mx-auto text-gray-400 mb-4" />
        <p class="text-gray-600 dark:text-gray-400 mb-4">{{ error }}</p>
        <button type="button" class="btn btn-primary" @click="loadReleases">
          Try Again
        </button>
      </div>

      <!-- Empty state -->
      <div v-else-if="releases.length === 0" class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-8 text-center">
        <RocketLaunchIcon class="w-12 h-12 mx-auto text-gray-400 mb-4" />
        <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">No releases yet</h2>
        <p class="text-gray-600 dark:text-gray-400 mb-4">
          Create your first release to get started.
        </p>
        <button
          type="button"
          class="btn btn-primary inline-flex items-center gap-2"
          @click="openCreateModal"
        >
          <PlusIcon class="w-5 h-5" />
          New Release
        </button>
      </div>

      <!-- Releases list -->
      <div v-else class="space-y-4">
        <div
          v-for="release in releases"
          :key="release.id"
          class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6"
        >
          <div class="flex items-start justify-between gap-4">
            <div class="flex-1 min-w-0">
              <div class="flex items-center gap-3 mb-2">
                <span class="px-2 py-1 text-sm font-mono font-semibold bg-gray-100 dark:bg-gray-700 text-gray-800 dark:text-gray-200 rounded">
                  {{ release.version }}
                </span>
                <h3 class="text-lg font-medium text-gray-900 dark:text-white truncate">
                  {{ release.title }}
                </h3>
                <span
                  :class="['px-2 py-0.5 text-xs font-medium rounded', getTypeBadgeClass(release.type)]"
                >
                  {{ release.type }}
                </span>
              </div>
              <p class="text-sm text-gray-600 dark:text-gray-400 line-clamp-2 mb-2">
                {{ release.summary || release.content.substring(0, 150) + '...' }}
              </p>
              <p class="text-xs text-gray-500 dark:text-gray-500">
                Released {{ formatDate(release.releaseDate) }}
                <span v-if="release.updatedAt !== release.createdAt">
                  · Updated {{ formatDate(release.updatedAt) }}
                </span>
              </p>
            </div>
            <div class="flex items-center gap-2">
              <button
                type="button"
                class="p-2 text-gray-400 hover:text-blue-500 transition-colors"
                title="Edit"
                @click="openEditModal(release)"
              >
                <PencilIcon class="w-5 h-5" />
              </button>
              <button
                type="button"
                class="p-2 text-gray-400 hover:text-red-500 transition-colors"
                title="Delete"
                @click="openDeleteModal(release)"
              >
                <TrashIcon class="w-5 h-5" />
              </button>
            </div>
          </div>
        </div>

        <!-- Pagination -->
        <div v-if="pagination.totalPages > 1" class="flex items-center justify-between pt-4">
          <p class="text-sm text-gray-500 dark:text-gray-400">
            Showing {{ pagination.page * pagination.size + 1 }} to {{ Math.min((pagination.page + 1) * pagination.size, pagination.totalElements) }} of {{ pagination.totalElements }} results
          </p>
          <div class="flex items-center gap-2">
            <button
              type="button"
              class="px-3 py-1 text-sm rounded border border-gray-300 dark:border-gray-600 disabled:opacity-50"
              :disabled="pagination.page === 0"
              @click="goToPage(pagination.page - 1)"
            >
              Previous
            </button>
            <button
              type="button"
              class="px-3 py-1 text-sm rounded border border-gray-300 dark:border-gray-600 disabled:opacity-50"
              :disabled="pagination.page >= pagination.totalPages - 1"
              @click="goToPage(pagination.page + 1)"
            >
              Next
            </button>
          </div>
        </div>
      </div>

      <!-- Create/Edit Modal -->
      <Teleport to="body">
        <div
          v-if="showFormModal"
          class="fixed inset-0 z-50 flex items-center justify-center p-4"
        >
          <div class="fixed inset-0 bg-black/50" @click="closeModals"></div>
          <div class="relative bg-white dark:bg-gray-800 rounded-xl shadow-xl max-w-2xl w-full max-h-[90vh] overflow-y-auto p-6">
            <button
              type="button"
              class="absolute top-4 right-4 text-gray-400 hover:text-gray-600 dark:hover:text-gray-300"
              @click="closeModals"
            >
              <XMarkIcon class="w-5 h-5" />
            </button>

            <h2 class="text-xl font-bold text-gray-900 dark:text-white mb-6">
              {{ editingRelease ? 'Edit Release' : 'New Release' }}
            </h2>

            <form @submit.prevent="handleSubmit" class="space-y-4">
              <!-- Version and Type -->
              <div class="grid grid-cols-2 gap-4">
                <div>
                  <label for="version" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                    Version <span class="text-red-500">*</span>
                  </label>
                  <input
                    id="version"
                    v-model="form.version"
                    type="text"
                    placeholder="e.g., 1.0.0 or v1.0.0"
                    class="w-full px-4 py-2 border rounded-lg bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent font-mono"
                    :class="formErrors.version ? 'border-red-500' : 'border-gray-300 dark:border-gray-600'"
                    :disabled="isSubmitting"
                  />
                  <p v-if="formErrors.version" class="mt-1 text-sm text-red-500">{{ formErrors.version }}</p>
                </div>

                <div>
                  <label for="type" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                    Type
                  </label>
                  <select
                    id="type"
                    v-model="form.type"
                    class="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                    :disabled="isSubmitting"
                  >
                    <option v-for="t in releaseTypes" :key="t.value" :value="t.value">
                      {{ t.label }}
                    </option>
                  </select>
                </div>
              </div>

              <!-- Title -->
              <div>
                <label for="title" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                  Title <span class="text-red-500">*</span>
                </label>
                <input
                  id="title"
                  v-model="form.title"
                  type="text"
                  placeholder="Release title"
                  class="w-full px-4 py-2 border rounded-lg bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  :class="formErrors.title ? 'border-red-500' : 'border-gray-300 dark:border-gray-600'"
                  :disabled="isSubmitting"
                />
                <p v-if="formErrors.title" class="mt-1 text-sm text-red-500">{{ formErrors.title }}</p>
              </div>

              <!-- Release Date -->
              <div>
                <label for="releaseDate" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                  Release Date <span class="text-red-500">*</span>
                </label>
                <input
                  id="releaseDate"
                  v-model="form.releaseDate"
                  type="date"
                  class="w-full px-4 py-2 border rounded-lg bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  :class="formErrors.releaseDate ? 'border-red-500' : 'border-gray-300 dark:border-gray-600'"
                  :disabled="isSubmitting"
                />
                <p v-if="formErrors.releaseDate" class="mt-1 text-sm text-red-500">{{ formErrors.releaseDate }}</p>
              </div>

              <!-- Summary -->
              <div>
                <label for="summary" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                  Summary
                </label>
                <input
                  id="summary"
                  v-model="form.summary"
                  type="text"
                  placeholder="Brief summary (optional)"
                  class="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  :disabled="isSubmitting"
                />
              </div>

              <!-- Content -->
              <div>
                <label for="content" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                  Release Notes <span class="text-red-500">*</span>
                </label>
                <textarea
                  id="content"
                  v-model="form.content"
                  rows="10"
                  placeholder="Release notes content (Markdown supported)"
                  class="w-full px-4 py-2 border rounded-lg bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent font-mono text-sm"
                  :class="formErrors.content ? 'border-red-500' : 'border-gray-300 dark:border-gray-600'"
                  :disabled="isSubmitting"
                ></textarea>
                <p v-if="formErrors.content" class="mt-1 text-sm text-red-500">{{ formErrors.content }}</p>
              </div>

              <!-- Actions -->
              <div class="flex justify-end gap-3 pt-4">
                <button
                  type="button"
                  class="btn btn-secondary"
                  :disabled="isSubmitting"
                  @click="closeModals"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  class="btn btn-primary"
                  :disabled="isSubmitting"
                >
                  {{ isSubmitting ? 'Saving...' : (editingRelease ? 'Update' : 'Create') }}
                </button>
              </div>
            </form>
          </div>
        </div>
      </Teleport>

      <!-- Delete Confirmation Modal -->
      <Teleport to="body">
        <div
          v-if="showDeleteModal && releaseToDelete"
          class="fixed inset-0 z-50 flex items-center justify-center p-4"
        >
          <div class="fixed inset-0 bg-black/50" @click="closeModals"></div>
          <div class="relative bg-white dark:bg-gray-800 rounded-xl shadow-xl max-w-md w-full p-6">
            <div class="flex items-center gap-3 mb-4">
              <div class="w-10 h-10 bg-red-100 dark:bg-red-900 rounded-full flex items-center justify-center">
                <ExclamationTriangleIcon class="w-5 h-5 text-red-600 dark:text-red-400" />
              </div>
              <h2 class="text-xl font-bold text-gray-900 dark:text-white">
                Delete Release
              </h2>
            </div>

            <p class="text-gray-600 dark:text-gray-400 mb-6">
              Are you sure you want to delete release "<strong>{{ releaseToDelete.version }}</strong>"?
              This action cannot be undone.
            </p>

            <div class="flex justify-end gap-3">
              <button
                type="button"
                class="btn btn-secondary"
                :disabled="isSubmitting"
                @click="closeModals"
              >
                Cancel
              </button>
              <button
                type="button"
                class="btn bg-red-600 text-white hover:bg-red-700"
                :disabled="isSubmitting"
                @click="handleDelete"
              >
                {{ isSubmitting ? 'Deleting...' : 'Delete' }}
              </button>
            </div>
          </div>
        </div>
      </Teleport>
    </div>
  </AdminLayout>
</template>
