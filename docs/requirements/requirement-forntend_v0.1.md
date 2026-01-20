# Developer Community Portal Frontend Requirements
Stack: Vue 3, TypeScript, Vite, Vue Router, Pinia, Tailwind CSS, pnpm

## 1. Goals
- Developer Portal UI: Home, Docs, API Docs 링크, Releases, Announcements, Pricing, Community
- Auth UI: 회원가입, 로그인, 로그아웃, 프로필
- Console: API Key 관리, 구독 상태 조회, 플랜 변경(스텁)
- Admin: 공지/릴리즈노트 CRUD UI 최소 제공
- 레퍼런스 스타일: 개발자 포털 느낌(상단 네비, 검색, 카드, 문서 사이드바)

## 2. Design requirements
- 전역 레이아웃
  - Header: 로고, 메뉴(Docs, API, Releases, Announcements, Pricing, Community, Console)
  - 우측: 검색, 로그인 버튼 또는 프로필 드롭다운
  - Footer: 링크, 약관, GitHub, 문의
- Home
  - Hero + CTA(Quickstart, Docs, Console)
  - Quickstart 카드 3개
  - 인기 Docs 섹션
  - 최신 Releases, Announcements 섹션
  - Pricing CTA
  - Community CTA
- Docs
  - 좌측 사이드바(섹션별)
  - 본문 마크다운 렌더
  - 우측 TOC
  - 코드 블록 하이라이트, 복사 버튼
- Releases, Announcements
  - 리스트(필터: 태그, 버전, 날짜), 상세 페이지
- Pricing
  - 플랜 카드(가격, 기능), 현재 구독 상태 표시
- Community
  - 외부 포럼 링크(추후 임베드/SSO TODO 안내)

## 3. Non-functional requirements
- 접근성: 키보드 탐색 가능, aria 속성
- 성능: 라우트 단위 코드 스플릿, 이미지 최적화
- 에러 처리: API 에러 메시지 표준화(Problem Details 대응)
- 인증 토큰 처리:
  - access token은 메모리 또는 pinia store에 보관
  - refresh는 HttpOnly cookie를 사용하므로 JS에서 직접 접근하지 않는다
  - 401 발생 시 refresh 후 재시도 로직(무한 루프 방지)
- 환경 변수:
  - VITE_API_BASE_URL 또는 dev proxy 사용

## 4. Tech decisions (권장)
- Styling: Tailwind CSS
- Markdown:
  - markdown-it 또는 mdx 대체 라이브러리 중 하나 선택
  - 코드 하이라이트: shiki 또는 highlight.js
- Data fetching:
  - axios 기반 API 클라이언트
  - (선택) @tanstack/vue-query로 캐시/로딩 상태 표준화
- Validation:
  - zod로 폼 입력 검증(프론트에서 1차), 서버 검증이 최종

## 5. Routes
- / (Home)
- /docs (docs index)
- /docs/:slug (doc page)
- /api (API docs 안내, swagger 링크 또는 embedded)
- /releases, /releases/:id
- /announcements, /announcements/:id
- /pricing
- /community
- /login, /register
- /console (protected)
  - /console/api-keys
  - /console/billing
- /admin (protected, ROLE_ADMIN)
  - /admin/announcements
  - /admin/releases
- /404

## 6. Components checklist
- AppHeader, AppFooter
- SearchBox(초기: 클라이언트 검색)
- Card, Button, Badge, Tabs, Modal(최소 직접 구현)
- MarkdownRenderer + Toc + CodeBlockCopy
- DataTable(관리 화면용 최소 기능)

## 7. Search MVP
- 1차: 공지, 릴리즈노트, docs(정적 파일)에서 인덱스 생성 후 fuse.js 같은 클라이언트 검색
- 2차: 서버 검색(OpenSearch 등)으로 교체 가능하게 인터페이스 분리

## 8. Definition of Done
- pnpm dev로 실행 가능
- 로그인 플로우 동작(로그인 -> Console 접근 -> API key 발급)
- Docs 마크다운 렌더와 TOC, 코드 복사 정상
- Releases, Announcements 리스트/상세 동작
- 에러 상태(401, 403, 404, 500) 화면 처리
