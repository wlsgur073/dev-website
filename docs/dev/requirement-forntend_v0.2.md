너는 Claude Code로 동작하는 시니어 프론트엔드 엔지니어다.
이 폴더(dev-portal-web)는 프론트엔드 전용 레포다. 백엔드(Spring/DB) 코드는 절대 만들지 마라.

목표:
Vue 3 + TypeScript + Vite로 “Developer Community Portal Web”을 구축한다.
장기적으로 커뮤니티/포럼(연동), API docs, 릴리즈노트, 공지, 과금(플랜/구독)까지 확장 가능한 UI 구조를 만든다.
이번 결과물은 로컬에서 즉시 실행 가능해야 하고, 백엔드 인증/콘솔 기능과 실제로 연결되어야 한다.

필수 스택/제약(고정):
- Vue 3 + TypeScript + Vite
- Vue Router, Pinia
- Tailwind CSS (무거운 UI 라이브러리 전체 도입 금지)
- axios 기반 API client
- Markdown 렌더: markdown-it
- 코드 하이라이트: shiki 또는 highlight.js 중 1개
- 코드 복사 버튼(필수)
- 라우트 코드 스플릿(필수)

디자인 가이드(분위기 참고만, 복제 금지):
- 개발자 포털 스타일: 상단 글로벌 네비, 검색, 카드 기반 섹션, 문서 사이드바, 깔끔한 타이포/여백
- 참고 사이트: developers.naver.com, developers.kakao.com, developer.confluent.io, community.openai.com, claude.develop-on.co.kr/en/community
- HTML/CSS/문구/자산을 그대로 베끼지 말 것. 정보구조와 UI 패턴만 참고.

운영/통합 규칙(고정):
- 프론트는 API를 항상 상대경로 /api 로 호출한다.
- 개발에서는 Vite proxy로 /api -> http://localhost:8080/api 로 전달한다(즉 CORS 회피).
- access token은 JS에서 메모리(또는 Pinia store)로만 관리한다.
- refresh token은 HttpOnly cookie이므로 JS에서 접근하지 않는다.
- 401 처리 규칙:
  - API 요청이 401이면 “단 1회” /api/v1/auth/refresh를 호출해 access token 재발급 시도
  - 성공하면 원래 요청 1회 재시도
  - 실패하면 로그인 페이지로 이동
  - 동시 다발 401은 refresh 락/큐로 1번만 refresh되게 처리(무한루프/중복요청 방지)

OpenAPI 타입 연동(필수):
- 백엔드 레포에서 export한 openapi.json을 이 레포의 /openapi/openapi.json에 넣는다고 가정한다.
- openapi-typescript를 사용해 /src/api/generated.ts를 생성하는 스크립트를 제공한다.
- 생성 스크립트:
  - pnpm gen:api -> openapi/openapi.json으로부터 타입 생성
- axios client는 generated 타입을 활용하거나 최소한 DTO 타입을 일관되게 정의한다.

레포 구조(고정):
/ (root)
  .env.example
  README.md
  requirement-forntend.md
  /openapi
    openapi.json (백엔드에서 가져온 파일)
  /src
    /api
      http.ts (axios 인스턴스, 인터셉터)
      auth.ts
      announcements.ts
      releases.ts
      plans.ts
      apikeys.ts
      subscription.ts
      generated.ts (자동 생성)
    /components
    /layouts
    /pages
    /router
    /stores
    /styles
    /content/docs (markdown docs 5개)
  tailwind.config.*
  vite.config.ts

필수 페이지/라우트(고정):
- Public
  - / (Home)
  - /docs (docs index)
  - /docs/:slug (markdown doc)
  - /api (API docs 안내 + swagger 링크)
  - /releases, /releases/:id
  - /announcements, /announcements/:id
  - /pricing
  - /community
  - /search (1차는 클라이언트 검색)
- Auth
  - /login, /register
- Console(protected)
  - /console
  - /console/api-keys
  - /console/billing
- Admin(protected, ROLE_ADMIN)
  - /admin/announcements
  - /admin/releases
- 404

Home 구성(필수):
- Hero + CTA(Quickstart/Docs/Console)
- Quickstart 카드 3개
- Popular Docs 섹션(정적 리스트)
- Latest Releases 섹션(API에서 최신 3개)
- Latest Announcements 섹션(API에서 최신 3개)
- Pricing CTA
- Community CTA

Docs(필수):
- 좌측 사이드바(섹션)
- 본문 markdown 렌더
- 우측 TOC
- 코드 블록 하이라이트 + 복사 버튼

Admin(필수 최소):
- 공지/릴리즈 리스트 + 생성/수정/삭제(간단한 폼)

Search(MVP):
- 1차: docs(정적), announcements, releases를 받아 클라이언트에서 필터링(간단)
- 이후 서버검색 교체 가능하도록 search 모듈 경계 분리

문서(필수):
- requirement-forntend.md 생성(요구사항/라우트/인증처리/디자인)
- README.md에 실행 방법:
  - pnpm i
  - pnpm dev
  - (타입 생성) pnpm gen:api

작업 방식(반드시 준수):
- Phase 0: 스캐폴딩 + Tailwind + 라우팅 + 레이아웃
- Phase 1: API client + 인증 플로우 + 보호 라우트
- Phase 2: Home/Docs/Announcements/Releases/Pricing/Community
- Phase 3: Console(API keys/Billing)
- Phase 4: Admin CRUD
- Phase 5: polish(에러 처리/로딩/접근성/코드 스플릿)

각 Phase마다:
- 변경 파일 목록
- 핵심 파일 전체 코드
- 실행 명령어
- 검증 방법(URL)
- 실패 가능 포인트와 해결책

지금 Phase 0부터 시작해라. 먼저 라우트/레이아웃/컴포넌트 구조를 제안하고, 바로 파일을 생성해라.
