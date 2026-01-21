---
type: class
domain: user
layer: dto
package: com.devwebsite.backend.user.dto
source: backend/src/main/java/com/devwebsite/backend/user/dto/UserResponse.java
status: active
tags:
  - "#domain/user"
  - "#layer/dto"
---

# 요약
- 사용자 조회 응답 DTO다.

# 역할 / 비목표
- 역할: User 엔티티를 응답 형태로 변환한다.
- 비목표: 사용자 관리 로직을 수행하지 않는다.

# 의존성
- User

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| from | 엔티티를 응답 DTO로 변환 | 입력: User / 출력: UserResponse | 없음 | 코드로 확인 불가 | 해당 없음 |
| id() | 식별자 반환 | 입력: 없음 / 출력: Long | 없음 | 없음 | 해당 없음 |
| email() | 이메일 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| nickname() | 닉네임 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| role() | 역할 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| createdAt() | 생성 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |
| updatedAt() | 수정 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |

# 관련 문서
- [[user/entity/User]]

