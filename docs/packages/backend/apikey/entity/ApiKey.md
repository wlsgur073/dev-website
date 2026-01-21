---
type: class
domain: apikey
layer: entity
package: com.devwebsite.backend.apikey.entity
source: backend/src/main/java/com/devwebsite/backend/apikey/entity/ApiKey.java
status: active
tags:
  - "#domain/apikey"
  - "#layer/entity"
---

# 요약
- API 키 정보를 표현하는 엔티티다.
- 마지막 사용 시각을 기록한다.

# 역할 / 비목표
- 역할: API 키 상태(마지막 사용 시각)를 갱신한다.
- 비목표: 키 생성/검증 로직을 처리하지 않는다.

# 의존성
- User
- LocalDateTime

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| updateLastUsedAt | 마지막 사용 시각 갱신 | 입력: 없음 / 출력: void | lastUsedAt 변경 | 없음 | 코드로 확인 불가 |

# 동작 정리
- `@PrePersist`에서 `createdAt`을 현재 시각으로 설정한다.

# 관련 문서
- [[apikey/service/ApiKeyService]]

