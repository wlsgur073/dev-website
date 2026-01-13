# Frontend Implementation Plan

> 이 문서는 requirement-forntend_v0.3.md를 기반으로 한 구현 계획입니다.
> 각 Phase 완료 시 체크리스트를 갱신합니다.

---

## Phase 0: 프로젝트 스캐폴딩 + 기본 설정

### Goals
- Vue 3 + TypeScript + Vite 프로젝트 초기화
- Tailwind CSS 설정
- Vue Router + Pinia 설정
- 기본 레이아웃 및 라우팅 구조 구성
- README.md, .env.example 작성

### Checklist
- [x] pnpm init + Vue 3/Vite 프로젝트 생성
- [x] TypeScript 설정
- [x] Tailwind CSS + PostCSS 설정
- [x] Vue Router 설치 및 기본 라우트 정의 (모든 필수 라우트 스텁)
- [x] Pinia 설치 및 스토어 구조 생성
- [x] 기본 레이아웃 컴포넌트 (DefaultLayout, ConsoleLayout, AdminLayout)
- [x] 폴더 구조 생성 (요구사항대로)
- [x] Vite proxy 설정 (/api -> localhost:8080)
- [x] .env.example 작성
- [x] README.md 작성 (실행 방법, 스크립트 설명)
- [x] openapi 폴더 및 placeholder 생성
- [x] gen:api 스크립트 추가 (openapi-typescript)
- [x] Heroicons 설치

### Definition of Done (DoD)
- [x] `pnpm dev` 실행 시 기본 페이지가 표시됨
- [x] 모든 라우트가 스텁 페이지로 연결됨
- [x] Tailwind 스타일이 적용됨

