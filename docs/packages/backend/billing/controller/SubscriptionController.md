---
type: class
domain: billing
layer: controller
package: com.devwebsite.backend.billing.controller
source: backend/src/main/java/com/devwebsite/backend/billing/controller/SubscriptionController.java
status: active
tags:
  - "#domain/billing"
  - "#layer/controller"
---

# 요약
- 사용자 구독 조회/변경 API를 제공한다.
- 사용량 통계는 스텁 응답을 반환한다.

# 역할 / 비목표
- 역할: 구독 조회/플랜 변경 요청을 서비스로 위임한다.
- 비목표: 결제 처리나 실제 사용량 집계를 처리하지 않는다.

# 의존성
- BillingService

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| getSubscription | 사용자 구독 조회 | 입력: User / 출력: ResponseEntity<SubscriptionResponse> | 구독 생성 가능(서비스) | ResourceNotFoundException(서비스) | 코드로 확인 불가 |
| changePlan | 구독 플랜 변경 | 입력: User, ChangePlanRequest / 출력: ResponseEntity<SubscriptionResponse> | 구독 생성/변경 | ResourceNotFoundException(서비스) | 코드로 확인 불가 |
| getUsageStats | 사용량 통계 조회 | 입력: User / 출력: ResponseEntity<UsageStatsResponse> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |

# 동작 정리
- `/api/v1/subscription/usage`는 `UsageStatsResponse.stub()`을 반환한다.
- 구독 조회 시 구독이 없으면 서비스에서 무료 플랜 구독을 생성한다.

# 관련 문서
- [[billing/service/BillingService]]

