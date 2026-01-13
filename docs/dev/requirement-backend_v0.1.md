# Developer Community Portal Backend Requirements
Stack: Spring Boot 3, Java 21, PostgreSQL, Gradle, Flyway, Spring Security, springdoc-openapi

## 1. Goals
- Public 영역: 공지사항, 릴리즈노트, 플랜, 문서 메타(필요 시), 검색용 데이터 제공
- Auth: 회원가입, 로그인, 토큰 갱신, 로그아웃
- Console: API Key 발급, 폐기, 목록
- Billing(결제 연동 전): Plan, Subscription, Entitlement(권한) 모델 제공
- Admin: 공지사항, 릴리즈노트 CRUD

## 2. Non-functional requirements
- API versioning: /api/v1 고정
- Error format: RFC 7807 Problem Details(JSON), traceId 포함
- Pagination: page, size, sort(예: createdAt,desc)
- Validation: Jakarta Validation(Bean Validation) 적용, 입력 검증 실패 시 400
- Security:
  - Password hashing: bcrypt
  - JWT access token (짧은 TTL, 예: 15분)
  - Refresh token: HttpOnly cookie, rotation(재발급 시 이전 토큰 폐기)
  - Roles: ROLE_USER, ROLE_ADMIN
- Observability:
  - Actuator enabled(health, info)
  - requestId 또는 traceId 로그에 포함
- DB migration: Flyway 필수, seed 데이터는 dev 프로파일에서만

## 3. Architecture decisions
- Modular monolith 권장(패키지 단위 모듈)
  - auth, users, content(announcements, releases), apikey, billing(plans, subscriptions, entitlements), common
- 배포 단순화를 위해 권장 옵션:
  - Option A: Spring Boot가 프론트 정적 파일(dist)을 함께 서빙, 동일 오리진으로 운영
  - Option B: Nginx 리버스 프록시(/ -> web, /api -> api)
- 개발 환경:
  - docker compose로 PostgreSQL
  - CORS는 dev에서만 최소 허용(가능하면 Vite proxy 사용)

## 4. Data model (PostgreSQL)
### 4.1 Tables (minimum)
- users
  - id (uuid), email(unique), password_hash, display_name, role, created_at, updated_at, last_login_at
- refresh_tokens
  - id (uuid), user_id(fk), token_hash, expires_at, created_at, revoked_at, replaced_by_id(nullable)
- api_keys
  - id (uuid), user_id(fk), name, prefix(노출용), secret_hash, created_at, revoked_at
- announcements
  - id (uuid), title, body_markdown, tags(text[] or join), is_published, published_at, created_at, updated_at
- releases
  - id (uuid), version, title, body_markdown, tags, released_at, created_at, updated_at
- plans
  - id (uuid), code(unique), name, price_monthly(int), features(jsonb), is_active, created_at, updated_at
- subscriptions
  - id (uuid), user_id(fk), plan_code, status, current_period_start, current_period_end, created_at, updated_at

### 4.2 Indexing
- users.email unique index
- announcements.published_at, releases.released_at index
- api_keys.user_id index
- subscriptions.user_id index

## 5. API endpoints (minimum)
Base: /api/v1

### 5.1 Auth
- POST /auth/register
  - req: { email, password, displayName }
  - res: 201 { userId, email, displayName }
- POST /auth/login
  - req: { email, password }
  - res: 200 { accessToken, user: { ... } }
  - set-cookie: refresh_token=...; HttpOnly; SameSite=Lax; Secure(프로덕션)
- POST /auth/refresh
  - req: empty
  - res: 200 { accessToken }
  - refresh token rotation, 실패 시 401
- POST /auth/logout
  - req: empty
  - res: 204, refresh cookie 만료, DB 토큰 revoke

### 5.2 Users
- GET /me (auth)
  - res: 200 { id, email, displayName, role }
- PATCH /me (auth)
  - req: { displayName }
  - res: 200

### 5.3 Announcements (public + admin)
- GET /announcements?published=true&page&size&sort&tag
- GET /announcements/{id}
- POST /admin/announcements (admin)
- PATCH /admin/announcements/{id} (admin)
- DELETE /admin/announcements/{id} (admin)

### 5.4 Releases (public + admin)
- GET /releases?page&size&sort&tag&versionPrefix
- GET /releases/{id}
- POST /admin/releases (admin)
- PATCH /admin/releases/{id} (admin)
- DELETE /admin/releases/{id} (admin)

### 5.5 API Keys (console)
- GET /api-keys (auth)
  - res: 목록(비밀키 원문 미포함)
- POST /api-keys (auth)
  - req: { name }
  - res: 201 { id, name, createdAt, secretOnce }  // secretOnce는 1회만 제공
- DELETE /api-keys/{id} (auth)

### 5.6 Billing
- GET /plans (public)
- GET /subscription (auth)  // 현재 사용자 구독 상태
- POST /subscription (auth) // 결제 없음, 플랜 변경 스텁. 정책은 서버에서 제한 가능

### 5.7 Health
- GET /actuator/health

## 6. Authorization rules
- Public: plans, announcements(published), releases, swagger/openapi 공개 여부는 환경별 설정
- Auth required: /me, /api-keys, /subscription
- Admin required: /admin/*

## 7. Security details
- Access token: Authorization: Bearer <token>
- Refresh token: HttpOnly cookie
- CSRF:
  - 동일 오리진 운영(Option A 또는 Nginx) 전제 시 SameSite=Lax로 기본 방어
  - /auth/refresh, /auth/logout는 Origin 체크 또는 double submit token 중 1개 적용
- Password policy:
  - 최소 10자, 숫자/문자 조합 권장(정책은 요구에 맞게 조정 가능)

## 8. OpenAPI
- springdoc-openapi-starter-webmvc-ui 사용
- 문서 주소:
  - /swagger-ui.html 또는 /swagger-ui/index.html
  - /v3/api-docs

## 9. Testing
- Unit: 서비스 로직 최소 5개
- Integration: Testcontainers로 PostgreSQL 붙인 통합 테스트 최소 2개(auth, announcements)

## 10. Definition of Done
- docker compose up 후 DB 준비
- 백엔드 ./gradlew bootRun 동작
- 모든 엔드포인트가 OpenAPI에 노출
- Flyway로 신규 환경에서 자동 스키마 구성
- 기본 기능 흐름(회원가입 -> 로그인 -> 공지 조회 -> API key 발급)이 정상 동작
