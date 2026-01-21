---
type: class
domain: billing
layer: dto
package: com.devwebsite.backend.billing.dto
source: backend/src/main/java/com/devwebsite/backend/billing/dto/PlanResponse.java
status: active
tags:
  - "#domain/billing"
  - "#layer/dto"
---

# 요약
- 요금제 조회 응답 DTO다.

# 역할 / 비목표
- 역할: Plan 엔티티를 응답 형태로 변환한다.
- 비목표: 요금제 관리 로직을 수행하지 않는다.

# 의존성
- Plan

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| from | 엔티티를 응답 DTO로 변환 | 입력: Plan / 출력: PlanResponse | 없음 | 코드로 확인 불가 | 해당 없음 |
| id() | 식별자 반환 | 입력: 없음 / 출력: Long | 없음 | 없음 | 해당 없음 |
| name() | 요금제 이름 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| displayName() | 요금제 표시명 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| description() | 설명 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| priceMonthly() | 월 요금 반환 | 입력: 없음 / 출력: BigDecimal | 없음 | 없음 | 해당 없음 |
| features() | 기능 목록 반환 | 입력: 없음 / 출력: Map<String, Object> | 없음 | 없음 | 해당 없음 |

# 관련 문서
- [[billing/service/BillingService]]

