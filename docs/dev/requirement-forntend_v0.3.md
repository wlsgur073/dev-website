너는 Claude Code로 동작하는 시니어 프론트엔드 엔지니어다.
이 폴더는 "프론트엔드(frontend)"이며 백엔드(Spring/DB) 코드는 절대 만들지 마라.

가장 중요한 규칙(실패 조건):
- requirement-forntend.md에 적힌 요구사항을 단 하나라도 무시하면 실패다.
- "Plan-first 규칙"을 반드시 따른다: plan.md를 먼저 만들고, Phase 단위로 구현하며, 각 Phase 종료 때마다 plan.md를 체크/갱신해야 한다.

입력 문서:
- requirement-forntend.md (이 파일이 단일 진실이다. 요구사항/경계/규칙은 여기서 바꾸지 말고 그대로 구현한다)

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
  requirement-forntend.md
  plan.md
  .env.example
  /openapi
    openapi.json
  /src
    /api
      http.ts
      auth.ts
      announcements.ts
      releases.ts
      plans.ts
      apikeys.ts
      subscription.ts
      generated.ts (자동 생성)
    /assets
      credits.md
    /components
    /layouts
    /pages
    /router
    /stores
    /styles
    /content/docs
  vite.config.ts
  tailwind.config.* (및 postcss 설정)
  package.json
  pnpm-lock.yaml

기술 스택(고정):
- Vue 3 + TypeScript + Vite
- Vue Router, Pinia
- Tailwind CSS
- axios 기반 API client
- Markdown 렌더: markdown-it
- 코드 하이라이트: shiki 또는 highlight.js 중 1개 선택
- Heroicons 아이콘 팩 사용(다른 아이콘 팩으로 교체 금지)

운영/통합 규칙(고정):
- API 호출은 항상 상대경로 /api 로 한다.
- 개발 환경에서는 Vite proxy로 /api -> http://localhost:8080/api 로 전달하여 CORS를 피한다.
- access token은 메모리/Pinia에만 저장한다.
- refresh token은 HttpOnly cookie이며 JS로 접근하지 않는다.

401 처리 규칙(엄격, 반드시 구현):
- API 요청이 401이면 refresh를 "딱 1회" 시도하고, 성공 시 원 요청을 "딱 1회" 재시도한다.
- refresh 실패 시 /login으로 이동한다.
- 무한 루프 방지: 재시도 플래그 필요.
- 동시성 처리: refresh 락/큐를 구현해서 동시에 여러 요청이 401이어도 refresh는 1번만 수행되게 한다.

OpenAPI 타입 연동(필수):
- 백엔드에서 export한 openapi.json을 /openapi/openapi.json에 둔다.
- openapi-typescript로 /src/api/generated.ts를 생성한다.
- 스크립트:
  - pnpm gen:api -> openapi/openapi.json -> src/api/generated.ts 생성
- API client/DTO는 가능한 한 generated 타입을 활용한다.

필수 기능(UI/UX):
- 반응형 디자인(모바일/태블릿/데스크톱)
- 디자인 방향:
  - Clean & Modern Minimalism 기본
  - Bold & Expressive Typography를 헤드라인/섹션 타이틀에 적절히 사용
- 다크모드:
  - Light / Dark / System 모드 제공
  - localStorage에 저장, System은 OS 설정을 따름
- 애니메이션/인터랙션:
  - 가벼운 hover/transition, (옵션) 섹션 진입 시 subtle 애니메이션
  - prefers-reduced-motion 존중
- SEO 기본:
  - 페이지별 title/description
  - OG/Twitter 메타(기본)
  - robots.txt, sitemap.xml(최소)
- 성능:
  - 라우트 단위 코드 스플릿
  - 의존성 최소화(무거운 UI 프레임워크 금지)
- 접근성:
  - 키보드 내비게이션, ARIA, 포커스 스타일

콘텐츠 규칙:
- 이미지/비디오는 Pixabay에서 라이선스 준수 자산만 사용
- 사용한 자산은 src/assets/credits.md에 출처 링크 기록
- docs markdown 5개 이상 포함(Quickstart 2, Auth 1, API usage 1, FAQ 1)

필수 라우트/페이지(고정):
- Public:
  - / (Home)
  - /docs (index), /docs/:slug
  - /api (API docs 안내 + swagger 링크)
  - /releases, /releases/:id
  - /announcements, /announcements/:id
  - /pricing
  - /community
  - /search (MVP: 클라이언트 검색)
- Auth:
  - /login, /register
- Console(Protected):
  - /console, /console/api-keys, /console/billing
- Admin(Protected, ROLE_ADMIN):
  - /admin/announcements, /admin/releases
- 404 fallback

Home 구성(필수):
- Hero + CTA(Quickstart / Docs / Console)
- Quickstart 카드 3개
- Popular docs 섹션(정적)
- Latest releases 섹션(API 최신 3개)
- Latest announcements 섹션(API 최신 3개)
- Pricing CTA
- Community CTA

Docs 구성(필수):
- 좌측 사이드바
- Markdown 렌더
- 우측 TOC
- 코드 하이라이트 + 복사 버튼

Admin(필수 최소):
- 공지/릴리즈 리스트 + 생성/수정/삭제(간단 폼)
- 권한 없으면 403 처리

Search(MVP):
- 1차: docs(정적), announcements, releases를 받아 클라이언트에서 필터링/검색(간단)
- 추후 서버 검색으로 교체 가능하도록 search 모듈 경계 분리

진행 방식(반드시 Phase로 진행, 실행 가능한 상태 유지):
- Phase 0: plan.md 작성/갱신 + 프로젝트 스캐폴딩 + Tailwind + 라우팅/레이아웃 + README/.env.example
- Phase 1: API client(axios) + 인증 플로우 + 401 refresh(락/큐) + 보호 라우트
- Phase 2: Home/Docs/Announcements/Releases/Pricing/Community 구현(반응형/기본 스타일 포함)
- Phase 3: Console(API keys/Billing) 구현
- Phase 4: Admin CRUD 구현
- Phase 5: Dark/Light/System 모드 + SEO 기본(robots/sitemap/meta) + a11y/성능 폴리싱
- Phase 6: credits.md 정리 + 문서/정리 + smoke 체크리스트(수동 검증 절차)

각 Phase 출력 형식(반드시 지켜라):
- 변경 파일 목록
- 핵심 파일 "전체 코드"(중요한 파일만, 누락 없이)
- 실행 명령어
- 검증 방법(URL/동작 시나리오)
- 실패 가능 포인트와 해결책
- plan.md 체크/갱신 요약(무엇을 체크했고 무엇이 남았는지)

지금 바로 시작해라.
1) requirement-forntend.md를 읽고 요구사항을 위배하지 않는 범위에서 plan.md를 작성/갱신한다.
2) Phase 0을 완료한다(스캐폴딩/문서/기본 레이아웃/라우팅/실행 확인).
3) Phase 0 완료 후, plan.md를 체크 처리하고 다음 Phase(Phase 1)로 넘어갈 준비 상태를 만들고 멈춘다(다음 지시는 내가 한다).
