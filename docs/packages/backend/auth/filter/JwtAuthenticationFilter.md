---
type: class
domain: auth
layer: filter
package: com.devwebsite.backend.auth.filter
source: backend/src/main/java/com/devwebsite/backend/auth/filter/JwtAuthenticationFilter.java
status: active
tags:
  - "#domain/auth"
  - "#layer/filter"
---

# 요약
- `Authorization: Bearer` 헤더 기반 JWT 인증 필터다.
- 유효한 토큰이면 SecurityContext에 인증 정보를 설정한다.

# 역할 / 비목표
- 역할: 요청 헤더에서 JWT를 추출하고 인증 컨텍스트를 설정한다.
- 비목표: 토큰 발급이나 사용자 생성 로직을 처리하지 않는다.

# 의존성
- JwtTokenProvider
- UserDetailsService

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| 없음 | public 메서드 없음 | - | - | - | - |

# 동작 정리
- Authorization 헤더에서 Bearer 토큰을 추출한다.
- 토큰이 유효하면 사용자 정보를 로드해 SecurityContext에 설정한다.
- 예외는 로깅 후 무시하고 필터 체인을 계속 진행한다.

# 관련 문서
- [[auth/jwt/JwtTokenProvider]]

