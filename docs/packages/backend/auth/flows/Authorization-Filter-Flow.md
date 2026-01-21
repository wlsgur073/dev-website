# 인증 필터 동작 플로우

## 사전조건
- Authorization 헤더가 Bearer 형식일 때 JWT 검증을 시도한다.

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant JwtAuthenticationFilter
  participant JwtTokenProvider
  participant UserDetailsService
  participant SecurityContextHolder
  opt Authorization 헤더에 Bearer 토큰 존재
    JwtAuthenticationFilter->>JwtTokenProvider: validateToken(jwt)
    alt 토큰 유효
      JwtAuthenticationFilter->>JwtTokenProvider: getEmailFromToken(jwt)
      JwtAuthenticationFilter->>UserDetailsService: loadUserByUsername(email)
      JwtAuthenticationFilter->>SecurityContextHolder: setAuthentication(authentication)
    else 토큰 무효
      Note right of JwtAuthenticationFilter: 인증 설정 없음
    end
  end
```

## 메모
- 실패 케이스: 예외 발생 시 로그 기록 후 인증 미설정
- 관측: log.error("Could not set user authentication in security context", ex)
- 트랜잭션: 코드로 확인 불가
