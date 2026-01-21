# 릴리스 생성(관리자) 플로우

## 사전조건
- CreateReleaseRequest가 전달된다.
- author(User) 파라미터가 주입된다(@AuthenticationPrincipal).

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant AdminReleaseController
  participant ReleaseService
  participant ReleaseRepository
  participant ReleaseResponse
  AdminReleaseController->>ReleaseService: createRelease(request, author)
  ReleaseService->>ReleaseRepository: existsByVersion(request.version)
  alt 버전 존재
    ReleaseService-->>AdminReleaseController: IllegalArgumentException
  else 버전 없음
    ReleaseService->>ReleaseRepository: save(Release)
    ReleaseRepository-->>ReleaseService: Release
    ReleaseService->>ReleaseResponse: from(Release)
    ReleaseService-->>AdminReleaseController: ReleaseResponse
  end
```

## 메모
- 실패 케이스: IllegalArgumentException("Version already exists: ...")
- 관측: 코드로 확인 불가
- 트랜잭션: ReleaseService.createRelease는 쓰기 트랜잭션
