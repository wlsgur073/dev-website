---
type: class
domain: apikey
layer: service
package: com.devwebsite.backend.apikey.service
source: backend/src/main/java/com/devwebsite/backend/apikey/service/ApiKeyService.java
status: active
tags:
  - "#domain/apikey"
  - "#layer/service"
---

# 요약
- API 키 조회/생성/삭제 및 유효성 검증을 제공한다.
- 원문 키를 해시로 저장하고, 반환은 한 번만 수행한다.

# 역할 / 비목표
- 역할: 키 생성 규칙, 해시 저장, 키 검증을 수행한다.
- 비목표: HTTP 응답 구성은 처리하지 않는다.

# 의존성
- ApiKeyRepository
- ResourceNotFoundException
- SecureRandom

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| getApiKeys | 사용자 API 키 목록 조회 | 입력: User / 출력: List<ApiKeyResponse> | 없음 | 코드로 확인 불가 | 읽기(readOnly) |
| createApiKey | API 키 생성 및 저장 | 입력: CreateApiKeyRequest, User / 출력: ApiKeyCreatedResponse | 저장 | IllegalArgumentException(최대 개수 초과), RuntimeException(SHA-256 미지원) | 쓰기 |
| deleteApiKey | API 키 삭제 | 입력: id, User / 출력: void | 삭제 | ResourceNotFoundException | 쓰기 |
| validateApiKey | API 키 검증 및 사용자 반환 | 입력: rawKey / 출력: User | lastUsedAt 갱신 | ResourceNotFoundException, RuntimeException(SHA-256 미지원) | 쓰기 |

# 동작 정리
- 키는 `sk_` 접두사와 32바이트 랜덤 값을 사용해 생성한다.
- 사용자당 최대 키 개수는 10개로 제한한다.
- 저장은 원문 키가 아니라 SHA-256 해시를 저장한다.
- 반환용 표시 문자열은 앞 8글자 + `...` 형태로 만든다.
- `secretOnce`는 생성 응답에서만 제공된다.

# 관련 문서
- [[apikey/controller/ApiKeyController]]
- [[apikey/repository/ApiKeyRepository]]

