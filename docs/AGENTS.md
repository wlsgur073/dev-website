# Repository Guidelines

## Project Structure & Module Organization
- `backend/` houses the Spring Boot API. Core code lives in `backend/src/main/java/com/devwebsite/backend` with modules like `auth`, `announcement`, `release`, `apikey`, `billing`, and `common`. Resources are in `backend/src/main/resources` (Flyway migrations in `db/migration`), and tests are in `backend/src/test/java`.
- `frontend/` contains the Vue 3 app. UI code is in `frontend/src` with `pages/`, `components/`, `layouts/`, `router/`, `stores/`, and `composables/`. API clients live in `src/api`, generated types in `src/api/generated.ts`, and docs markdown in `src/content/docs`.
- `docs/` holds requirements and planning notes for the project.

## Build, Test, and Development Commands
Backend (run from `backend/`):
- `cp .env.example .env` to create local config.
- `docker compose up -d` starts PostgreSQL.
- `./gradlew bootRun` runs the API on `http://localhost:8080`.
- `./gradlew test` runs JUnit/Testcontainers tests.

Frontend (run from `frontend/`):
- `pnpm install` (or `npm install`) to install dependencies.
- `pnpm dev` starts the Vite dev server on `http://localhost:5173`.
- `pnpm build` builds production assets; `pnpm preview` serves the build.
- `pnpm gen:api` regenerates `src/api/generated.ts` from `openapi/openapi.json`.

## Coding Style & Naming Conventions
- Java: 4-space indentation, standard Spring conventions, package names in lowercase, classes in `PascalCase`.
- Vue/TypeScript: 2-space indentation, no semicolons; components and layouts in `PascalCase.vue`, composables in `useX.ts`, stores under `stores/`.
- Keep API routes under `/api/v1` and share API types through `frontend/src/api`.

## Testing Guidelines
- Backend uses JUnit 5 with Testcontainers. Integration tests live in `backend/src/test/java` and follow `*IntegrationTest` naming.
- Frontend has no automated test suite yet; add tests when introducing complex UI logic.

## Commit & Pull Request Guidelines
- Commit messages follow a `type: summary` style; observed types include `feat`, `refactor`, `add`, and `update`. Example: `feat: add API key management`.
- PRs should include a clear description, verification steps (commands or endpoints), and screenshots for UI changes.
- Link related issues or requirements when applicable.

## Configuration & Secrets
- Never commit real secrets. Use `backend/.env.example` and `frontend/.env.example` as templates.
- For local API calls from the frontend, rely on the Vite proxy or set `VITE_API_BASE_URL`.
