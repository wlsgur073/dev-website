---
type: class
domain: auth
layer: service
package: com.devwebsite.backend.auth.service
source: backend/src/main/java/com/devwebsite/backend/auth/service/AuthService.java
status: active
tags:
  - "#domain/auth"
  - "#layer/service"
---

# 요약
- 회원가입, 로그인, 리프레시 토큰 회전, 로그아웃 로직을 제공한다.
- JWT 발급과 사용자 조회를 담당한다.

# 역할 / 비목표
- 역할: 인증/토큰 발급 및 갱신, 사용자 조회.
- 비목표: HTTP 쿠키 설정은 처리하지 않는다.

# 의존성
- UserRepository
- RefreshTokenRepository
- PasswordEncoder
- JwtTokenProvider
- AuthenticationManager
- ResourceNotFoundException

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| register | 사용자 등록 및 액세스 토큰 발급 | 입력: RegisterRequest / 출력: AuthResponse | 사용자 저장 | IllegalArgumentException(이메일 중복) | 쓰기 |
| login | 로그인 및 액세스 토큰 발급 | 입력: LoginRequest / 출력: AuthResponse | 없음 | BadCredentialsException | 쓰기 |
| createRefreshToken | 리프레시 토큰 생성 및 저장 | 입력: User / 출력: String | 토큰 저장 | RuntimeException(SHA-256 미지원) | 쓰기 |
| refreshWithRotation | 리프레시 토큰 회전 | 입력: rawRefreshToken / 출력: TokenPair | 새 토큰 저장, 기존 토큰 revoke | ResourceNotFoundException, RuntimeException(SHA-256 미지원) | 쓰기 |
| logout | 리프레시 토큰 revoke | 입력: rawRefreshToken / 출력: void | 토큰 revoke | RuntimeException(SHA-256 미지원) | 쓰기 |
| logoutAll | 사용자 토큰 일괄 revoke | 입력: User / 출력: void | 토큰 revoke | 코드로 확인 불가 | 쓰기 |
| getUserByEmail | 이메일로 사용자 조회 | 입력: email / 출력: User | 없음 | ResourceNotFoundException | 코드로 확인 불가 |

# 동작 정리
- register는 이메일 중복을 검사한 뒤 비밀번호를 인코딩해 저장한다.
- login은 `AuthenticationManager`로 인증 후 액세스 토큰을 생성한다.
- 리프레시 토큰은 원문을 SHA-256 해시로 저장한다.
- 회전 시 새 토큰을 저장하고 기존 토큰을 `revoke`로 연결한다.

# 관련 문서
- [[auth/controller/AuthController]]
- [[auth/repository/RefreshTokenRepository]]
- [[auth/jwt/JwtTokenProvider]]

