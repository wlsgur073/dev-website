# Dev Website Frontend

Vue 3 + TypeScript + Vite 기반 프론트엔드 애플리케이션

## 기술 스택

- **Framework**: Vue 3 (Composition API)
- **Language**: TypeScript
- **Build Tool**: Vite
- **Styling**: Tailwind CSS
- **State Management**: Pinia
- **Routing**: Vue Router
- **HTTP Client**: Axios
- **Icons**: Heroicons

## 시작하기

### 필수 요구사항

- Node.js 18+
- pnpm (권장) 또는 npm

### 설치

```bash
# 의존성 설치
pnpm install

# 개발 서버 실행
pnpm dev
```

### 스크립트

| 명령어 | 설명 |
|--------|------|
| `pnpm dev` | 개발 서버 실행 (http://localhost:5173) |
| `pnpm build` | 프로덕션 빌드 |
| `pnpm preview` | 프로덕션 빌드 미리보기 |
| `pnpm gen:api` | OpenAPI 스키마로부터 TypeScript 타입 생성 |

## 프로젝트 구조

```
src/
├── api/              # API 클라이언트 모듈
│   ├── http.ts       # Axios 인스턴스 및 인터셉터
│   ├── auth.ts       # 인증 API
│   ├── announcements.ts
│   ├── releases.ts
│   ├── plans.ts
│   ├── apikeys.ts
│   ├── subscription.ts
│   └── generated.ts  # OpenAPI 자동 생성 타입
├── assets/           # 정적 자산
├── components/       # 공통 컴포넌트
├── content/docs/     # 문서 Markdown 파일
├── layouts/          # 레이아웃 컴포넌트
├── pages/            # 페이지 컴포넌트
├── router/           # Vue Router 설정
├── stores/           # Pinia 스토어
└── styles/           # 전역 스타일
```

## 개발 환경

### API Proxy

개발 환경에서 `/api` 요청은 Vite proxy를 통해 `http://localhost:8080/api`로 전달됩니다.

### 환경 변수

`.env.example`을 복사하여 `.env`를 만들고 필요한 값을 설정하세요.

```bash
cp .env.example .env
```

## 라우트 구조

### Public
- `/` - 홈
- `/docs`, `/docs/:slug` - 문서
- `/api` - API 문서 안내
- `/releases`, `/releases/:id` - 릴리즈
- `/announcements`, `/announcements/:id` - 공지사항
- `/pricing` - 가격 정책
- `/community` - 커뮤니티
- `/search` - 검색

### Auth
- `/login` - 로그인
- `/register` - 회원가입

### Console (인증 필요)
- `/console` - 대시보드
- `/console/api-keys` - API 키 관리
- `/console/billing` - 결제 정보

### Admin (관리자 권한 필요)
- `/admin/announcements` - 공지사항 관리
- `/admin/releases` - 릴리즈 관리
