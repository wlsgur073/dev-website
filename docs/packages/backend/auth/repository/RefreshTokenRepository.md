---
type: class
domain: auth
layer: repository
package: com.devwebsite.backend.auth.repository
source: backend/src/main/java/com/devwebsite/backend/auth/repository/RefreshTokenRepository.java
status: active
tags:
  - "#domain/auth"
  - "#layer/repository"
---

# 요약
- 리프레시 토큰 조회/갱신을 위한 JPA 리포지토리다.
- 사용자 연관 fetch와 일괄 revoke 쿼리를 제공한다.

# 역할 / 비목표
- 역할: 리프레시 토큰 조회/갱신용 커스텀 쿼리를 제공한다.
- 비목표: 비즈니스 규칙을 처리하지 않는다.

# 의존성
- RefreshToken
- User

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| findByTokenHashAndRevokedFalse | 유효 리프레시 토큰 조회 | 입력: tokenHash / 출력: Optional<RefreshToken> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| revokeAllByUser | 사용자 토큰 일괄 revoke | 입력: User / 출력: void | revoked 갱신 | 코드로 확인 불가 | 코드로 확인 불가 |
| findByTokenHashWithUser | 토큰+사용자 fetch 조회 | 입력: tokenHash / 출력: Optional<RefreshToken> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |

# 동작 정리
- revokeAllByUser는 `@Modifying` 업데이트 쿼리를 사용한다.
- findByTokenHashWithUser는 `JOIN FETCH rt.user`를 사용한다.

# 관련 문서
- [[auth/entity/RefreshToken]]
- [[auth/service/AuthService]]

