# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 프로젝트 개요

개발자 포털 백엔드 API 서버. Spring Boot + PostgreSQL 기반이며, API base는 `/api/v1`.

## 기술 스택

- Spring Boot 4.0.1 + Java 25 + Gradle 9.2.1
- PostgreSQL 17.7 + Flyway (마이그레이션)
- Spring Security + JWT (Access 15분 + Refresh HttpOnly cookie 14일, rotation)
- springdoc-openapi (Swagger UI, OpenAPI JSON)
- RFC7807 Problem Details + traceId

## 주요 명령어

```bash
# PostgreSQL 실행 (로컬 개발)
docker compose up -d

# 서버 실행
./gradlew bootRun

# 테스트 실행
./gradlew test

# OpenAPI JSON 추출 (서버 실행 중)
./scripts/export-openapi.sh
```

## 주요 엔드포인트

- Health: `http://localhost:8080/actuator/health`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 프로젝트 구조

```
backend/
├── src/main/java/com/devwebsite/backend/
│   ├── common/           # 공통 설정 (SecurityConfig, TraceIdFilter, GlobalExceptionHandler)
│   ├── auth/             # 인증 (JWT, Refresh Token rotation)
│   ├── user/             # 사용자 (/me)
│   ├── announcement/     # 공지사항 CRUD
│   ├── release/          # 릴리스 노트 CRUD
│   ├── apikey/           # API 키 관리 (secretOnce, hash 저장)
│   └── billing/          # 플랜/구독 (스텁)
├── src/main/resources/
│   ├── application.yml, application-dev.yml, application-prod.yml
│   └── db/migration/     # Flyway 마이그레이션
├── src/test/java/        # 통합 테스트 (Testcontainers)
├── scripts/export-openapi.sh
├── docker-compose.yml
└── .env.example
```

## 아키텍처 규칙

**보안:**
- 비밀번호: bcrypt 해시
- Refresh token: 해시로만 DB 저장 (원문 저장 금지)
- Refresh rotation: 이전 토큰 revoke + replaced_by_id 기록
- `/auth/refresh`, `/auth/logout`: CSRF 방어 필수 (Origin/Referer 검사)
- RBAC: ROLE_USER, ROLE_ADMIN

**API:**
- 에러 응답: RFC7807 Problem Details (JSON) + traceId
- 인증: Bearer JWT (Access Token)
- API Keys: 생성 시 secretOnce로 1회만 평문 반환, 이후 prefix만 조회 가능

**DB 스키마:**
- users, refresh_tokens, api_keys, announcements, releases, plans, subscriptions

## 개발용 seed 계정 (dev 환경)

README.md에 크리덴셜 명시 (admin 1명, user 1명)

## 작업 프로토콜

1. 구현 전 `plan.md` 작성/갱신
2. Phase 단위 구현 (Phase 0~6)
3. 각 Phase 완료 시 `plan.md` 체크 및 `README.md` 최신화
4. 코드/주석/식별자는 영어, 문서/설명은 한국어
