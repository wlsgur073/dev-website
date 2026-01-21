---
type: class
domain: billing
layer: controller
package: com.devwebsite.backend.billing.controller
source: backend/src/main/java/com/devwebsite/backend/billing/controller/PlanController.java
status: active
tags:
  - "#domain/billing"
  - "#layer/controller"
---

# 요약
- 공개 요금제(Plan) 조회 API를 제공한다.

# 역할 / 비목표
- 역할: 요금제 목록/단건 조회 요청을 서비스로 위임한다.
- 비목표: 요금제 생성/수정 로직을 처리하지 않는다.

# 의존성
- BillingService

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| getPlans | 요금제 목록 조회 | 입력: 없음 / 출력: ResponseEntity<List<PlanResponse>> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| getPlan | 요금제 단건 조회 | 입력: id / 출력: ResponseEntity<PlanResponse> | 없음 | ResourceNotFoundException(서비스) | 코드로 확인 불가 |

# 동작 정리
- `/api/v1/plans` 경로를 사용한다.

# 관련 문서
- [[billing/service/BillingService]]

