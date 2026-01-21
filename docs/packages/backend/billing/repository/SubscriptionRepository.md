---
type: class
domain: billing
layer: repository
package: com.devwebsite.backend.billing.repository
source: backend/src/main/java/com/devwebsite/backend/billing/repository/SubscriptionRepository.java
status: active
tags:
  - "#domain/billing"
  - "#layer/repository"
---

# 요약
- 구독 조회를 위한 JPA 리포지토리다.
- 플랜 fetch 조인 쿼리를 제공한다.

# 역할 / 비목표
- 역할: 구독 조회용 커스텀 쿼리를 제공한다.
- 비목표: 비즈니스 규칙을 처리하지 않는다.

# 의존성
- Subscription
- User

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| findByUser | 사용자 구독 조회 | 입력: User / 출력: Optional<Subscription> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| findByUserWithPlan | 사용자 구독+플랜 조회 | 입력: User / 출력: Optional<Subscription> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| existsByUser | 사용자 구독 존재 여부 | 입력: User / 출력: boolean | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |

# 동작 정리
- findByUserWithPlan은 `JOIN FETCH s.plan`을 사용한다.

# 관련 문서
- [[billing/entity/Subscription]]
- [[billing/service/BillingService]]

