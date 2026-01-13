import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'

// Lazy load pages for code splitting
const HomePage = () => import('@/pages/HomePage.vue')
const DocsIndexPage = () => import('@/pages/docs/DocsIndexPage.vue')
const DocsSlugPage = () => import('@/pages/docs/DocsSlugPage.vue')
const ApiDocsPage = () => import('@/pages/ApiDocsPage.vue')
const ReleasesPage = () => import('@/pages/releases/ReleasesPage.vue')
const ReleaseDetailPage = () => import('@/pages/releases/ReleaseDetailPage.vue')
const AnnouncementsPage = () => import('@/pages/announcements/AnnouncementsPage.vue')
const AnnouncementDetailPage = () => import('@/pages/announcements/AnnouncementDetailPage.vue')
const PricingPage = () => import('@/pages/PricingPage.vue')
const CommunityPage = () => import('@/pages/CommunityPage.vue')
const SearchPage = () => import('@/pages/SearchPage.vue')

const LoginPage = () => import('@/pages/auth/LoginPage.vue')
const RegisterPage = () => import('@/pages/auth/RegisterPage.vue')

const ConsolePage = () => import('@/pages/console/ConsolePage.vue')
const ConsoleApiKeysPage = () => import('@/pages/console/ConsoleApiKeysPage.vue')
const ConsoleBillingPage = () => import('@/pages/console/ConsoleBillingPage.vue')

const AdminAnnouncementsPage = () => import('@/pages/admin/AdminAnnouncementsPage.vue')
const AdminReleasesPage = () => import('@/pages/admin/AdminReleasesPage.vue')

const NotFoundPage = () => import('@/pages/NotFoundPage.vue')

const routes: RouteRecordRaw[] = [
  // Public routes
  {
    path: '/',
    name: 'home',
    component: HomePage,
    meta: { title: 'Home' },
  },
  {
    path: '/docs',
    name: 'docs',
    component: DocsIndexPage,
    meta: { title: 'Documentation' },
  },
  {
    path: '/docs/:slug',
    name: 'docs-slug',
    component: DocsSlugPage,
    meta: { title: 'Documentation' },
  },
  {
    path: '/api',
    name: 'api-docs',
    component: ApiDocsPage,
    meta: { title: 'API Documentation' },
  },
  {
    path: '/releases',
    name: 'releases',
    component: ReleasesPage,
    meta: { title: 'Releases' },
  },
  {
    path: '/releases/:id',
    name: 'release-detail',
    component: ReleaseDetailPage,
    meta: { title: 'Release Details' },
  },
  {
    path: '/announcements',
    name: 'announcements',
    component: AnnouncementsPage,
    meta: { title: 'Announcements' },
  },
  {
    path: '/announcements/:id',
    name: 'announcement-detail',
    component: AnnouncementDetailPage,
    meta: { title: 'Announcement' },
  },
  {
    path: '/pricing',
    name: 'pricing',
    component: PricingPage,
    meta: { title: 'Pricing' },
  },
  {
    path: '/community',
    name: 'community',
    component: CommunityPage,
    meta: { title: 'Community' },
  },
  {
    path: '/search',
    name: 'search',
    component: SearchPage,
    meta: { title: 'Search' },
  },

  // Auth routes
  {
    path: '/login',
    name: 'login',
    component: LoginPage,
    meta: { title: 'Login', guest: true },
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterPage,
    meta: { title: 'Register', guest: true },
  },

  // Console routes (protected)
  {
    path: '/console',
    name: 'console',
    component: ConsolePage,
    meta: { title: 'Console', requiresAuth: true },
  },
  {
    path: '/console/api-keys',
    name: 'console-api-keys',
    component: ConsoleApiKeysPage,
    meta: { title: 'API Keys', requiresAuth: true },
  },
  {
    path: '/console/billing',
    name: 'console-billing',
    component: ConsoleBillingPage,
    meta: { title: 'Billing', requiresAuth: true },
  },

  // Admin routes (protected, ROLE_ADMIN)
  {
    path: '/admin/announcements',
    name: 'admin-announcements',
    component: AdminAnnouncementsPage,
    meta: { title: 'Manage Announcements', requiresAuth: true, requiresAdmin: true },
  },
  {
    path: '/admin/releases',
    name: 'admin-releases',
    component: AdminReleasesPage,
    meta: { title: 'Manage Releases', requiresAuth: true, requiresAdmin: true },
  },

  // 404 fallback
  {
    path: '/:pathMatch(.*)*',
    name: 'not-found',
    component: NotFoundPage,
    meta: { title: '404 Not Found' },
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(_to, _from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    }
    return { top: 0 }
  },
})

// Navigation guard placeholder - will be implemented in Phase 1
router.beforeEach((to, _from, next) => {
  // Update document title
  const title = to.meta.title as string | undefined
  document.title = title ? `${title} | Dev Website` : 'Dev Website'

  // Auth guards will be implemented in Phase 1
  next()
})

export default router

// Type augmentation for route meta
declare module 'vue-router' {
  interface RouteMeta {
    title?: string
    requiresAuth?: boolean
    requiresAdmin?: boolean
    guest?: boolean
  }
}
