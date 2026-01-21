---
type: class
domain: auth
layer: jwt
package: com.devwebsite.backend.auth.jwt
source: backend/src/main/java/com/devwebsite/backend/auth/jwt/JwtTokenProvider.java
status: active
tags:
  - "#domain/auth"
  - "#layer/jwt"
---

# 요약
- JWT 액세스/리프레시 토큰 생성과 검증을 담당한다.
- 토큰에서 이메일(Subject)을 추출한다.

# 역할 / 비목표
- 역할: JWT 생성, 파싱, 유효성 검증.
- 비목표: 사용자 조회나 권한 부여 로직을 직접 처리하지 않는다.

# 의존성
- JwtProperties

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| generateAccessToken(Authentication) | 인증 객체로 액세스 토큰 생성 | 입력: Authentication / 출력: String | 없음 | 코드로 확인 불가 | 해당 없음 |
| generateAccessToken(String) | 이메일로 액세스 토큰 생성 | 입력: email / 출력: String | 없음 | 코드로 확인 불가 | 해당 없음 |
| generateRefreshToken | 리프레시 토큰 생성 | 입력: 없음 / 출력: String | 없음 | 코드로 확인 불가 | 해당 없음 |
| getEmailFromToken | 토큰에서 이메일 추출 | 입력: token / 출력: String | 없음 | 코드로 확인 불가 | 해당 없음 |
| validateToken | 토큰 검증 | 입력: token / 출력: boolean | 없음 | 없음(내부 처리) | 해당 없음 |
| getRefreshExpirationMs | 리프레시 만료 시간 반환 | 입력: 없음 / 출력: long | 없음 | 코드로 확인 불가 | 해당 없음 |

# 동작 정리
- 서명 키는 `JwtProperties.secret`로부터 생성한다.
- validateToken은 예외를 로깅한 뒤 false를 반환한다.

# 관련 문서
- [[auth/jwt/JwtProperties]]

