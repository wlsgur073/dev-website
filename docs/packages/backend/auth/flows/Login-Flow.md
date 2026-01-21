# 로그인 플로우

## 사전조건
- LoginRequest가 전달된다.

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant AuthController
  participant AuthService
  participant AuthenticationManager
  participant JwtTokenProvider
  participant UserRepository
  participant RefreshTokenRepository
  participant AuthResponse
  AuthController->>AuthService: login(LoginRequest)
  AuthService->>AuthenticationManager: authenticate(UsernamePasswordAuthenticationToken)
  AuthenticationManager-->>AuthService: Authentication
  AuthService->>JwtTokenProvider: generateAccessToken(authentication)
  AuthService->>AuthResponse: of(accessToken, userInfo)
  AuthService-->>AuthController: AuthResponse
  AuthController->>AuthService: getUserByEmail(email)
  AuthService->>UserRepository: findByEmail(email)
  UserRepository-->>AuthService: User
  AuthController->>AuthService: createRefreshToken(user)
  AuthService->>JwtTokenProvider: generateRefreshToken()
  AuthService->>RefreshTokenRepository: save(RefreshToken)
  AuthService-->>AuthController: refreshToken
  Note right of AuthController: setRefreshTokenCookie(response, refreshToken)
```

## 메모
- 실패 케이스: BadCredentialsException("Invalid email or password"), ResourceNotFoundException("User not found")
- 관측: 코드로 확인 불가
- 트랜잭션: AuthService.login, AuthService.createRefreshToken는 쓰기 트랜잭션
