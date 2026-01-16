# Backend Implementation Plan

## 개요
개발자 포털 백엔드 API 서버 구현 계획서

## 기술 스택
- Spring Boot 4.0.1 + Java 25 + Gradle 9.2.1
- PostgreSQL 17.7 + Flyway
- Spring Security + JWT (Access 15분 + Refresh HttpOnly cookie 14일)
- springdoc-openapi + RFC7807 Problem Details

---

## Phase 0: 프로젝트 스캐폴딩
**상태: [x] 완료**

### Goals
- Spring Boot 프로젝트 초기 설정
- Docker Compose로 PostgreSQL 환경 구성
- 기본 문서화 (README, .env.example)

### Checklist
- [x] build.gradle 작성 (Spring Boot 4.0.1, Java 25)
- [x] settings.gradle 작성
- [x] application.yml, application-dev.yml, application-prod.yml 작성
- [x] docker-compose.yml 작성 (PostgreSQL 17.7)
- [x] .env.example 작성
- [x] README.md 작성
- [x] 기본 Application 클래스 생성
- [x] 프로젝트 빌드 검증

### DoD (Definition of Done)
- [x] `./gradlew build` 성공
- [ ] `docker compose up -d` 후 PostgreSQL 접속 가능 (Phase 1에서 검증 예정)
- [x] README.md에 실행 방법 문서화 완료

### Verification
```bash
./gradlew build  # SUCCESS
```

### Risks
- None

### Issues & Solutions
- 요구사항에 명시된 Spring Boot 4.0.1, Java 25, Gradle 9.2.1로 설정 완료
- 빌드 검증 성공

---

## Phase 1: Flyway + OpenAPI + Problem Details + Health
**상태: [x] 완료**

### Goals
- Flyway 마이그레이션 설정 및 초기 스키마 생성
- OpenAPI/Swagger 설정
- RFC7807 Problem Details 에러 응답 구현
- Actuator Health 엔드포인트 활성화

### Checklist
- [x] V1__init.sql 작성 (전체 스키마)
- [x] springdoc-openapi 설정 (OpenApiConfig.java)
- [x] GlobalExceptionHandler 구현 (RFC7807)
- [x] TraceIdFilter 구현
- [x] Actuator health 활성화
- [x] /v3/api-docs, /swagger-ui 접근 확인

### DoD
- [x] Flyway 마이그레이션 성공
- [x] /actuator/health 200 OK
- [x] /swagger-ui/index.html 접근 가능
- [x] 에러 응답에 traceId 포함

### Verification
```bash
docker compose up -d
./gradlew bootRun
curl http://localhost:8080/actuator/health  # {"status":"UP"}
curl http://localhost:8080/v3/api-docs  # OpenAPI JSON 반환
```

### Risks
- Flyway 버전 호환성 -> 11.2.0 사용하여 해결
- PostgreSQL 17.7 특정 문법 주의

### Issues & Solutions
- Flyway 자동 설정 미작동 -> FlywayConfig.java로 수동 설정
- Docker PostgreSQL 포트 충돌 -> 5433 포트 사용으로 변경

---

## Phase 2: Auth (JWT + Refresh Rotation + CSRF)
**상태: [x] 완료**

### Goals
- Spring Security + JWT 설정
- Refresh Token rotation 구현
- CSRF 방어 (Origin/Referer 검사)
- 개발용 seed 계정 (V2__seed_dev.sql)

### Checklist
- [x] SecurityConfig 구현
- [x] JwtTokenProvider 구현
- [x] POST /api/v1/auth/register
- [x] POST /api/v1/auth/login (refresh cookie set)
- [x] POST /api/v1/auth/refresh (rotation)
- [x] POST /api/v1/auth/logout
- [x] GET /api/v1/me
- [x] PATCH /api/v1/me
- [x] CSRF 방어 구현 (CsrfProtectionFilter)
- [x] V2__seed_dev.sql 작성 (admin, user 계정)

