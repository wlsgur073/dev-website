# 공지 생성(관리자) 플로우

## 사전조건
- CreateAnnouncementRequest가 전달된다.
- author(User) 파라미터가 주입된다(@AuthenticationPrincipal).

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant AdminAnnouncementController
  participant AnnouncementService
  participant AnnouncementRepository
  participant AnnouncementResponse
  AdminAnnouncementController->>AnnouncementService: createAnnouncement(request, author)
  AnnouncementService->>AnnouncementRepository: save(Announcement)
  AnnouncementRepository-->>AnnouncementService: Announcement
  AnnouncementService->>AnnouncementResponse: from(Announcement)
  AnnouncementService-->>AdminAnnouncementController: AnnouncementResponse
```

## 메모
- 실패 케이스: 코드로 확인 불가
- 관측: 코드로 확인 불가
- 트랜잭션: AnnouncementService.createAnnouncement은 쓰기 트랜잭션
