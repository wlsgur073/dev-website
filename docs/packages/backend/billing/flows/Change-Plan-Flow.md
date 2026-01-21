# 구독 플랜 변경 플로우

## 사전조건
- ChangePlanRequest가 전달된다.
- User 파라미터가 주입된다(@AuthenticationPrincipal).

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant SubscriptionController
  participant BillingService
  participant PlanRepository
  participant SubscriptionRepository
  participant Subscription
  participant SubscriptionResponse
  SubscriptionController->>BillingService: changePlan(user, request)
  BillingService->>PlanRepository: findById(request.planId)
  PlanRepository-->>BillingService: Optional<Plan>
  alt 플랜 없음
    BillingService-->>SubscriptionController: ResourceNotFoundException
  else 플랜 존재
    BillingService->>SubscriptionRepository: findByUserWithPlan(user)
    SubscriptionRepository-->>BillingService: Optional<Subscription>
    alt 구독 없음
      BillingService->>SubscriptionRepository: save(Subscription)
    else 구독 존재
      BillingService->>Subscription: changePlan(newPlan)
    end
    BillingService->>SubscriptionResponse: from(subscription)
    BillingService-->>SubscriptionController: SubscriptionResponse
  end
```

## 메모
- 실패 케이스: ResourceNotFoundException("Plan not found")
- 관측: 코드로 확인 불가
- 트랜잭션: BillingService.changePlan은 쓰기 트랜잭션
