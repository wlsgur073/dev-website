# 공지 목록 조회 플로우

## 사전조건
- category는 선택 입력이다.
- pageable 파라미터가 전달될 수 있다.

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant AnnouncementController
  participant AnnouncementService
  participant AnnouncementRepository
  participant AnnouncementResponse
  alt category 있음
    AnnouncementController->>AnnouncementService: getPublishedAnnouncementsByCategory(category, pageable)
    AnnouncementService->>AnnouncementRepository: findAllPublishedByCategory(category, pageable)
  else category 없음
    AnnouncementController->>AnnouncementService: getPublishedAnnouncements(pageable)
    AnnouncementService->>AnnouncementRepository: findAllPublished(pageable)
  end
  AnnouncementRepository-->>AnnouncementService: Page<Announcement>
  AnnouncementService->>AnnouncementResponse: from(Announcement)
  AnnouncementService-->>AnnouncementController: Page<AnnouncementResponse>
```

## 메모
- 실패 케이스: 코드로 확인 불가
- 관측: 코드로 확인 불가
- 트랜잭션: AnnouncementService.getPublishedAnnouncements, getPublishedAnnouncementsByCategory는 readOnly 트랜잭션