### DoD
- [x] 회원가입 -> 로그인 -> refresh -> logout 플로우 동작
- [x] Refresh token rotation 시 이전 토큰 revoke 확인
- [x] CSRF 방어 동작 확인

### Verification
```bash
# Register
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"password123","nickname":"tester"}'

# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"password123"}'
```

### Risks
- Refresh token 해시 저장 구현 복잡도
- Cookie 설정 (HttpOnly, Secure, SameSite)

### Issues & Solutions
- V2__seed_dev.sql의 bcrypt 해시가 예시 해시 -> DataInitializer로 dev 환경에서 올바른 비밀번호로 업데이트

---

## Phase 3: Announcements / Releases CRUD
**상태: [x] 완료**

### Goals
- 공지사항 (Announcements) CRUD
- 릴리스 노트 (Releases) CRUD
- Pagination 및 필터링

### Checklist
- [x] Announcement 엔티티/리포지토리
- [x] GET /api/v1/announcements (public, published only)
- [x] GET /api/v1/announcements/{id}
- [x] POST /api/v1/admin/announcements (admin)
- [x] PATCH /api/v1/admin/announcements/{id} (admin)
- [x] DELETE /api/v1/admin/announcements/{id} (admin)
- [x] Release 엔티티/리포지토리
- [x] GET /api/v1/releases (public)
- [x] GET /api/v1/releases/{id}
- [x] POST /api/v1/admin/releases (admin)
- [x] PATCH /api/v1/admin/releases/{id} (admin)
- [x] DELETE /api/v1/admin/releases/{id} (admin)
- [x] Pagination 구현 (page, size, sort)

### DoD
- [x] Public 엔드포인트 인증 없이 접근 가능
- [x] Admin 엔드포인트 ROLE_ADMIN 필요
- [x] Pagination 동작 확인

### Verification
```bash
curl http://localhost:8080/api/v1/announcements
curl http://localhost:8080/api/v1/releases
```

### Risks
- N+1 쿼리 문제 -> Fetch Join 또는 EntityGraph 사용

### Issues & Solutions
- N+1 문제 해결: Repository에서 JOIN FETCH 사용

---

## Phase 4: API Keys (secretOnce, hash 저장)
**상태: [ ] 대기**

### Goals
- API Key 관리 기능 구현
- 생성 시 secretOnce로 1회만 평문 반환
- DB에는 해시로만 저장

### Checklist
- [ ] ApiKey 엔티티/리포지토리
- [ ] GET /api/v1/api-keys (auth)
- [ ] POST /api/v1/api-keys (auth) -> secretOnce 반환
- [ ] DELETE /api/v1/api-keys/{id} (auth)
- [ ] API Key 해시 저장 구현
- [ ] prefix만 조회 가능하도록 구현

### DoD
- API Key 생성 시 secretOnce 1회만 반환
- 이후 조회 시 prefix만 표시
- 삭제 기능 동작

### Verification
```bash
curl -X POST http://localhost:8080/api/v1/api-keys \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{"name":"My API Key"}'
```

### Risks
- 해시 알고리즘 선택 (SHA-256 권장)

---

## Phase 5: Plans / Subscription (Stub)
**상태: [ ] 대기**

### Goals
- 플랜 및 구독 스텁 구현
- 결제 연동 없이 기본 구조만

### Checklist
- [ ] Plan 엔티티/리포지토리
- [ ] Subscription 엔티티/리포지토리
- [ ] GET /api/v1/plans (public)
- [ ] GET /api/v1/subscription (auth)
- [ ] POST /api/v1/subscription (auth) - 플랜 변경 스텁
- [ ] Seed 데이터에 plans 3개 (Free, Pro, Team) 추가

### DoD
- Plans 목록 조회 가능
- 구독 조회/변경 스텁 동작

### Verification
```bash
curl http://localhost:8080/api/v1/plans
```

