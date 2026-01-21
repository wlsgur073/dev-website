# 구독 조회 플로우

## 사전조건
- User 파라미터가 주입된다(@AuthenticationPrincipal).

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant SubscriptionController
  participant BillingService
  participant SubscriptionRepository
  participant PlanRepository
  participant SubscriptionResponse
  SubscriptionController->>BillingService: getSubscription(user)
  BillingService->>SubscriptionRepository: findByUserWithPlan(user)
  SubscriptionRepository-->>BillingService: Optional<Subscription>
  alt 구독 존재
    BillingService->>SubscriptionResponse: from(subscription)
    BillingService-->>SubscriptionController: SubscriptionResponse
  else 구독 없음
    BillingService->>PlanRepository: findByName("free")
    PlanRepository-->>BillingService: Plan
    BillingService->>SubscriptionRepository: save(Subscription)
    BillingService->>SubscriptionResponse: from(subscription)
    BillingService-->>SubscriptionController: SubscriptionResponse
  end
```

## 메모
- 실패 케이스: ResourceNotFoundException("Default plan not found")
- 관측: 코드로 확인 불가
- 트랜잭션: BillingService.getSubscription은 쓰기 트랜잭션