### Verification
1. ✅ `npm install && npm run dev` 실행
2. ✅ http://localhost:5173 접속 → Home 페이지 표시
3. ✅ /docs, /login, /console, /admin/* 등 라우트 접근 확인
4. ✅ Tailwind 클래스 적용 확인

### Risks & Mitigations
| Risk | Mitigation |
|------|------------|
| pnpm 미설치 | README에 pnpm/npm 설치 안내 포함 |
| Vite proxy 미작동 | vite.config.ts 설정 검증 완료 |

### Issues & Solutions
- pnpm CLI가 Windows 환경에서 작동하지 않아 npm으로 대체 사용

---

## Phase 1: API Client + 인증 플로우

### Goals
- axios 기반 API client 구현
- 401 refresh 로직 (락/큐, 재시도 플래그)
- 인증 스토어 (Pinia)
- 보호 라우트 (Navigation Guard)

### Checklist
- [ ] axios 설치
- [ ] src/api/http.ts: axios 인스턴스 + interceptor 구현
- [ ] 401 처리: refresh 락/큐 구현 (동시성 처리)
- [ ] 재시도 플래그로 무한 루프 방지
- [ ] src/api/auth.ts: login, logout, register, refresh API
- [ ] src/stores/auth.ts: accessToken 메모리 저장, 사용자 정보
- [ ] Vue Router navigation guard: 보호 라우트 체크
- [ ] ROLE_ADMIN 권한 체크 (admin 라우트용)
- [ ] 로그인/로그아웃 플로우 테스트 가능 상태

### Definition of Done (DoD)
- 401 발생 시 자동 refresh 후 원 요청 재시도
- refresh 실패 시 /login 리다이렉트
- 동시 요청 시 refresh 1회만 수행
- 보호 라우트 미인증 시 /login 리다이렉트

### Verification
1. 로그인 → accessToken 메모리 저장 확인
2. 만료된 토큰으로 API 호출 → refresh → 재시도 확인
3. refresh 실패 → /login 이동 확인
4. /console 미인증 접근 → /login 리다이렉트 확인
5. /admin/* 비관리자 접근 → 403 처리 확인

### Risks & Mitigations
| Risk | Mitigation |
|------|------------|
| refresh 무한 루프 | _retry 플래그 + 재시도 횟수 제한 |
| 동시 요청 race condition | refresh 락 + 대기 큐 패턴 |

### Issues & Solutions
(Phase 완료 후 기록)

---

## Phase 2: Public 페이지 구현

### Goals
- Home 페이지 (Hero, Quickstart, Popular docs, Latest releases/announcements, Pricing CTA, Community CTA)
- Docs 페이지 (사이드바, Markdown 렌더, TOC, 코드 하이라이트)
- Announcements, Releases 리스트/상세
- Pricing, Community, API docs 페이지
- Search (클라이언트 사이드 MVP)
- 반응형 디자인

### Checklist
- [ ] markdown-it 설치 및 설정
- [ ] shiki 또는 highlight.js 설치 (코드 하이라이트)
- [ ] src/content/docs/*.md 파일 5개 이상 작성
- [ ] Home 페이지 구현 (모든 섹션)
- [ ] Docs 인덱스 + /docs/:slug 페이지
- [ ] Docs 사이드바 컴포넌트
- [ ] Docs TOC 컴포넌트
- [ ] 코드 블록 복사 버튼
- [ ] Announcements 리스트/상세 페이지
- [ ] Releases 리스트/상세 페이지
- [ ] Pricing 페이지
- [ ] Community 페이지
- [ ] API docs 안내 페이지 (Swagger 링크)
- [ ] Search 페이지 (클라이언트 검색)
- [ ] 404 페이지
- [ ] 반응형 디자인 (모바일/태블릿/데스크톱)
- [ ] src/api/announcements.ts, releases.ts 구현

### Definition of Done (DoD)
- 모든 public 페이지 접근 가능
- Markdown 렌더링 + 코드 하이라이트 동작
- 검색 기능 동작 (클라이언트)
- 반응형 레이아웃 확인

### Verification
1. / → Home 페이지 모든 섹션 확인
2. /docs → 사이드바 + 첫 문서 표시
3. /docs/quickstart-1 → Markdown 렌더 + TOC + 코드 하이라이트
4. /announcements, /releases → 리스트 표시
5. /search → 검색어 입력 시 필터링
6. 모바일 뷰포트에서 레이아웃 확인

### Risks & Mitigations
| Risk | Mitigation |
|------|------------|
| Markdown XSS | markdown-it sanitize 옵션 |
| 코드 하이라이트 번들 크기 | 필요 언어만 import |

### Issues & Solutions
(Phase 완료 후 기록)

---

## Phase 3: Console (Protected) 구현

### Goals
- /console 대시보드
- /console/api-keys: API 키 관리
- /console/billing: 구독/결제 정보

### Checklist
- [ ] Console 레이아웃 완성
- [ ] /console 대시보드 페이지
- [ ] /console/api-keys: 키 리스트, 생성, 삭제
- [ ] /console/billing: 구독 정보, 플랜 표시
- [ ] src/api/apikeys.ts 구현
- [ ] src/api/subscription.ts 구현
- [ ] src/api/plans.ts 구현
- [ ] 로딩/에러 상태 처리

### Definition of Done (DoD)
- 인증된 사용자만 Console 접근 가능
- API 키 CRUD 동작
- Billing 정보 표시

### Verification
1. 로그인 후 /console 접근 → 대시보드 표시
2. /console/api-keys → 키 목록, 생성, 삭제 테스트
3. /console/billing → 구독 정보 표시
4. 미인증 시 /login 리다이렉트

### Risks & Mitigations
| Risk | Mitigation |
|------|------------|
| API 키 노출 | 생성 시 1회만 표시, 이후 마스킹 |

### Issues & Solutions
(Phase 완료 후 기록)

---

## Phase 4: Admin CRUD 구현

### Goals
- /admin/announcements: 공지사항 CRUD
- /admin/releases: 릴리즈 CRUD
- ROLE_ADMIN 권한 체크

### Checklist
- [ ] Admin 레이아웃 완성
- [ ] /admin/announcements: 리스트, 생성, 수정, 삭제 폼
- [ ] /admin/releases: 리스트, 생성, 수정, 삭제 폼
- [ ] 권한 체크 (비관리자 → 403)
- [ ] 폼 유효성 검사
- [ ] 성공/실패 토스트 알림

### Definition of Done (DoD)
- ROLE_ADMIN 사용자만 Admin 접근 가능
- Announcements/Releases CRUD 완전 동작
- 비관리자 403 페이지 표시

### Verification
1. ROLE_ADMIN 계정으로 /admin/* 접근
2. 공지사항 생성/수정/삭제 테스트
3. 릴리즈 생성/수정/삭제 테스트
4. 일반 사용자로 /admin/* 접근 → 403

### Risks & Mitigations
| Risk | Mitigation |
|------|------------|
| 권한 우회 | 프론트 가드 + 백엔드 검증 (백엔드 책임) |

### Issues & Solutions
(Phase 완료 후 기록)

---

## Phase 5: 다크모드 + SEO + 접근성/성능

### Goals
- Light/Dark/System 모드
- SEO 기본 (meta, OG, robots.txt, sitemap.xml)
- 접근성 개선 (키보드, ARIA, 포커스)
- 성능 최적화 (코드 스플릿)

### Checklist
- [ ] 다크모드 토글 컴포넌트
- [ ] localStorage 저장 (light/dark/system)
- [ ] System 모드: OS 설정 따름 (prefers-color-scheme)
- [ ] Tailwind dark: 클래스 적용
- [ ] 페이지별 title/description (vue-router meta 또는 @vueuse/head)
- [ ] OG/Twitter 메타 태그
- [ ] public/robots.txt 생성
- [ ] public/sitemap.xml 생성 (정적)
- [ ] prefers-reduced-motion 존중
- [ ] 키보드 내비게이션 확인
- [ ] ARIA 속성 추가 (필요한 곳)
- [ ] 포커스 스타일 (outline)
- [ ] 라우트 단위 코드 스플릿 확인 (defineAsyncComponent / lazy import)

### Definition of Done (DoD)
- 다크모드 전환 동작, 새로고침 후 유지
- 모든 페이지에 적절한 title/meta
- robots.txt, sitemap.xml 존재
- 키보드만으로 주요 기능 사용 가능

### Verification
1. 다크모드 토글 → 테마 변경 확인
2. System 선택 → OS 설정 반영 확인
3. 새로고침 → 테마 유지 확인
4. 각 페이지 title 확인
5. /robots.txt, /sitemap.xml 접근
6. Tab 키로 네비게이션 테스트

### Risks & Mitigations
| Risk | Mitigation |
|------|------------|
| FOUC (Flash of Unstyled Content) | HTML head에 초기 테마 스크립트 |
| 접근성 누락 | axe DevTools로 검사 |

### Issues & Solutions
(Phase 완료 후 기록)

---

## Phase 6: 문서화 + 마무리

### Goals
- credits.md 정리 (사용 자산 출처)
- 문서 최종 정리
- Smoke 테스트 체크리스트

### Checklist
- [ ] src/assets/credits.md: 모든 Pixabay 자산 출처 기록
- [ ] README.md 최종 업데이트
- [ ] CLAUDE.md 업데이트 (아키텍처, 명령어)
- [ ] Smoke 테스트 수행 (아래 체크리스트)

### Smoke Test Checklist
- [ ] 홈페이지 로드 및 모든 섹션 확인
- [ ] Docs 페이지 Markdown 렌더링 확인
- [ ] 로그인/로그아웃 플로우
- [ ] 401 refresh 동작
- [ ] Console 페이지 접근 (인증)
- [ ] Admin 페이지 접근 (권한)
- [ ] 다크모드 전환
- [ ] 반응형 레이아웃 (모바일/데스크톱)
- [ ] 검색 기능
- [ ] 404 페이지

### Definition of Done (DoD)
- 모든 Smoke 테스트 통과
- 문서 완비
- 빌드 성공 (`pnpm build`)

### Verification
1. `pnpm build` 성공
2. `pnpm preview`로 프로덕션 빌드 확인
3. Smoke 체크리스트 수동 검증

### Risks & Mitigations
| Risk | Mitigation |
|------|------------|
| 빌드 실패 | TypeScript strict 오류 사전 해결 |

### Issues & Solutions
(Phase 완료 후 기록)

---

## Progress Summary

| Phase | Status | Completed Date |
|-------|--------|----------------|
| Phase 0 | ✅ Completed | 2026-01-13 |
| Phase 1 | ⏳ Pending | - |
| Phase 2 | ⏳ Pending | - |
| Phase 3 | ⏳ Pending | - |
| Phase 4 | ⏳ Pending | - |
| Phase 5 | ⏳ Pending | - |
| Phase 6 | ⏳ Pending | - |
