---
type: class
domain: auth
layer: controller
package: com.devwebsite.backend.auth.controller
source: backend/src/main/java/com/devwebsite/backend/auth/controller/AuthController.java
status: active
tags:
  - "#domain/auth"
  - "#layer/controller"
---

# 요약
- 회원가입/로그인/토큰 갱신/로그아웃 API를 제공한다.
- 리프레시 토큰을 쿠키로 설정/삭제한다.

# 역할 / 비목표
- 역할: 인증 요청을 서비스로 위임하고 쿠키를 관리한다.
- 비목표: 인증/암호화 로직을 직접 구현하지 않는다.

# 의존성
- AuthService
- JwtTokenProvider

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| register | 사용자 등록 | 입력: RegisterRequest, HttpServletResponse / 출력: ResponseEntity<AuthResponse> | 리프레시 토큰 쿠키 설정 | IllegalArgumentException(서비스) | 코드로 확인 불가 |
| login | 사용자 로그인 | 입력: LoginRequest, HttpServletResponse / 출력: ResponseEntity<AuthResponse> | 리프레시 토큰 쿠키 설정 | BadCredentialsException(서비스) | 코드로 확인 불가 |
| refresh | 액세스 토큰 갱신 | 입력: HttpServletRequest, HttpServletResponse / 출력: ResponseEntity<RefreshResponse> | 리프레시 토큰 쿠키 갱신 | ResourceNotFoundException(서비스) | 코드로 확인 불가 |
| logout | 로그아웃 및 토큰 무효화 | 입력: HttpServletRequest, HttpServletResponse / 출력: ResponseEntity<Map<String, String>> | 리프레시 토큰 쿠키 삭제 | 코드로 확인 불가 | 코드로 확인 불가 |

# 동작 정리
- 쿠키 이름은 `refreshToken`이며 HttpOnly, SameSite=Strict를 사용한다.
- 쿠키 경로는 `/api/v1/auth`로 제한한다.
- 쿠키 만료는 `jwtTokenProvider.getRefreshExpirationMs()`를 사용한다.
- `server.ssl.enabled` 값을 이용해 secure 쿠키 여부를 결정한다.
- 리프레시 쿠키가 없으면 401 응답을 반환한다.

# 관련 문서
- [[auth/service/AuthService]]
- [[auth/jwt/JwtTokenProvider]]