### Risks
- 없음 (스텁 구현이므로)

---

## Phase 6: 테스트 / 정리
**상태: [ ] 대기**

### Goals
- 통합 테스트 작성
- export-openapi.sh 스크립트 작성
- 최종 문서 정리

### Checklist
- [ ] AuthIntegrationTest (register -> login -> refresh)
- [ ] AnnouncementIntegrationTest (public list)
- [ ] Testcontainers 설정
- [x] scripts/export-openapi.sh 작성 (Phase 0에서 완료)
- [ ] README.md 최종 업데이트
- [ ] 전체 기능 검증

### DoD
- 모든 테스트 통과
- export-openapi.sh 실행 시 openapi.json 생성
- README.md 최신 상태

### Verification
```bash
./gradlew test
./scripts/export-openapi.sh
```

### Risks
- Testcontainers Docker 필요

---

## 진행 기록

### 2026-01-16
- plan.md 최초 작성
- **Phase 0 완료**
  - Spring Boot 4.0.1 + Java 25 + Gradle 9.2.1 프로젝트 스캐폴딩
  - docker-compose.yml (PostgreSQL 17.7)
  - application.yml, application-dev.yml, application-prod.yml
  - README.md, .env.example, .gitignore
  - scripts/export-openapi.sh
  - `./gradlew build -x test` 성공
- **Phase 1 완료**
  - V1__init.sql: users, refresh_tokens, api_keys, plans, subscriptions, announcements, releases 테이블
  - OpenApiConfig.java: springdoc-openapi + JWT Bearer Auth 설정
  - SecurityConfig.java: 기본 보안 설정 (public 엔드포인트 허용)
  - TraceIdFilter.java: 요청별 traceId 생성 및 MDC 저장
  - GlobalExceptionHandler.java: RFC7807 Problem Details 에러 응답
  - FlywayConfig.java: Flyway 수동 설정 (autoconfiguration 미작동 해결)
  - Docker 포트 5433으로 변경 (로컬 PostgreSQL 5432 충돌 해결)
  - /actuator/health, /swagger-ui, /v3/api-docs 검증 완료
- **Phase 2 완료**
  - JwtProperties.java, JwtTokenProvider.java: JWT 토큰 생성/검증
  - JwtAuthenticationFilter.java: Bearer 토큰 인증 필터
  - CsrfProtectionFilter.java: Origin/Referer 검사 CSRF 방어
  - User.java, UserRepository.java: 사용자 엔티티 및 리포지토리
  - RefreshToken.java, RefreshTokenRepository.java: 리프레시 토큰 엔티티 및 리포지토리
  - AuthService.java, AuthController.java: 회원가입, 로그인, 토큰 갱신, 로그아웃
  - UserService.java, UserController.java: /api/v1/me 엔드포인트
  - V2__seed_dev.sql: 개발용 seed 계정 (admin, user)
  - DataInitializer.java: dev 환경에서 seed 비밀번호 업데이트
  - SecurityConfig.java 업데이트: JWT 필터, CSRF 필터, CORS 설정
  - 전체 인증 플로우 검증 완료 (register -> login -> refresh -> logout)
- **Phase 3 완료**
  - Announcement.java, AnnouncementRepository.java: 공지사항 엔티티/리포지토리
  - AnnouncementService.java: 공지사항 비즈니스 로직
  - AnnouncementController.java: Public 공지사항 API
  - AdminAnnouncementController.java: Admin 공지사항 CRUD API
  - Release.java, ReleaseRepository.java: 릴리스 엔티티/리포지토리
  - ReleaseService.java: 릴리스 비즈니스 로직
  - ReleaseController.java: Public 릴리스 API
  - AdminReleaseController.java: Admin 릴리스 CRUD API
  - Pagination (page, size, sort) 지원
  - N+1 문제 해결을 위한 JOIN FETCH 적용
