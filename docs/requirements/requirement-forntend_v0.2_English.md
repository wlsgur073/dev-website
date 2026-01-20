# Developer Community Portal — Frontend Requirements

**Stack**: Vue 3, TypeScript, Vite, Vue Router, Pinia, Tailwind CSS, pnpm

## 1. Goals
- Implement a developer-portal style UI:
  - Home, Docs (Markdown), API Docs link, Release Notes, Announcements, Pricing, Community
- Auth UI:
  - Register, Login, Logout, Profile
- Console:
  - API Key management, Subscription status, Plan change (stub)
- Admin:
  - Minimal CRUD UI for announcements and release notes
- Visual direction:
  - Developer portal look & feel (global nav, search, card layouts, docs sidebar)

## 2. Design Requirements
- Global layout
  - Header: logo + navigation (Docs, API, Releases, Announcements, Pricing, Community, Console)
  - Right side: search + login button or profile dropdown
  - Footer: links, terms, GitHub, contact
- Home
  - Hero + CTAs (Quickstart, Docs, Console)
  - Quickstart: 3 cards
  - Popular docs section
  - Latest releases and latest announcements sections
  - Pricing CTA
  - Community CTA
- Docs
  - Left sidebar navigation
  - Markdown content area
  - Right-side TOC
  - Code highlight + copy button
- Releases & Announcements
  - List + detail pages
  - Filters: tags, version/date
- Pricing
  - Plan cards with features; show current subscription status
- Community
  - Link-based (no in-house forum implementation); show TODO for Discourse/SSO later

## 3. Non-Functional Requirements
- Accessibility:
  - Keyboard navigation, sensible aria attributes
- Performance:
  - Route-level code splitting
  - Basic asset optimization
- Error handling:
  - Normalize API errors (Problem Details from backend)
- Auth token handling:
  - Access token stored in memory/Pinia store
  - Refresh token is HttpOnly cookie; not accessible via JS
  - On 401: attempt refresh once, then retry original request once; avoid infinite loops
- Environment variables:
  - Use `VITE_API_BASE_URL` or Vite proxy (preferred)

## 4. Tech Decisions (Recommended)
- Styling: Tailwind CSS (avoid heavy component frameworks)
- Markdown:
  - `markdown-it` for rendering
  - Syntax highlight: `shiki` or `highlight.js`
  - Add a code copy button
- Data fetching:
  - Axios-based API client
  - (Optional) @tanstack/vue-query for caching/loading consistency
- Validation:
  - (Optional) Zod for client-side form validation (server validation is final)

## 5. Routes
- `/` (Home)
- `/docs` (Docs index)
- `/docs/:slug` (Doc page)
- `/api` (API docs entry; link to swagger)
- `/releases`, `/releases/:id`
- `/announcements`, `/announcements/:id`
- `/pricing`
- `/community`
- `/search`
- `/login`, `/register`
- `/console` (protected)
  - `/console/api-keys`
  - `/console/billing`
- `/admin` (protected, ROLE_ADMIN)
  - `/admin/announcements`
  - `/admin/releases`
- `/*` -> 404 page

## 6. Component Checklist
- `AppHeader`, `AppFooter`
- `SearchBox` (MVP: client filtering)
- Basic UI primitives: `Card`, `Button`, `Badge`, `Tabs`, `Modal` (minimal, custom)
- `MarkdownRenderer` + `Toc` + `CodeBlockCopy`
- `DataTable` (for admin pages; minimal features)

## 7. Search MVP
- Phase 1:
  - Client-side search across docs (static markdown), announcements, releases
  - Use a lightweight approach (simple filtering or Fuse.js)
- Phase 2:
  - Replace with server-side search (OpenSearch/Elasticsearch) behind an interface

## 8. Definition of Done
- `pnpm dev` runs the frontend locally.
- Auth flow works: login -> access console -> create API key.
- Docs: markdown rendering with TOC + code copy works.
- Releases & announcements list/detail pages work.
- Proper handling for 401/403/404/500 states.
