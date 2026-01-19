# Frontend-Backend 통합 테스트 계획서

## 개요

이 문서는 Vue 3 Frontend와 Spring Boot 4.0.1 Backend의 연계 통합 테스트를 위한 절차와 수행 방법을 정리합니다.

---

## 아키텍처 구성도

```
┌─────────────────────────────────────────────────────────────┐
│                      개발 환경 구성                           │
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

---

## 테스트 단계

### Step 1: 사전 환경 점검
- [ ] Java 25 설치 확인
- [ ] Node.js 18+ 설치 확인
- [ ] Docker 설치 및 실행 확인
- [ ] 필요한 포트 사용 가능 여부 확인 (5173, 8080, 5433)

### Step 2: PostgreSQL 데이터베이스 실행
- [ ] Docker Compose로 PostgreSQL 컨테이너 실행
- [ ] 데이터베이스 연결 테스트
- [ ] 컨테이너 health check 확인

### Step 3: Backend 서버 실행 및 검증
- [ ] Gradle 빌드 실행
- [ ] Spring Boot 서버 시작
- [ ] Actuator health 엔드포인트 확인
- [ ] Swagger UI 접근 확인
- [ ] Flyway 마이그레이션 완료 확인

### Step 4: Frontend 서버 실행 및 검증
- [ ] npm 의존성 설치
- [ ] Vite 개발 서버 시작
- [ ] 브라우저에서 접근 확인

### Step 5: Frontend-Backend 연계 테스트
- [ ] API 프록시 동작 확인
- [ ] 인증 플로우 테스트 (로그인/로그아웃)
- [ ] CORS 설정 확인
- [ ] 에러 핸들링 확인

---

## 상세 수행 방법

### Step 1: 사전 환경 점검

```bash
# Java 버전 확인
java --version

# Node.js 버전 확인
node --version
npm --version

# Docker 상태 확인
docker --version
docker ps
```

**예상 결과:**
- Java: 25.x
- Node.js: 18.x 이상
- Docker: 실행 중

---

### Step 2: PostgreSQL 데이터베이스 실행

```bash
cd backend
docker compose up -d
```

**검증 명령어:**
```bash
# 컨테이너 상태 확인
docker ps --filter name=backend-postgres

# 로그 확인
docker logs backend-postgres

# 연결 테스트
docker exec backend-postgres pg_isready -U devuser -d devdb
```

**예상 결과:**
- 컨테이너 상태: Up, healthy
- 포트: 5433 바인딩

---

### Step 3: Backend 서버 실행 및 검증

```bash
cd backend
./gradlew bootRun
```

**검증 URL:**
| URL | 예상 결과 |
|-----|----------|
| http://localhost:8080/actuator/health | `{"status":"UP"}` |
| http://localhost:8080/swagger-ui/index.html | Swagger UI 페이지 |
| http://localhost:8080/v3/api-docs | OpenAPI JSON |

---

### Step 4: Frontend 서버 실행 및 검증

```bash
cd frontend
npm install
npm run dev
```

**검증:**
- http://localhost:5173 접속
- 페이지 정상 렌더링 확인

---

### Step 5: Frontend-Backend 연계 테스트

#### 5.1 API 프록시 테스트
브라우저 개발자 도구에서 Network 탭 확인:
- `/api/*` 요청이 백엔드로 프록시되는지 확인
- 응답 상태 코드 확인

#### 5.2 인증 플로우 테스트
1. 회원가입 시도
2. 로그인 시도
3. 인증된 API 호출
4. 토큰 갱신 (자동)
5. 로그아웃

#### 5.3 테스트 계정 (dev 환경)
- README.md 참조

---

## 트러블슈팅

### 포트 충돌
```bash
# Windows - 포트 사용 프로세스 확인
netstat -ano | findstr :8080
netstat -ano | findstr :5173
netstat -ano | findstr :5433

# 프로세스 종료
taskkill /PID <PID> /F
```

### Docker 관련 문제
```bash
# 컨테이너 재시작
docker compose down
docker compose up -d

# 볼륨 초기화 (데이터 삭제)
docker compose down -v
docker compose up -d
```

### Backend 빌드 실패
```bash
# Gradle 캐시 정리
./gradlew clean
./gradlew bootRun
```

### Frontend 빌드 실패
```bash
# node_modules 재설치
rm -rf node_modules
npm install
```

---

## 체크리스트 요약

| 단계 | 항목 | 상태 |
|------|------|------|
| Step 1 | Java 25 확인 | ✅ |
| Step 1 | Node.js 18+ 확인 | ✅ |
| Step 1 | Docker 실행 확인 | ✅ |
| Step 2 | PostgreSQL 컨테이너 실행 | ✅ |
| Step 2 | DB 연결 테스트 | ✅ |
| Step 3 | Backend 서버 시작 | ✅ |
| Step 3 | Health 엔드포인트 확인 | ✅ |
| Step 3 | Swagger UI 확인 | ✅ |
| Step 4 | Frontend 의존성 설치 | ✅ |
| Step 4 | Vite 서버 시작 | ✅ |
| Step 5 | API 프록시 동작 확인 | ✅ |
| Step 5 | 인증 플로우 테스트 | ✅ |
| Step 5 | CORS 설정 확인 | ✅ |

---

## 테스트 결과 상세

### Step 1: 사전 환경 점검 ✅
- Java: 25.0.1 (C:\Program Files\Java\jdk-25.0.1)
- Node.js: 24.10.0
- npm: 11.6.1
- Docker: 29.0.2-rd

### Step 2: PostgreSQL ✅
- 컨테이너: backend-postgres (healthy)
- 포트: 5433:5432
- 상태: accepting connections

### Step 3: Backend 서버 ✅
- 포트: 8080
- Health: UP (DB, Disk, SSL 정상)
- Swagger UI: 접근 가능
- OpenAPI JSON: 정상 반환

### Step 4: Frontend 서버 ✅
- Vite: v6.4.1
- 포트: 5173
- 상태: 200 OK

### Step 5: 연계 테스트 ✅
- API 프록시: /api/* → localhost:8080 정상 동작
- 로그인 테스트: admin@example.com 로그인 성공
- 인증 API: /api/v1/me 정상 응답
- CORS: Access-Control-Allow-Origin 설정 정상

---

## 진행 상태

- **현재 단계**: 모든 테스트 완료 ✅
- **최종 업데이트**: 2026-01-19
