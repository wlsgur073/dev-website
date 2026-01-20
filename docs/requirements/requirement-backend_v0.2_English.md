# Developer Community Portal — Backend Requirements

**Stack**: Spring Boot 3, Java 21, PostgreSQL, Gradle, Flyway, Spring Security, springdoc-openapi

## 1. Goals
- Provide public APIs for: announcements, release notes, plans, and (optionally) docs metadata.
- Provide authentication APIs: registration, login, token refresh, logout.
- Provide console APIs: API key issuance/list/revocation.
- Provide billing foundation (no payment integration yet): plan, subscription, entitlement model.
- Provide admin APIs: CRUD for announcements and release notes.

## 2. Non-Functional Requirements
- API versioning: all endpoints under `/api/v1`.
- Error format: RFC 7807 Problem Details (JSON) with `traceId`.
- Pagination: `page`, `size`, `sort` (e.g., `createdAt,desc`) for list endpoints.
- Validation: Jakarta Bean Validation for request DTOs; return 400 on validation failures.
- Security:
  - Password hashing: bcrypt.
  - JWT Access Token (short TTL, e.g., 15 minutes).
  - Refresh Token in HttpOnly cookie with rotation (revokes previous token on refresh).
  - Roles: `ROLE_USER`, `ROLE_ADMIN`.
- Observability:
  - Actuator enabled: at least `health`, `info`.
  - Log correlation: requestId/traceId included in logs.
- Database migration:
  - Flyway is mandatory.
  - Seed data must be enabled in `dev` profile only.

## 3. Architecture Decisions
- Prefer a modular monolith (package-level modules):
  - `auth`, `users`, `content` (announcements/releases), `apikey`, `billing` (plans/subscriptions/entitlements), `common`.
- Deployment simplification options:
  - Option A: Spring Boot also serves frontend static assets (same-origin).
  - Option B: Reverse proxy (e.g., Nginx) routes `/` to web and `/api` to API.
- Dev setup:
  - PostgreSQL via Docker Compose.
  - Avoid CORS complexity by recommending same-origin in prod and a dev proxy in frontend.

## 4. Data Model (PostgreSQL)
### 4.1 Minimum Tables
- `users`
  - `id` (uuid), `email` (unique), `password_hash`, `display_name`, `role`,
    `created_at`, `updated_at`, `last_login_at`
- `refresh_tokens`
  - `id` (uuid), `user_id` (fk), `token_hash`, `expires_at`,
    `created_at`, `revoked_at`, `replaced_by_id` (nullable)
- `api_keys`
  - `id` (uuid), `user_id` (fk), `name`, `prefix` (public identifier),
    `secret_hash`, `created_at`, `revoked_at`
- `announcements`
  - `id` (uuid), `title`, `body_markdown`, `tags` (text[] or join table),
    `is_published`, `published_at`, `created_at`, `updated_at`
- `releases`
  - `id` (uuid), `version`, `title`, `body_markdown`, `tags`,
    `released_at`, `created_at`, `updated_at`
- `plans`
  - `id` (uuid), `code` (unique), `name`, `price_monthly` (int),
    `features` (jsonb), `is_active`, `created_at`, `updated_at`
- `subscriptions`
  - `id` (uuid), `user_id` (fk), `plan_code`, `status`,
    `current_period_start`, `current_period_end`, `created_at`, `updated_at`

### 4.2 Indexing
- Unique index: `users.email`, `plans.code`
- Indexes: `announcements.published_at`, `releases.released_at`,
  `api_keys.user_id`, `subscriptions.user_id`

## 5. API Endpoints (Minimum)
Base: `/api/v1`

### 5.1 Auth
- `POST /auth/register`
  - req: `{ email, password, displayName }`
  - res: `201 { userId, email, displayName }`
- `POST /auth/login`
  - req: `{ email, password }`
  - res: `200 { accessToken, user: {...} }`
  - sets cookie: `refresh_token=...; HttpOnly; SameSite=Lax; Secure (prod)`
- `POST /auth/refresh`
  - req: empty
  - res: `200 { accessToken }`
  - refresh token rotation; on failure return `401`
- `POST /auth/logout`
  - req: empty
  - res: `204`
  - expires refresh cookie and revokes token(s)

### 5.2 Users
- `GET /me` (auth)
  - res: `200 { id, email, displayName, role }`
- `PATCH /me` (auth)
  - req: `{ displayName }`
  - res: `200 ...`

### 5.3 Announcements (Public + Admin)
- `GET /announcements?published=true&page&size&sort&tag`
- `GET /announcements/{id}`
- `POST /admin/announcements` (admin)
- `PATCH /admin/announcements/{id}` (admin)
- `DELETE /admin/announcements/{id}` (admin)

### 5.4 Releases (Public + Admin)
- `GET /releases?page&size&sort&tag&versionPrefix`
- `GET /releases/{id}`
- `POST /admin/releases` (admin)
- `PATCH /admin/releases/{id}` (admin)
- `DELETE /admin/releases/{id}` (admin)

### 5.5 API Keys (Console)
- `GET /api-keys` (auth)
  - returns list without raw secret
- `POST /api-keys` (auth)
  - req: `{ name }`
  - res: `201 { id, name, createdAt, secretOnce }` (secret returned once only)
- `DELETE /api-keys/{id}` (auth)

### 5.6 Billing
- `GET /plans` (public)
- `GET /subscription` (auth)
- `POST /subscription` (auth)
  - stub for plan changes (no payment integration yet)

### 5.7 Health
- `GET /actuator/health`

## 6. Authorization Rules
- Public: `plans`, `announcements (published)`, `releases`. Swagger/OpenAPI exposure configurable by environment.
- Auth required: `/me`, `/api-keys`, `/subscription`
- Admin required: `/admin/*`

## 7. Security Details
- Access token: `Authorization: Bearer <token>`
- Refresh token: HttpOnly cookie
- CSRF:
  - For same-origin setups, rely on `SameSite=Lax` plus basic Origin/Referer validation for refresh/logout.
  - Alternatively, implement double-submit token for refresh/logout.
- Password policy:
  - Minimum length 10, recommended mixed character sets.

## 8. OpenAPI
- Use `springdoc-openapi-starter-webmvc-ui`.
- Paths:
  - `/swagger-ui/index.html`
  - `/v3/api-docs`

## 9. Testing
- Unit tests: at least 5 for service logic.
- Integration tests: at least 2 using Testcontainers + Postgres (preferred).
  - Auth flow: register -> login -> refresh
  - Public content: announcements list

## 10. Definition of Done
- `docker compose up` provisions PostgreSQL.
- `./gradlew bootRun` starts backend.
- All endpoints visible in OpenAPI.
- Flyway creates schema on a fresh DB.
- Happy path works: register -> login -> list announcements -> create API key.
