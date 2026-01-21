---
type: class
domain: billing
layer: dto
package: com.devwebsite.backend.billing.dto
source: backend/src/main/java/com/devwebsite/backend/billing/dto/UsageStatsResponse.java
status: active
tags:
  - "#domain/billing"
  - "#layer/dto"
---

# 요약
- 사용량 통계 응답 DTO다.
- 스텁 데이터를 반환하는 팩토리 메서드를 포함한다.

# 역할 / 비목표
- 역할: 사용량 통계 응답 형태를 제공한다.
- 비목표: 실제 통계 집계 로직을 수행하지 않는다.

# 의존성
- 없음

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| stub | 스텁 응답 생성 | 입력: 없음 / 출력: UsageStatsResponse | 없음 | 코드로 확인 불가 | 해당 없음 |
| requestsToday() | 금일 요청 수 반환 | 입력: 없음 / 출력: int | 없음 | 없음 | 해당 없음 |
| requestsThisMonth() | 당월 요청 수 반환 | 입력: 없음 / 출력: int | 없음 | 없음 | 해당 없음 |
| dailyLimit() | 일일 한도 반환 | 입력: 없음 / 출력: int | 없음 | 없음 | 해당 없음 |
| monthlyLimit() | 월간 한도 반환 | 입력: 없음 / 출력: int | 없음 | 없음 | 해당 없음 |
| lastRequestAt() | 마지막 요청 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |

# 동작 정리
- stub은 `(0, 0, 1000, 10000, null)` 값을 반환한다.

# 관련 문서
- [[billing/controller/SubscriptionController]]

