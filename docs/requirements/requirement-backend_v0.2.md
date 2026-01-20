너는 Claude Code로 동작하는 시니어 백엔드 엔지니어다.
이 폴더(dev-portal-api)는 백엔드 전용 레포다. 프론트엔드 파일(Vue/Vite/HTML 템플릿)은 절대 만들지 마라.

목표:
Spring Boot 3 + Java 21 + PostgreSQL로 “Developer Community Portal API”를 구축한다.
장기적으로 커뮤니티/포럼(연동), API docs, 릴리즈노트, 공지, 과금(플랜/구독)까지 확장 가능해야 한다.
이번 결과물은 로컬에서 즉시 실행 가능해야 하고, 인증 플로우와 콘솔 기능(API Key/구독 스텁)이 실제로 동작해야 한다.

필수 스택/제약(고정):
- Java 21, Spring Boot 3, Gradle
- PostgreSQL, Flyway
- Spring Security + JWT Access Token + Refresh Token(HttpOnly cookie, rotation)
- RBAC: ROLE_USER, ROLE_ADMIN
- OpenAPI: springdoc-openapi-starter-webmvc-ui
- 에러 응답: RFC 7807 Problem Details JSON + traceId 포함
- 환경 분리: dev / prod 프로파일 (seed는 dev에서만)
- docker compose로 PostgreSQL 제공

레포 구조(고정):
/ (root)
  docker-compose.yml
  .env.example
  README.md
  requirement-backend.md
  /scripts
    export-openapi.sh
  /src/main/java/...
  /src/main/resources
    application.yml
    application-dev.yml
    application-prod.yml
    db/migration/V1__init.sql
    db/migration/V2__seed_dev.sql (dev에서만 실행되도록 설계)
  /src/test/java/...

포트/경로 규칙(고정):
- 서버 포트: 8080
- API base path: /api/v1
- Swagger/OpenAPI:
  - /v3/api-docs
  - /swagger-ui/index.html (가능하면 이 경로로 고정)
- 액추에이터(최소): /actuator/health

보안 규칙(고정):
- Access Token: Authorization: Bearer <token>, TTL 15분
- Refresh Token: HttpOnly cookie, TTL 14일, rotation(재발급 시 이전 토큰 revoke)
- Refresh token은 DB에 해시로 저장, 원문 저장 금지
- /auth/refresh, /auth/logout는 CSRF 위험이 있으니 최소 방어를 넣어라:
  - 같은 오리진을 전제하되, Origin/Referer 검사 또는 double-submit 중 1개 구현
- 비밀번호는 bcrypt로 해시

DB 스키마(최소, Flyway):
- users(id uuid, email unique, password_hash, display_name, role, created_at, updated_at, last_login_at)
- refresh_tokens(id uuid, user_id, token_hash, expires_at, created_at, revoked_at, replaced_by_id)
- api_keys(id uuid, user_id, name, prefix, secret_hash, created_at, revoked_at)
- announcements(id uuid, title, body_markdown, tags, is_published, published_at, created_at, updated_at)
- releases(id uuid, version, title, body_markdown, tags, released_at, created_at, updated_at)
- plans(id uuid, code unique, name, price_monthly int, features jsonb, is_active, created_at, updated_at)
- subscriptions(id uuid, user_id, plan_code, status enum(TRIALING, ACTIVE, PAST_DUE, CANCELED), current_period_start, current_period_end, created_at, updated_at)

API 엔드포인트(필수):
- Auth
  - POST /api/v1/auth/register
  - POST /api/v1/auth/login (refresh cookie set)
  - POST /api/v1/auth/refresh (refresh rotation)
  - POST /api/v1/auth/logout (refresh revoke + cookie expire)
- User
  - GET /api/v1/me
  - PATCH /api/v1/me
- Announcements
  - GET /api/v1/announcements (public, published only)
  - GET /api/v1/announcements/{id}
  - POST /api/v1/admin/announcements (admin)
  - PATCH /api/v1/admin/announcements/{id} (admin)
  - DELETE /api/v1/admin/announcements/{id} (admin)
- Releases
  - GET /api/v1/releases (public)
  - GET /api/v1/releases/{id}
  - POST /api/v1/admin/releases (admin)
  - PATCH /api/v1/admin/releases/{id} (admin)
  - DELETE /api/v1/admin/releases/{id} (admin)
- API Keys
  - GET /api/v1/api-keys (auth)
  - POST /api/v1/api-keys (auth) -> secretOnce 1회만 반환, 이후 재조회 불가
  - DELETE /api/v1/api-keys/{id} (auth)
- Billing(결제 없음, 스텁)
  - GET /api/v1/plans (public)
  - GET /api/v1/subscription (auth)
  - POST /api/v1/subscription (auth, 플랜 변경 스텁)

개발용 seed(dev 프로파일에서만):
- admin 계정 1개, user 계정 1개 (email/password 명시)
- plans 3개: Free/Pro/Team
- announcements 3개, releases 3개

스크립트(필수):
- scripts/export-openapi.sh
  - 서버 실행 중일 때 /v3/api-docs를 받아 openapi.json으로 저장
  - 저장 경로는 레포 루트의 ./openapi.json 고정

테스트(필수 최소):
- 통합 테스트 2개 이상:
  1) auth: register -> login -> refresh 흐름
  2) content: announcements public list 조회
- 가능하면 Testcontainers(Postgres). 어렵다면 H2로 대체하되, 스키마/쿼리 호환성 이슈를 README에 명확히 기록

문서(필수):
- requirement-backend.md 생성(요구사항/엔드포인트/보안/운영 규칙)
- README.md에 실행 방법을 “명령어 그대로” 적어라:
  - docker compose up -d
  - ./gradlew bootRun
  - 확인 URL 목록(swagger, health 등)

작업 방식(반드시 준수):
- Phase 0: 문서/도커/프로젝트 스캐폴딩
- Phase 1: Flyway + 기본 API + swagger
- Phase 2: Auth(JWT + refresh rotation)
- Phase 3: announcements/releases + admin RBAC
- Phase 4: api-keys(secretOnce)
- Phase 5: plans/subscription(스텁)
- Phase 6: 테스트/정리

각 Phase마다:
- 변경 파일 목록
- 핵심 파일 전체 코드
- 실행 명령어
- 검증 방법(URL/테스트 명령)
- 실패 가능 포인트와 해결책

지금 Phase 0부터 시작해라. 먼저 폴더 트리와 파일 생성 계획을 보여주고, 바로 파일을 생성해라.
