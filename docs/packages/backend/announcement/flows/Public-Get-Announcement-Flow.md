# 공지 상세 조회 플로우

## 사전조건
- id가 필요하다.

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant AnnouncementController
  participant AnnouncementService
  participant AnnouncementRepository
  participant AnnouncementResponse
  AnnouncementController->>AnnouncementService: getPublishedAnnouncement(id)
  AnnouncementService->>AnnouncementRepository: findByIdAndPublishedTrue(id)
  AnnouncementRepository-->>AnnouncementService: Optional<Announcement>
  alt 존재
    AnnouncementService->>AnnouncementResponse: from(Announcement)
    AnnouncementService-->>AnnouncementController: AnnouncementResponse
  else 없음
    AnnouncementService-->>AnnouncementController: ResourceNotFoundException
  end
```

## 메모
- 실패 케이스: ResourceNotFoundException("Announcement not found")
- 관측: 코드로 확인 불가
- 트랜잭션: AnnouncementService.getPublishedAnnouncement은 readOnly 트랜잭션
