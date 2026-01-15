## 구현 내용 요약

### 다크모드 (이미 구현됨)
- **ThemeToggle.vue**: Light/Dark/System 3가지 모드 지원
- **stores/theme.ts**: localStorage 저장 + OS 설정 감지 (prefers-color-scheme)
- **index.html**: FOUC 방지 스크립트 포함
- **tailwind.config.js**: `darkMode: 'class'` 설정
### SEO
- **[useSeoMeta.ts](vscode-webview://04p6ibh40pufn65lghffphgnmjnphjd0sj0qh8772lrloo792h2l/src/composables/useSeoMeta.ts)**: 페이지별 동적 메타 태그 composable
- **[index.html](vscode-webview://04p6ibh40pufn65lghffphgnmjnphjd0sj0qh8772lrloo792h2l/index.html)**: 기본 OG/Twitter 메타 태그, theme-color 추가
- **[robots.txt](vscode-webview://04p6ibh40pufn65lghffphgnmjnphjd0sj0qh8772lrloo792h2l/public/robots.txt)**: admin/console 영역 차단, sitemap 경로 명시
- **[sitemap.xml](vscode-webview://04p6ibh40pufn65lghffphgnmjnphjd0sj0qh8772lrloo792h2l/public/sitemap.xml)**: 정적 페이지 URL 목록
### 접근성
- **Skip Link**: 모든 레이아웃에 "Skip to main content" 링크 추가
- **ARIA 속성**: 네비게이션 영역에 role, aria-label 추가
- **포커스 스타일**: `:focus-visible` outline 스타일
- **prefers-reduced-motion**: 애니메이션/트랜지션 비활성화
### 성능
- **코드 스플릿**: 라우터에서 모든 페이지 lazy import 확인됨