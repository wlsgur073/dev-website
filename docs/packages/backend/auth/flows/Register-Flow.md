# 회원가입 플로우

## 사전조건
- RegisterRequest가 전달된다.

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant AuthController
  participant AuthService
  participant UserRepository
  participant PasswordEncoder
  participant JwtTokenProvider
  participant RefreshTokenRepository
  participant AuthResponse
  AuthController->>AuthService: register(RegisterRequest)
  AuthService->>UserRepository: existsByEmail(email)
  alt 이메일 존재
    AuthService-->>AuthController: IllegalArgumentException
  else 이메일 없음
    AuthService->>PasswordEncoder: encode(password)
    AuthService->>UserRepository: save(User)
    AuthService->>JwtTokenProvider: generateAccessToken(user.email)
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
  end
```

## 메모
- 실패 케이스: IllegalArgumentException("Email already exists"), ResourceNotFoundException("User not found")
- 관측: 코드로 확인 불가
- 트랜잭션: AuthService.register, AuthService.createRefreshToken는 쓰기 트랜잭션
