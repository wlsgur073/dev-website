# 릴리스 목록 조회 플로우

## 사전조건
- releaseType은 선택 입력이다.
- pageable 파라미터가 전달될 수 있다.

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant ReleaseController
  participant ReleaseService
  participant ReleaseRepository
  participant ReleaseResponse
  alt releaseType 있음
    ReleaseController->>ReleaseService: getReleasesByType(releaseType, pageable)
    ReleaseService->>ReleaseRepository: findAllByReleaseType(releaseType, pageable)
  else releaseType 없음
    ReleaseController->>ReleaseService: getAllReleases(pageable)
    ReleaseService->>ReleaseRepository: findAllWithAuthor(pageable)
  end
  ReleaseRepository-->>ReleaseService: Page<Release>
  ReleaseService->>ReleaseResponse: from(Release)
  ReleaseService-->>ReleaseController: Page<ReleaseResponse>
```

## 메모
- 실패 케이스: 코드로 확인 불가
- 관측: 코드로 확인 불가
- 트랜잭션: ReleaseService.getAllReleases, getReleasesByType는 readOnly 트랜잭션
