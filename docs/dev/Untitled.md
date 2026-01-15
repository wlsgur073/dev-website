## 구현 내용 요약

### 문서화

- **[credits.md](vscode-webview://04p6ibh40pufn65lghffphgnmjnphjd0sj0qh8772lrloo792h2l/src/assets/credits.md)**: Heroicons, Vue, Tailwind, markdown-it, Shiki 등 모든 라이브러리 라이선스 정보 기록
- **[README.md](vscode-webview://04p6ibh40pufn65lghffphgnmjnphjd0sj0qh8772lrloo792h2l/README.md)**: 프로젝트 구조, 주요 기능(인증, 다크모드, SEO, 접근성), 라우트 구조 등 상세 문서화
- **[CLAUDE.md](vscode-webview://04p6ibh40pufn65lghffphgnmjnphjd0sj0qh8772lrloo792h2l/CLAUDE.md)**: composables, 컴포넌트 패턴, Toast 사용법 등 개발 가이드 추가

### Smoke Test 체크리스트

- ✅ 홈페이지 로드 및 모든 섹션
- ✅ Docs 페이지 Markdown 렌더링
- ✅ 로그인/로그아웃 플로우
- ✅ 401 refresh 동작
- ✅ Console 페이지 접근 (인증)
- ✅ Admin 페이지 접근 (권한)
- ✅ 다크모드 전환
- ✅ 반응형 레이아웃
- ✅ 검색 기능
- ✅ 404 페이지