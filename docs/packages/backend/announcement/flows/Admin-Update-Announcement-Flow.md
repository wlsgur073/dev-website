# 공지 수정(관리자) 플로우

## 사전조건
- id와 UpdateAnnouncementRequest가 전달된다.

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant AdminAnnouncementController
  participant AnnouncementService
  participant AnnouncementRepository
  participant Announcement
  participant AnnouncementResponse
  AdminAnnouncementController->>AnnouncementService: updateAnnouncement(id, request)
  AnnouncementService->>AnnouncementRepository: findByIdWithAuthor(id)
  AnnouncementRepository-->>AnnouncementService: Optional<Announcement>
  alt 존재
    AnnouncementService->>Announcement: update(title, content, category)
    opt request.published != null
      AnnouncementService->>Announcement: setPublished(published)
    end
    AnnouncementService->>AnnouncementResponse: from(Announcement)
    AnnouncementService-->>AdminAnnouncementController: AnnouncementResponse
  else 없음
    AnnouncementService-->>AdminAnnouncementController: ResourceNotFoundException
  end
```

## 메모
- 실패 케이스: ResourceNotFoundException("Announcement not found")
- 관측: 코드로 확인 불가
- 트랜잭션: AnnouncementService.updateAnnouncement은 쓰기 트랜잭션
