---
type: class
domain: billing
layer: dto
package: com.devwebsite.backend.billing.dto
source: backend/src/main/java/com/devwebsite/backend/billing/dto/ChangePlanRequest.java
status: active
tags:
  - "#domain/billing"
  - "#layer/dto"
---

# 요약
- 플랜 변경 요청 DTO다.
- 변경할 플랜 ID를 입력으로 받는다.

# 역할 / 비목표
- 역할: 플랜 변경 요청의 입력 값을 보관한다.
- 비목표: 저장/검증 로직을 직접 수행하지 않는다.

# 의존성
- jakarta.validation

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| planId() | 플랜 ID 반환 | 입력: 없음 / 출력: Long | 없음 | 없음 | 해당 없음 |

# 동작 정리
- planId: 필수 (`@NotNull`)

# 관련 문서
- [[billing/controller/SubscriptionController]]

