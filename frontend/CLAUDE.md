# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Vue 3 + TypeScript + Vite 기반 프론트엔드 애플리케이션. 백엔드(Spring/DB) 코드는 생성하지 않는다.

## Commands

```bash
pnpm install      # 의존성 설치 (npm install도 가능)
pnpm dev          # 개발 서버 (http://localhost:5173)
pnpm build        # 프로덕션 빌드
pnpm preview      # 빌드 미리보기
pnpm gen:api      # OpenAPI -> TypeScript 타입 생성
```

## Tech Stack

- Vue 3 + TypeScript + Vite
- Vue Router, Pinia
- Tailwind CSS (darkMode: 'class')
- Axios (API client)
- markdown-it + Shiki (Markdown 렌더링 + 코드 하이라이트)
- Heroicons (아이콘 - 다른 아이콘 팩 사용 금지)

## Architecture

### 폴더 구조
```
src/
├── api/           # API 클라이언트 (axios 기반)
├── assets/        # 정적 자산 + credits.md
├── components/    # 공통 컴포넌트
├── composables/   # Vue Composables (useToast, useSeoMeta)
├── content/docs/  # 문서 Markdown 파일
├── layouts/       # 레이아웃 (Default, Console, Admin, Auth, Docs)
├── pages/         # 페이지 컴포넌트
├── router/        # Vue Router 설정
├── stores/        # Pinia 스토어 (auth, theme)
├── styles/        # Tailwind 기반 전역 스타일
└── utils/         # 유틸리티 함수 (markdown.ts)
```

### 인증 규칙
- access token: 메모리(Pinia)에만 저장
- refresh token: HttpOnly cookie (JS 접근 불가)
- 401 처리: refresh 1회 시도 → 성공 시 원 요청 재시도 → 실패 시 /login 리다이렉트
- 동시성: refresh 락/큐로 중복 refresh 방지

### API 규칙
- 모든 API 호출은 `/api` 상대경로 사용
- 개발환경: Vite proxy → `http://localhost:8080/api`
- OpenAPI 타입: `pnpm gen:api`로 생성

### 다크모드
- `src/stores/theme.ts`: light/dark/system 모드 관리
- `index.html`: FOUC 방지 스크립트 포함
- Tailwind `dark:` 클래스로 스타일링

### SEO & 접근성
- `src/composables/useSeoMeta.ts`: 페이지별 메타 태그
- `public/robots.txt`, `public/sitemap.xml`: SEO 파일
- 모든 레이아웃에 Skip Link 적용
- `prefers-reduced-motion` 존중

## Key Files

- `plan.md`: 구현 계획 및 Phase별 체크리스트 (Phase 0-6)
- `requirement-forntend_v0.3.md`: 요구사항 원본 (수정 금지)
- `src/router/index.ts`: 모든 라우트 정의 + Navigation Guard
- `src/stores/auth.ts`: 인증 상태 관리
- `src/stores/theme.ts`: 테마 상태 관리
- `src/api/http.ts`: axios 인스턴스 + 401 인터셉터
- `src/composables/useToast.ts`: 토스트 알림 시스템
- `src/composables/useSeoMeta.ts`: SEO 메타 태그 관리

## Component Patterns

### 레이아웃
- `DefaultLayout`: 일반 public 페이지
- `DocsLayout`: 문서 페이지 (사이드바 + TOC)
- `ConsoleLayout`: 인증된 사용자 콘솔
- `AdminLayout`: 관리자 전용 (ROLE_ADMIN)
- `AuthLayout`: 로그인/회원가입

### Toast 사용
```typescript
import { useToast } from '@/composables/useToast'
const toast = useToast()
toast.success('성공 메시지')
toast.error('에러 메시지')
```

## MCP Servers

`.vscode/mcp.json`에 설정됨:
- Context7 (@upstash/context7-mcp)
- Playwright (@playwright/mcp)
- GitHub MCP Server (Docker 필요)
