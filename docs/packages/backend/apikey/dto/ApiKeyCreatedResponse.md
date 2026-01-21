---
type: class
domain: apikey
layer: dto
package: com.devwebsite.backend.apikey.dto
source: backend/src/main/java/com/devwebsite/backend/apikey/dto/ApiKeyCreatedResponse.java
status: active
tags:
  - "#domain/apikey"
  - "#layer/dto"
---

# 요약
- API 키 생성 응답 DTO다.
- secretOnce를 포함해 최초 생성 시에만 전달한다.

# 역할 / 비목표
- 역할: ApiKey 엔티티와 secretOnce를 응답 형태로 변환한다.
- 비목표: 키 생성/저장 로직을 수행하지 않는다.

# 의존성
- ApiKey

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| of | 엔티티와 secretOnce를 응답 DTO로 변환 | 입력: ApiKey, secretOnce / 출력: ApiKeyCreatedResponse | 없음 | 코드로 확인 불가 | 해당 없음 |
| id() | 식별자 반환 | 입력: 없음 / 출력: Long | 없음 | 없음 | 해당 없음 |
| name() | 이름 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| keyPrefix() | 키 접두사 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| secretOnce() | 최초 1회 노출 시크릿 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| createdAt() | 생성 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |

# 동작 정리
- secretOnce는 생성 응답에서만 제공된다.

# 관련 문서
- [[apikey/service/ApiKeyService]]

