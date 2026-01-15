<script setup lang="ts">
import { ref, onMounted } from 'vue'
import AdminLayout from '@/layouts/AdminLayout.vue'
import Toast from '@/components/Toast.vue'
import { useToast } from '@/composables/useToast'
import {
  getAnnouncements,
  createAnnouncement,
  updateAnnouncement,
  deleteAnnouncement,
  type Announcement,
  type AnnouncementsResponse,
  type CreateAnnouncementRequest,
} from '@/api/announcements'
import {
  PlusIcon,
  PencilIcon,
  TrashIcon,
  XMarkIcon,
  ExclamationTriangleIcon,
  MegaphoneIcon,
} from '@heroicons/vue/24/outline'

const toast = useToast()

// List state
const announcements = ref<Announcement[]>([])
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
const editingAnnouncement = ref<Announcement | null>(null)

// Form state
const form = ref<CreateAnnouncementRequest>({
  title: '',
  content: '',
  summary: '',
  category: 'general',
  pinned: false,
})
const formErrors = ref<Record<string, string>>({})

// Delete state
const announcementToDelete = ref<Announcement | null>(null)

const categories = [
  { value: 'general', label: 'General' },
  { value: 'update', label: 'Update' },
  { value: 'maintenance', label: 'Maintenance' },
  { value: 'security', label: 'Security' },
]

async function loadAnnouncements() {
  isLoading.value = true
  error.value = null

  try {
    const response: AnnouncementsResponse = await getAnnouncements({
      page: pagination.value.page,
      size: pagination.value.size,
    })
    announcements.value = response.content
    pagination.value = {
      page: response.page,
      size: response.size,
      totalElements: response.totalElements,
      totalPages: response.totalPages,
    }
  } catch {
    error.value = 'Failed to load announcements'
  } finally {
    isLoading.value = false
  }
}

function openCreateModal() {
  editingAnnouncement.value = null
  form.value = {
    title: '',
    content: '',
    summary: '',
    category: 'general',
    pinned: false,
  }
  formErrors.value = {}
  showFormModal.value = true
}

function openEditModal(announcement: Announcement) {
  editingAnnouncement.value = announcement
  form.value = {
    title: announcement.title,
    content: announcement.content,
    summary: announcement.summary || '',
    category: announcement.category,
    pinned: announcement.pinned,
  }
  formErrors.value = {}
  showFormModal.value = true
}

function openDeleteModal(announcement: Announcement) {
  announcementToDelete.value = announcement
  showDeleteModal.value = true
}

function closeModals() {
  showFormModal.value = false
  showDeleteModal.value = false
  editingAnnouncement.value = null
  announcementToDelete.value = null
  formErrors.value = {}
}

function validateForm(): boolean {
  formErrors.value = {}

  if (!form.value.title.trim()) {
    formErrors.value.title = 'Title is required'
  }
  if (!form.value.content.trim()) {
    formErrors.value.content = 'Content is required'
  }

  return Object.keys(formErrors.value).length === 0
}

async function handleSubmit() {
  if (!validateForm()) return

  isSubmitting.value = true

  try {
    if (editingAnnouncement.value) {
      await updateAnnouncement(editingAnnouncement.value.id, form.value)
      toast.success('Announcement updated successfully')
    } else {
      await createAnnouncement(form.value)
      toast.success('Announcement created successfully')
    }
    closeModals()
    await loadAnnouncements()
  } catch {
    toast.error(editingAnnouncement.value ? 'Failed to update announcement' : 'Failed to create announcement')
  } finally {
    isSubmitting.value = false
  }
}

