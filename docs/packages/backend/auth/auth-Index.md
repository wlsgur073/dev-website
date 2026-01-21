# auth 도메인 문서

## 탐색 가이드
- 이 문서는 도메인 하위 문서의 진입점이다.
- 모든 문서는 Obsidian 위키링크로 연결한다.
- 그래프/직접 링크를 활용해 흐름을 탐색한다.

## 문서 목차
### controller
- [[controller/AuthController]]

### service
- [[service/AuthService]]
- [[service/CustomUserDetailsService]]

### repository
- [[repository/RefreshTokenRepository]]

### dto
- [[dto/AuthResponse]]
- [[dto/LoginRequest]]
- [[dto/RefreshResponse]]
- [[dto/RegisterRequest]]

### entity
- [[entity/RefreshToken]]

### filter
- [[filter/CsrfProtectionFilter]]
- [[filter/JwtAuthenticationFilter]]

### jwt
- [[jwt/JwtProperties]]
- [[jwt/JwtTokenProvider]]

### flows
- [[flows/Authorization-Filter-Flow]]
- [[flows/Login-Flow]]
- [[flows/Logout-Flow]]
- [[flows/Refresh-Token-Flow]]
- [[flows/Register-Flow]]
