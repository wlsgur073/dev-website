---
type: class
domain: apikey
layer: dto
package: com.devwebsite.backend.apikey.dto
source: backend/src/main/java/com/devwebsite/backend/apikey/dto/CreateApiKeyRequest.java
status: active
tags:
  - "#domain/apikey"
  - "#layer/dto"
---

# 요약
- API 키 생성 요청 DTO다.
- 키 이름을 입력으로 받는다.

# 역할 / 비목표
- 역할: API 키 생성 요청의 입력 값을 보관한다.
- 비목표: 저장/검증 로직을 직접 수행하지 않는다.

# 의존성
- jakarta.validation

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| name() | 이름 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |

# 동작 정리
- name: 필수, 최대 100자 (`@NotBlank`, `@Size`)

# 관련 문서
- [[apikey/controller/ApiKeyController]]

