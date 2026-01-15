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
- **Markdown**: markdown-it + Shiki (코드 하이라이트)

## 시작하기

### 필수 요구사항

- Node.js 18+
- pnpm (권장) 또는 npm

### 설치

```bash
# 의존성 설치
pnpm install
# 또는
npm install

# 개발 서버 실행
pnpm dev
# 또는
npm run dev
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
│   ├── http.ts       # Axios 인스턴스 및 401 인터셉터
│   ├── auth.ts       # 인증 API
│   ├── announcements.ts
│   ├── releases.ts
│   ├── plans.ts
│   ├── apikeys.ts
│   ├── subscription.ts
│   └── generated.ts  # OpenAPI 자동 생성 타입
├── assets/           # 정적 자산 + credits.md
├── components/       # 공통 컴포넌트
│   ├── AppHeader.vue
│   ├── AppFooter.vue
│   ├── ThemeToggle.vue
│   ├── Toast.vue
│   ├── MarkdownRenderer.vue
│   └── ...
├── composables/      # Vue Composables
│   ├── useToast.ts
│   └── useSeoMeta.ts
├── content/docs/     # 문서 Markdown 파일
├── layouts/          # 레이아웃 컴포넌트
│   ├── DefaultLayout.vue
│   ├── ConsoleLayout.vue
│   ├── AdminLayout.vue
│   ├── AuthLayout.vue
│   └── DocsLayout.vue
├── pages/            # 페이지 컴포넌트
├── router/           # Vue Router 설정
├── stores/           # Pinia 스토어 (auth, theme)
├── styles/           # Tailwind 기반 전역 스타일
└── utils/            # 유틸리티 함수
```

## 주요 기능

### 인증 시스템
- Access Token: 메모리 저장 (Pinia)
- Refresh Token: HttpOnly Cookie
- 401 발생 시 자동 토큰 갱신 및 요청 재시도
- 동시 요청 시 refresh 락/큐로 중복 방지

### 다크모드
- Light / Dark / System 모드 지원
- localStorage 저장으로 새로고침 후에도 유지
- FOUC 방지 스크립트 적용

### SEO
- 페이지별 title/description 메타 태그
- Open Graph / Twitter Card 지원
- robots.txt, sitemap.xml 제공

### 접근성
- Skip to content 링크
- 키보드 네비게이션 지원
- ARIA 속성 적용
- prefers-reduced-motion 존중

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

## 라이선스

자세한 라이선스 정보는 [src/assets/credits.md](src/assets/credits.md)를 참조하세요.
