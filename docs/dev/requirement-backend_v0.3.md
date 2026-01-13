너는 Claude Code로 동작하는 시니어 백엔드 엔지니어다.
이 폴더는 "백엔드(backend)"이며 프론트엔드 파일(Vue/Vite/HTML/CSS)은 절대 만들지 마라.

가장 중요한 규칙(실패 조건):
- requirement-backend.md에 적힌 요구사항을 단 하나라도 무시하면 실패다.
- "Plan-first 규칙"을 반드시 따른다: plan.md를 먼저 만들고, Phase 단위로 구현하며, 각 Phase 종료 때마다 plan.md를 체크/갱신해야 한다.

입력 문서:
- requirement-backend.md (이 파일이 단일 진실이다. 요구사항/경계/규칙은 여기서 바꾸지 말고 그대로 구현한다)

필수 작업 프로토콜(엄격):
1) 구현 시작 전에 plan.md를 반드시 생성/갱신한다.
   - plan.md에는 Phase 0..N을 정의하고, 각 Phase마다 Goals/Checklist/DoD/Verification/Risks를 포함한다.
2) 모든 구현은 plan.md의 체크리스트를 기준으로 진행한다.
3) 각 Phase를 끝낼 때마다:
   - plan.md에서 완료 항목 체크
   - 발생한 이슈와 해결책을 plan.md에 기록
   - README.md에 실행/검증 방법이 최신인지 반영
4) 매 세션 시작 시 plan.md를 읽고 다음 미완료 항목부터 진행한다.
5) Claude Code의 설명/출력은 한국어로 작성하되, 코드/주석/식별자는 영어를 권장한다.

레포 구조(고정, 반드시 이대로):
/
  README.md
  requirement-backend.md
  plan.md
  docker-compose.yml
  .env.example
  /scripts
    export-openapi.sh
  /src/main/java/...
  /src/main/resources
    application.yml
    application-dev.yml
    application-prod.yml
    db/migration/V1__init.sql
    db/migration/V2__seed_dev.sql
  /src/test/java/...

기술 스택(고정):
- Spring Boot 3 + Java 21 + Gradle
- PostgreSQL + Flyway
- Spring Security
- JWT Access Token(15분) + Refresh Token(HttpOnly cookie 14일, rotation)
- RFC7807 Problem Details(JSON) + traceId
- springdoc-openapi-starter-webmvc-ui (/v3/api-docs, /swagger-ui/index.html)
- Actuator(최소 /actuator/health)

API/경로 규칙(고정):
- 서버 포트: 8080
- API base: /api/v1
- Swagger: /swagger-ui/index.html
- OpenAPI JSON: /v3/api-docs

보안 규칙(고정):
- 비밀번호 bcrypt
- Refresh token 원문 저장 금지(해시로만 DB 저장)
- Refresh rotation 필수(이전 토큰 revoke + replaced_by_id 기록)
- /auth/refresh, /auth/logout는 쿠키 기반이므로 CSRF 최소 방어 필수:
  - Origin/Referer 검사 또는 double-submit 중 하나를 반드시 구현하고, README에 문서화
- RBAC: ROLE_USER, ROLE_ADMIN

DB/Flyway 최소 스키마(고정):
- users, refresh_tokens, api_keys, announcements, releases, plans, subscriptions
- 인덱스/unique는 requirement-backend.md 기준

필수 엔드포인트(고정):
- POST /api/v1/auth/register
- POST /api/v1/auth/login (refresh cookie set)
- POST /api/v1/auth/refresh (rotation)
- POST /api/v1/auth/logout
- GET /api/v1/me
- PATCH /api/v1/me
- Announcements:
  - GET /api/v1/announcements (public, published only)
  - GET /api/v1/announcements/{id}
  - POST/PATCH/DELETE /api/v1/admin/announcements (admin)
- Releases:
  - GET /api/v1/releases (public)
  - GET /api/v1/releases/{id}
  - POST/PATCH/DELETE /api/v1/admin/releases (admin)
- API Keys:
  - GET /api/v1/api-keys (auth)
  - POST /api/v1/api-keys (auth) -> secretOnce 1회만 반환
  - DELETE /api/v1/api-keys/{id} (auth)
- Billing(stub):
  - GET /api/v1/plans (public)
  - GET /api/v1/subscription (auth)
  - POST /api/v1/subscription (auth) (플랜 변경 스텁)

개발용 seed(dev에서만):
- admin 1명, user 1명 (README에 크리덴셜 명시)
- plans 3개(Free, Pro, Team)
- announcements 3개, releases 3개

필수 스크립트:
- scripts/export-openapi.sh
  - 서버 실행 중 /v3/api-docs를 내려받아 루트 ./openapi.json으로 저장

테스트(최소):
- 통합 테스트 2개 이상:
  1) auth: register -> login -> refresh
  2) content: announcements public list
- 가능하면 Testcontainers(Postgres). 불가능하면 대체(H2 등)하되 README에 한계를 기록.

진행 방식(반드시 Phase로 진행, 실행 가능한 상태 유지):
- Phase 0: plan.md 작성/갱신 + 프로젝트 스캐폴딩 + docker-compose + 문서(README/.env.example)
- Phase 1: Flyway(V1 init) + OpenAPI + Problem Details + health
- Phase 2: Auth(JWT+refresh rotation+CSRF 방어) + seed 계정
- Phase 3: announcements/releases(public+admin CRUD) + pagination/filter
- Phase 4: API keys(secretOnce, hash 저장)
- Phase 5: plans/subscription(entitlement 스텁)
- Phase 6: 테스트/정리(export-openapi 포함)

각 Phase 출력 형식(반드시 지켜라):
- 변경 파일 목록
- 핵심 파일 "전체 코드"(중요한 파일만, 누락 없이)
- 실행 명령어
- 검증 방법(URL/테스트 명령)
- 실패 가능 포인트와 해결책
- plan.md 체크/갱신 요약(무엇을 체크했고 무엇이 남았는지)

지금 바로 시작해라.
1) requirement-backend.md를 읽고 요구사항을 위배하지 않는 범위에서 plan.md를 작성/갱신한다.
2) Phase 0을 완료한다(스캐폴딩/문서/도커/실행 확인).
3) Phase 0 완료 후, plan.md를 체크 처리하고 다음 Phase(Phase 1)로 넘어갈 준비 상태를 만들고 멈춘다(다음 지시는 내가 한다).
