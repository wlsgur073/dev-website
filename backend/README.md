# Dev Website Backend

개발자 포털 백엔드 API 서버

## 기술 스택

- **Framework**: Spring Boot 4.0.1
- **Language**: Java 25
- **Build Tool**: Gradle 9.2.1
- **Database**: PostgreSQL 17.7 + Flyway
- **Security**: Spring Security + JWT (Access Token 15분 + Refresh Token 14일)
- **API Documentation**: springdoc-openapi (Swagger UI)
- **Error Response**: RFC7807 Problem Details + traceId

## 사전 요구사항

- Java 25+
- Docker & Docker Compose
- Gradle 9.2.1 (또는 Gradle Wrapper 사용)

## 빠른 시작

### 1. 환경 설정

```bash
# .env 파일 생성
cp .env.example .env

# 필요시 .env 파일 수정
```

### 2. PostgreSQL 실행

```bash
docker compose up -d
```

### 3. 서버 실행

```bash
./gradlew bootRun
```

### 4. 확인

- Health Check: http://localhost:8080/actuator/health
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## 개발용 Seed 계정

| Role | Email | Password |
|------|-------|----------|
| ADMIN | admin@example.com | admin123 |
| USER | user@example.com | user123 |

## API 엔드포인트

### 인증 (Auth)
- `POST /api/v1/auth/register` - 회원가입
- `POST /api/v1/auth/login` - 로그인 (Refresh Token 쿠키 설정)
- `POST /api/v1/auth/refresh` - Access Token 갱신
- `POST /api/v1/auth/logout` - 로그아웃

### 사용자 (User)
- `GET /api/v1/me` - 내 정보 조회
- `PATCH /api/v1/me` - 내 정보 수정

### 공지사항 (Announcements)
- `GET /api/v1/announcements` - 공지사항 목록 (공개)
- `GET /api/v1/announcements/{id}` - 공지사항 상세 (공개)
- `POST /api/v1/admin/announcements` - 공지사항 생성 (관리자)
- `PATCH /api/v1/admin/announcements/{id}` - 공지사항 수정 (관리자)
- `DELETE /api/v1/admin/announcements/{id}` - 공지사항 삭제 (관리자)

### 릴리스 노트 (Releases)
- `GET /api/v1/releases` - 릴리스 목록 (공개)
- `GET /api/v1/releases/{id}` - 릴리스 상세 (공개)
- `POST /api/v1/admin/releases` - 릴리스 생성 (관리자)
- `PATCH /api/v1/admin/releases/{id}` - 릴리스 수정 (관리자)
- `DELETE /api/v1/admin/releases/{id}` - 릴리스 삭제 (관리자)

### API Keys
- `GET /api/v1/api-keys` - API Key 목록 (인증 필요)
- `POST /api/v1/api-keys` - API Key 생성 (인증 필요)
- `DELETE /api/v1/api-keys/{id}` - API Key 삭제 (인증 필요)

### 플랜/구독 (Billing)
- `GET /api/v1/plans` - 플랜 목록 (공개)
- `GET /api/v1/subscription` - 내 구독 정보 (인증 필요)
- `POST /api/v1/subscription` - 플랜 변경 (인증 필요)

## 테스트

```bash
./gradlew test
```

## OpenAPI JSON 추출

```bash
# 서버가 실행 중인 상태에서
./scripts/export-openapi.sh
```

## CSRF 방어

`/auth/refresh`, `/auth/logout` 엔드포인트는 쿠키 기반이므로 CSRF 공격에 취약할 수 있습니다.
이 프로젝트에서는 **Origin/Referer 검사** 방식으로 CSRF를 방어합니다.

- 허용된 Origin만 요청 가능
- Referer 헤더 검증

## 환경 변수

| 변수명 | 설명 | 기본값 |
|--------|------|--------|
| DB_HOST | PostgreSQL 호스트 | localhost |
| DB_PORT | PostgreSQL 포트 | 5433 |
| DB_NAME | 데이터베이스 이름 | devdb |
| DB_USER | 데이터베이스 사용자 | devuser |
| DB_PASSWORD | 데이터베이스 비밀번호 | devpass |
| JWT_SECRET | JWT 서명 키 (256비트 이상) | - |
| SPRING_PROFILES_ACTIVE | 활성 프로필 | dev |
| CORS_ORIGINS | 허용된 CORS Origin | http://localhost:3000,http://localhost:5173 |

## 프로젝트 구조

```
backend/
├── src/main/java/com/devwebsite/backend/
│   ├── common/           # 공통 설정 (SecurityConfig, GlobalExceptionHandler, TraceIdFilter)
│   ├── auth/             # 인증 (JWT, Refresh Token rotation)
│   ├── user/             # 사용자 (/me)
│   ├── announcement/     # 공지사항 CRUD
│   ├── release/          # 릴리스 노트 CRUD
│   ├── apikey/           # API 키 관리
│   └── billing/          # 플랜/구독 (스텁)
├── src/main/resources/
│   ├── application.yml
│   ├── application-dev.yml
│   ├── application-prod.yml
│   └── db/migration/
│       ├── V1__init.sql
│       └── V2__seed_dev.sql
├── src/test/java/
├── scripts/
│   └── export-openapi.sh
├── docker-compose.yml
├── .env.example
├── plan.md
└── README.md
```
