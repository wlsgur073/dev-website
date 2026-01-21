# 릴리스 상세 조회 플로우

## 사전조건
- id가 필요하다.

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant ReleaseController
  participant ReleaseService
  participant ReleaseRepository
  participant ReleaseResponse
  ReleaseController->>ReleaseService: getRelease(id)
  ReleaseService->>ReleaseRepository: findByIdWithAuthor(id)
  ReleaseRepository-->>ReleaseService: Optional<Release>
  alt 존재
    ReleaseService->>ReleaseResponse: from(Release)
    ReleaseService-->>ReleaseController: ReleaseResponse
  else 없음
    ReleaseService-->>ReleaseController: ResourceNotFoundException
  end
```

## 메모
- 실패 케이스: ResourceNotFoundException("Release not found")
- 관측: 코드로 확인 불가
- 트랜잭션: ReleaseService.getRelease는 readOnly 트랜잭션