async function handleDelete() {
  if (!announcementToDelete.value) return

  isSubmitting.value = true

  try {
    await deleteAnnouncement(announcementToDelete.value.id)
    toast.success('Announcement deleted successfully')
    closeModals()
    await loadAnnouncements()
  } catch {
    toast.error('Failed to delete announcement')
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

function getCategoryBadgeClass(category: string): string {
  const classes: Record<string, string> = {
    general: 'bg-gray-100 text-gray-800 dark:bg-gray-700 dark:text-gray-300',
    update: 'bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-300',
    maintenance: 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-300',
    security: 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-300',
  }
  return classes[category] || classes.general
}

function goToPage(page: number) {
  pagination.value.page = page
  loadAnnouncements()
}

onMounted(loadAnnouncements)
</script>

<template>
  <AdminLayout>
    <div>
      <!-- Toast -->
      <Toast :messages="toast.messages.value" @remove="toast.remove" />

      <!-- Header -->
      <div class="flex items-center justify-between mb-6">
        <h1 class="text-2xl font-bold text-gray-900 dark:text-white">Manage Announcements</h1>
        <button
          type="button"
          class="btn btn-primary inline-flex items-center gap-2"
          @click="openCreateModal"
        >
          <PlusIcon class="w-5 h-5" />
          New Announcement
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
        <MegaphoneIcon class="w-12 h-12 mx-auto text-gray-400 mb-4" />
        <p class="text-gray-600 dark:text-gray-400 mb-4">{{ error }}</p>
        <button type="button" class="btn btn-primary" @click="loadAnnouncements">
          Try Again
        </button>
      </div>

      <!-- Empty state -->
      <div v-else-if="announcements.length === 0" class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-8 text-center">
        <MegaphoneIcon class="w-12 h-12 mx-auto text-gray-400 mb-4" />
        <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">No announcements yet</h2>
        <p class="text-gray-600 dark:text-gray-400 mb-4">
          Create your first announcement to get started.
        </p>
        <button
          type="button"
          class="btn btn-primary inline-flex items-center gap-2"
          @click="openCreateModal"
        >
          <PlusIcon class="w-5 h-5" />
          New Announcement
        </button>
      </div>

      <!-- Announcements list -->
      <div v-else class="space-y-4">
        <div
          v-for="announcement in announcements"
          :key="announcement.id"
          class="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6"
        >
          <div class="flex items-start justify-between gap-4">
            <div class="flex-1 min-w-0">
              <div class="flex items-center gap-3 mb-2">
                <h3 class="text-lg font-medium text-gray-900 dark:text-white truncate">
                  {{ announcement.title }}
                </h3>
                <span
                  v-if="announcement.pinned"
                  class="px-2 py-0.5 text-xs font-medium bg-purple-100 text-purple-800 dark:bg-purple-900 dark:text-purple-300 rounded"
                >
                  Pinned
                </span>
                <span
                  :class="['px-2 py-0.5 text-xs font-medium rounded', getCategoryBadgeClass(announcement.category)]"
                >
                  {{ announcement.category }}
                </span>
              </div>
              <p class="text-sm text-gray-600 dark:text-gray-400 line-clamp-2 mb-2">
                {{ announcement.summary || announcement.content.substring(0, 150) + '...' }}
              </p>
              <p class="text-xs text-gray-500 dark:text-gray-500">
                Created {{ formatDate(announcement.createdAt) }}
                <span v-if="announcement.updatedAt !== announcement.createdAt">
                  · Updated {{ formatDate(announcement.updatedAt) }}
                </span>
              </p>
            </div>
            <div class="flex items-center gap-2">
              <button
                type="button"
                class="p-2 text-gray-400 hover:text-blue-500 transition-colors"
                title="Edit"
                @click="openEditModal(announcement)"
              >
                <PencilIcon class="w-5 h-5" />
              </button>
              <button
                type="button"
                class="p-2 text-gray-400 hover:text-red-500 transition-colors"
                title="Delete"
                @click="openDeleteModal(announcement)"
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
              {{ editingAnnouncement ? 'Edit Announcement' : 'New Announcement' }}
            </h2>

            <form @submit.prevent="handleSubmit" class="space-y-4">
              <!-- Title -->
              <div>
                <label for="title" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                  Title <span class="text-red-500">*</span>
                </label>
                <input
                  id="title"
                  v-model="form.title"
                  type="text"
                  class="w-full px-4 py-2 border rounded-lg bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  :class="formErrors.title ? 'border-red-500' : 'border-gray-300 dark:border-gray-600'"
                  :disabled="isSubmitting"
                />
                <p v-if="formErrors.title" class="mt-1 text-sm text-red-500">{{ formErrors.title }}</p>
              </div>

              <!-- Category -->
              <div>
                <label for="category" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                  Category
                </label>
                <select
                  id="category"
                  v-model="form.category"
                  class="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  :disabled="isSubmitting"
                >
                  <option v-for="cat in categories" :key="cat.value" :value="cat.value">
                    {{ cat.label }}
                  </option>
                </select>
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
                  Content <span class="text-red-500">*</span>
                </label>
                <textarea
                  id="content"
                  v-model="form.content"
                  rows="8"
                  placeholder="Announcement content (Markdown supported)"
                  class="w-full px-4 py-2 border rounded-lg bg-white dark:bg-gray-700 text-gray-900 dark:text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent font-mono text-sm"
                  :class="formErrors.content ? 'border-red-500' : 'border-gray-300 dark:border-gray-600'"
                  :disabled="isSubmitting"
                ></textarea>
                <p v-if="formErrors.content" class="mt-1 text-sm text-red-500">{{ formErrors.content }}</p>
              </div>

              <!-- Pinned -->
              <div class="flex items-center gap-2">
                <input
                  id="pinned"
                  v-model="form.pinned"
                  type="checkbox"
                  class="w-4 h-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
                  :disabled="isSubmitting"
                />
                <label for="pinned" class="text-sm text-gray-700 dark:text-gray-300">
                  Pin this announcement
                </label>
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
                  {{ isSubmitting ? 'Saving...' : (editingAnnouncement ? 'Update' : 'Create') }}
                </button>
              </div>
            </form>
          </div>
        </div>
      </Teleport>

      <!-- Delete Confirmation Modal -->
      <Teleport to="body">
        <div
          v-if="showDeleteModal && announcementToDelete"
          class="fixed inset-0 z-50 flex items-center justify-center p-4"
        >
          <div class="fixed inset-0 bg-black/50" @click="closeModals"></div>
          <div class="relative bg-white dark:bg-gray-800 rounded-xl shadow-xl max-w-md w-full p-6">
            <div class="flex items-center gap-3 mb-4">
              <div class="w-10 h-10 bg-red-100 dark:bg-red-900 rounded-full flex items-center justify-center">
                <ExclamationTriangleIcon class="w-5 h-5 text-red-600 dark:text-red-400" />
              </div>
              <h2 class="text-xl font-bold text-gray-900 dark:text-white">
                Delete Announcement
              </h2>
            </div>

            <p class="text-gray-600 dark:text-gray-400 mb-6">
              Are you sure you want to delete "<strong>{{ announcementToDelete.title }}</strong>"?
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
