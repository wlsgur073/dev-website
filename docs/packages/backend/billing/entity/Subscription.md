---
type: class
domain: billing
layer: entity
package: com.devwebsite.backend.billing.entity
source: backend/src/main/java/com/devwebsite/backend/billing/entity/Subscription.java
status: active
tags:
  - "#domain/billing"
  - "#layer/entity"
---

# 요약
- 사용자 구독 정보를 표현하는 엔티티다.
- 플랜 변경 및 상태 전이를 제공한다.

# 역할 / 비목표
- 역할: 구독 상태와 플랜 변경 로직을 제공한다.
- 비목표: 결제 처리 로직을 수행하지 않는다.

# 의존성
- User
- Plan
- LocalDateTime

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| changePlan | 구독 플랜 변경 | 입력: newPlan / 출력: void | plan, startedAt 변경 | 없음 | 코드로 확인 불가 |
| cancel | 구독 취소 상태로 변경 | 입력: 없음 / 출력: void | status 변경 | 없음 | 코드로 확인 불가 |
| activate | 구독 활성 상태로 변경 | 입력: 없음 / 출력: void | status 변경 | 없음 | 코드로 확인 불가 |

# 동작 정리
- Builder는 status가 null이면 ACTIVE로 설정하고 startedAt을 현재 시각으로 설정한다.
- `@PrePersist`에서 `createdAt`, `updatedAt`을 현재 시각으로 설정한다.
- `SubscriptionStatus`는 ACTIVE, CANCELLED, EXPIRED, PENDING 값을 가진다.

# 관련 문서
- [[billing/service/BillingService]]

