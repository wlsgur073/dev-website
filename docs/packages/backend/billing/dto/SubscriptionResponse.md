---
type: class
domain: billing
layer: dto
package: com.devwebsite.backend.billing.dto
source: backend/src/main/java/com/devwebsite/backend/billing/dto/SubscriptionResponse.java
status: active
tags:
  - "#domain/billing"
  - "#layer/dto"
---

# 요약
- 구독 조회 응답 DTO다.
- 플랜 정보를 포함한다.

# 역할 / 비목표
- 역할: Subscription 엔티티를 응답 형태로 변환한다.
- 비목표: 구독 변경 로직을 수행하지 않는다.

# 의존성
- Subscription
- PlanResponse

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| from | 엔티티를 응답 DTO로 변환 | 입력: Subscription / 출력: SubscriptionResponse | 없음 | 코드로 확인 불가 | 해당 없음 |
| id() | 식별자 반환 | 입력: 없음 / 출력: Long | 없음 | 없음 | 해당 없음 |
| plan() | 플랜 정보 반환 | 입력: 없음 / 출력: PlanResponse | 없음 | 없음 | 해당 없음 |
| status() | 구독 상태 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| startedAt() | 시작 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |
| expiresAt() | 만료 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |
| createdAt() | 생성 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |
| updatedAt() | 수정 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |

# 동작 정리
- status는 `subscription.getStatus().name()`을 사용한다.

# 관련 문서
- [[billing/entity/Subscription]]

