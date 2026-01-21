---
type: class
domain: billing
layer: service
package: com.devwebsite.backend.billing.service
source: backend/src/main/java/com/devwebsite/backend/billing/service/BillingService.java
status: active
tags:
  - "#domain/billing"
  - "#layer/service"
---

# 요약
- 요금제와 구독 데이터를 조회/변경한다.
- 구독이 없으면 기본 무료 플랜으로 생성한다.

# 역할 / 비목표
- 역할: 요금제 조회, 구독 생성/변경 로직 처리.
- 비목표: 실제 결제 처리 로직은 포함하지 않는다.

# 의존성
- PlanRepository
- SubscriptionRepository
- ResourceNotFoundException

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| getAllPlans | 모든 요금제 조회 | 입력: 없음 / 출력: List<PlanResponse> | 없음 | 코드로 확인 불가 | 읽기(readOnly) |
| getPlan | 요금제 단건 조회 | 입력: id / 출력: PlanResponse | 없음 | ResourceNotFoundException | 읽기(readOnly) |
| getSubscription | 사용자 구독 조회/생성 | 입력: User / 출력: SubscriptionResponse | 구독 생성 가능 | ResourceNotFoundException(기본 플랜 미존재) | 쓰기 |
| changePlan | 구독 플랜 변경 | 입력: User, ChangePlanRequest / 출력: SubscriptionResponse | 구독 생성/변경 | ResourceNotFoundException | 쓰기 |

# 동작 정리
- 기본 플랜 이름은 `free`이며 없으면 예외를 발생시킨다.
- 구독이 없으면 새 구독을 생성하고 저장한다.
- 구독이 있으면 `subscription.changePlan(newPlan)`으로 변경한다.

# 관련 문서
- [[billing/controller/SubscriptionController]]
- [[billing/repository/SubscriptionRepository]]

