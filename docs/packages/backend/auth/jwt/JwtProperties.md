---
type: class
domain: auth
layer: jwt
package: com.devwebsite.backend.auth.jwt
source: backend/src/main/java/com/devwebsite/backend/auth/jwt/JwtProperties.java
status: active
tags:
  - "#domain/auth"
  - "#layer/jwt"
---

# 요약
- JWT 설정 값을 보관하는 설정 프로퍼티 레코드다.

# 역할 / 비목표
- 역할: 시크릿과 만료 시간 값을 제공한다.
- 비목표: 값 검증 로직을 포함하지 않는다.

# 의존성
- 없음

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| secret() | 시크릿 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| accessExpirationMs() | 액세스 만료 시간 반환 | 입력: 없음 / 출력: long | 없음 | 없음 | 해당 없음 |
| refreshExpirationMs() | 리프레시 만료 시간 반환 | 입력: 없음 / 출력: long | 없음 | 없음 | 해당 없음 |

# 동작 정리
- `@ConfigurationProperties(prefix = "jwt")`로 바인딩된다.

# 관련 문서
- [[auth/jwt/JwtTokenProvider]]

