# Dev Website

개발자 포털 웹사이트 - Full Stack 애플리케이션

## 프로젝트 구조

```
dev-website/
├── backend/          # Spring Boot API 서버
├── frontend/         # Vue 3 SPA 클라이언트
└── docs/             # 프로젝트 문서
```

## 기술 스택

### Backend
- **Framework**: Spring Boot 4.0.1 + Java 25
- **Database**: PostgreSQL 17.7 + Flyway
- **Security**: Spring Security + JWT (Access 15분 / Refresh 14일)
- **API Docs**: springdoc-openapi (Swagger UI)
- **Build**: Gradle 9.2.1

### Frontend
- **Framework**: Vue 3 (Composition API) + TypeScript
- **Build**: Vite
- **Styling**: Tailwind CSS
- **State**: Pinia
- **Routing**: Vue Router
- **HTTP**: Axios
- **Icons**: Heroicons
- **Markdown**: markdown-it + Shiki

## 사전 요구사항

- Java 25+
- Node.js 18+
- Docker & Docker Compose
- pnpm (권장) 또는 npm

## 빠른 시작

### 1. PostgreSQL 실행

```bash
cd backend
docker compose up -d
```

### 2. Backend 실행

```bash
cd backend
./gradlew bootRun
```

- Health: http://localhost:8080/actuator/health
- Swagger UI: http://localhost:8080/swagger-ui/index.html

### 3. Frontend 실행

```bash
cd frontend
npm install
npm run dev
```

- 개발 서버: http://localhost:5173

## 개발용 계정

| 역할 | 이메일 | 비밀번호 |
|------|--------|----------|
| Admin | admin@example.com | admin123 |
| User | user@example.com | user123 |

## 아키텍처

```
┌─────────────────────────────────────────────────────────────┐
│                      개발 환경 구성                            │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│   ┌─────────────┐      /api/*       ┌─────────────┐        │
│   │  Frontend   │ ───────────────▶  │   Backend   │        │
│   │  (Vue 3)    │    Vite Proxy     │(Spring Boot)│        │
│   │  :5173      │                   │   :8080     │        │
│   └─────────────┘                   └──────┬──────┘        │
│         ▲                                  │               │
│         │                                  ▼               │
│      브라우저                        ┌─────────────┐        │
│                                     │ PostgreSQL  │        │
│                                     │   :5433     │        │
│                                     └─────────────┘        │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## 주요 기능

### 공개 페이지
- `/` - 홈
- `/docs` - API 문서
- `/announcements` - 공지사항
- `/releases` - 릴리스 노트
- `/pricing` - 가격 정책

### 인증
- `/login` - 로그인
- `/register` - 회원가입

### Console (인증 필요)
- `/console` - 대시보드
- `/console/api-keys` - API 키 관리
- `/console/billing` - 결제 정보

### Admin (관리자 전용)
- `/admin/announcements` - 공지사항 관리
- `/admin/releases` - 릴리스 관리

## API 엔드포인트

### 인증
| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/v1/auth/register` | 회원가입 |
| POST | `/api/v1/auth/login` | 로그인 |
| POST | `/api/v1/auth/refresh` | 토큰 갱신 |
| POST | `/api/v1/auth/logout` | 로그아웃 |

### 사용자
| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/api/v1/me` | 내 정보 조회 |
| PATCH | `/api/v1/me` | 내 정보 수정 |

### 공지사항
| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/api/v1/announcements` | 목록 조회 |
| GET | `/api/v1/announcements/{id}` | 상세 조회 |
| POST | `/api/v1/admin/announcements` | 생성 (관리자) |
| PATCH | `/api/v1/admin/announcements/{id}` | 수정 (관리자) |
| DELETE | `/api/v1/admin/announcements/{id}` | 삭제 (관리자) |

### 릴리스
| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/api/v1/releases` | 목록 조회 |
| GET | `/api/v1/releases/{id}` | 상세 조회 |
| POST | `/api/v1/admin/releases` | 생성 (관리자) |
| PATCH | `/api/v1/admin/releases/{id}` | 수정 (관리자) |
| DELETE | `/api/v1/admin/releases/{id}` | 삭제 (관리자) |

### API Keys
| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/api/v1/api-keys` | 목록 조회 |
| POST | `/api/v1/api-keys` | 생성 |
| DELETE | `/api/v1/api-keys/{id}` | 삭제 |

### 플랜/구독
| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/api/v1/plans` | 플랜 목록 |
| GET | `/api/v1/subscription` | 내 구독 조회 |
| POST | `/api/v1/subscription` | 플랜 변경 |

## 테스트

### Backend 테스트

```bash
cd backend
./gradlew test
```

> Testcontainers를 사용하여 PostgreSQL 17 컨테이너에서 실행됩니다.

### Frontend 빌드

```bash
cd frontend
npm run build
```

## 환경 변수

### Backend

| 변수명 | 설명 | 기본값 |
|--------|------|--------|
| DB_HOST | PostgreSQL 호스트 | localhost |
| DB_PORT | PostgreSQL 포트 | 5433 |
| DB_NAME | 데이터베이스 이름 | devdb |
| DB_USER | 데이터베이스 사용자 | devuser |
| DB_PASSWORD | 데이터베이스 비밀번호 | devpass |
| JWT_SECRET | JWT 서명 키 (256비트 이상) | - |
| CORS_ORIGINS | 허용된 CORS Origin | http://localhost:3000,http://localhost:5173 |

## 상세 문서

- [Backend README](./backend/README.md)
- [Frontend README](./frontend/README.md)
