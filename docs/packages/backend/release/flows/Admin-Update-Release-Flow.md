# 릴리스 수정(관리자) 플로우

## 사전조건
- id와 UpdateReleaseRequest가 전달된다.

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant AdminReleaseController
  participant ReleaseService
  participant ReleaseRepository
  participant Release
  participant ReleaseResponse
  AdminReleaseController->>ReleaseService: updateRelease(id, request)
  ReleaseService->>ReleaseRepository: findByIdWithAuthor(id)
  ReleaseRepository-->>ReleaseService: Optional<Release>
  alt 릴리스 없음
    ReleaseService-->>AdminReleaseController: ResourceNotFoundException
  else 릴리스 존재
    opt request.version 변경
      ReleaseService->>ReleaseRepository: existsByVersion(request.version)
    end
    alt 버전 중복
      ReleaseService-->>AdminReleaseController: IllegalArgumentException
    else 중복 없음
      ReleaseService->>Release: update(version, title, content, releaseType, releasedAt)
      ReleaseService->>ReleaseResponse: from(Release)
      ReleaseService-->>AdminReleaseController: ReleaseResponse
    end
  end
```

## 메모
- 실패 케이스: ResourceNotFoundException("Release not found"), IllegalArgumentException("Version already exists: ...")
- 관측: 코드로 확인 불가
- 트랜잭션: ReleaseService.updateRelease는 쓰기 트랜잭션
