---
type: class
domain: apikey
layer: controller
package: com.devwebsite.backend.apikey.controller
source: backend/src/main/java/com/devwebsite/backend/apikey/controller/ApiKeyController.java
status: active
tags:
  - "#domain/apikey"
  - "#layer/controller"
---

# 요약
- 사용자 API 키 조회/생성/삭제 API를 제공한다.
- 생성 시 secretOnce를 포함한 응답을 반환한다.

# 역할 / 비목표
- 역할: API 키 관리 요청을 서비스로 위임하고 응답을 구성한다.
- 비목표: 키 생성/해시 같은 보안 로직을 직접 수행하지 않는다.

# 의존성
- ApiKeyService

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| getApiKeys | 사용자 API 키 목록 조회 | 입력: User / 출력: ResponseEntity<List<ApiKeyResponse>> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| createApiKey | API 키 생성 | 입력: CreateApiKeyRequest, User / 출력: ResponseEntity<ApiKeyCreatedResponse> | 저장 | IllegalArgumentException(최대 개수 초과) | 코드로 확인 불가 |
| deleteApiKey | API 키 삭제 | 입력: id, User / 출력: ResponseEntity<Map<String, String>> | 삭제 | ResourceNotFoundException(서비스) | 코드로 확인 불가 |

# 동작 정리
- 생성 성공 시 HTTP 201을 반환한다.
- 삭제 응답은 `{"message": "API key deleted successfully"}` 형태의 Map이다.

# 관련 문서
- [[apikey/service/ApiKeyService]]
- [[apikey/apikey-Index]]

