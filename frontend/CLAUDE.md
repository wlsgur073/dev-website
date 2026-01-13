# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Vue 3 + TypeScript + Vite 기반 프론트엔드 애플리케이션. 백엔드(Spring/DB) 코드는 생성하지 않는다.

## Commands

```bash
pnpm install      # 의존성 설치
pnpm dev          # 개발 서버 (http://localhost:5173)
pnpm build        # 프로덕션 빌드
pnpm preview      # 빌드 미리보기
pnpm gen:api      # OpenAPI -> TypeScript 타입 생성
```

## Tech Stack

- Vue 3 + TypeScript + Vite
- Vue Router, Pinia
- Tailwind CSS
- Axios (API client)
- markdown-it (Markdown 렌더링)
- Heroicons (아이콘 - 다른 아이콘 팩 사용 금지)

## Architecture

### 폴더 구조
```
src/
├── api/           # API 클라이언트 (axios 기반)
├── assets/        # 정적 자산 + credits.md
├── components/    # 공통 컴포넌트
├── content/docs/  # 문서 Markdown 파일
├── layouts/       # 레이아웃 (Default, Console, Admin, Auth, Docs)
├── pages/         # 페이지 컴포넌트
├── router/        # Vue Router 설정
├── stores/        # Pinia 스토어 (auth, theme)
└── styles/        # Tailwind 기반 전역 스타일
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

## Key Files

- `plan.md`: 구현 계획 및 Phase별 체크리스트
- `requirement-forntend_v0.3.md`: 요구사항 원본 (수정 금지)
- `src/router/index.ts`: 모든 라우트 정의
- `src/stores/auth.ts`: 인증 상태 관리
- `src/api/http.ts`: axios 인스턴스 + 401 인터셉터

## MCP Servers

`.vscode/mcp.json`에 설정됨:
- Context7 (@upstash/context7-mcp)
- Playwright (@playwright/mcp)
- GitHub MCP Server (Docker 필요)
