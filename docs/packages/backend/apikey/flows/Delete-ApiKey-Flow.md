# API 키 삭제 플로우

## 사전조건
- id가 필요하다.
- User 파라미터가 주입된다(@AuthenticationPrincipal).

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant ApiKeyController
  participant ApiKeyService
  participant ApiKeyRepository
  ApiKeyController->>ApiKeyService: deleteApiKey(id, user)
  ApiKeyService->>ApiKeyRepository: findByIdAndUser(id, user)
  ApiKeyRepository-->>ApiKeyService: Optional<ApiKey>
  alt 존재
    ApiKeyService->>ApiKeyRepository: delete(apiKey)
  else 없음
    ApiKeyService-->>ApiKeyController: ResourceNotFoundException
  end
```

## 메모
- 실패 케이스: ResourceNotFoundException("API key not found")
- 관측: 코드로 확인 불가
- 트랜잭션: ApiKeyService.deleteApiKey는 쓰기 트랜잭션
