---
type: class
domain: auth
layer: entity
package: com.devwebsite.backend.auth.entity
source: backend/src/main/java/com/devwebsite/backend/auth/entity/RefreshToken.java
status: active
tags:
  - "#domain/auth"
  - "#layer/entity"
---

# 요약
- 리프레시 토큰 정보를 표현하는 엔티티다.
- revoke 및 유효성 검증 로직을 포함한다.

# 역할 / 비목표
- 역할: 토큰 상태(revoked, replacedBy)와 유효성 판단을 제공한다.
- 비목표: 토큰 발급/저장 로직을 처리하지 않는다.

# 의존성
- User
- LocalDateTime

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| revoke(RefreshToken) | 토큰 revoke 및 교체 토큰 연결 | 입력: newToken / 출력: void | revoked, replacedBy 변경 | 없음 | 코드로 확인 불가 |
| revoke() | 토큰 revoke | 입력: 없음 / 출력: void | revoked 변경 | 없음 | 코드로 확인 불가 |
| isValid | 토큰 유효성 판단 | 입력: 없음 / 출력: boolean | 없음 | 없음 | 코드로 확인 불가 |

# 동작 정리
- `isValid`는 revoked=false이고 expiresAt이 현재 시각 이후인지 확인한다.
- `@PrePersist`에서 `createdAt`을 현재 시각으로 설정한다.

# 관련 문서
- [[auth/service/AuthService]]

