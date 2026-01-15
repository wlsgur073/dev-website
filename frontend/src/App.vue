<script setup lang="ts">
import { RouterView } from 'vue-router'
import Toast from '@/components/Toast.vue'
import { useToast } from '@/composables/useToast'

const toast = useToast()
</script>

<template>
  <RouterView />
  <Toast :messages="toast.messages.value" @remove="toast.remove" />
</template>
