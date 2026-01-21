---
type: class
domain: announcement
layer: service
package: com.devwebsite.backend.announcement.service
source: backend/src/main/java/com/devwebsite/backend/announcement/service/AnnouncementService.java
status: active
tags:
  - "#domain/announcement"
  - "#layer/service"
---

# 요약
- 공지 조회 및 관리 로직을 제공한다.
- 공지 엔티티를 조회/저장하고 응답 DTO로 변환한다.

# 역할 / 비목표
- 역할: 공개/전체 공지 조회, 생성/수정/삭제 처리.
- 비목표: HTTP 요청/응답 구성은 처리하지 않는다.

# 의존성
- AnnouncementRepository
- ResourceNotFoundException

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| getPublishedAnnouncements | 게시된 공지 목록 조회 | 입력: Pageable / 출력: Page<AnnouncementResponse> | 없음 | 코드로 확인 불가 | 읽기(readOnly) |
| getPublishedAnnouncementsByCategory | 카테고리별 게시 공지 조회 | 입력: category, Pageable / 출력: Page<AnnouncementResponse> | 없음 | 코드로 확인 불가 | 읽기(readOnly) |
| getPublishedAnnouncement | 게시 공지 단건 조회 | 입력: id / 출력: AnnouncementResponse | 없음 | ResourceNotFoundException | 읽기(readOnly) |
| getAllAnnouncements | 전체 공지 조회(작성자 포함) | 입력: Pageable / 출력: Page<AnnouncementResponse> | 없음 | 코드로 확인 불가 | 읽기(readOnly) |
| getAnnouncement | 공지 단건 조회(작성자 포함) | 입력: id / 출력: AnnouncementResponse | 없음 | ResourceNotFoundException | 읽기(readOnly) |
| createAnnouncement | 공지 생성 | 입력: CreateAnnouncementRequest, User / 출력: AnnouncementResponse | 저장 | 코드로 확인 불가 | 쓰기 |
| updateAnnouncement | 공지 수정 | 입력: id, UpdateAnnouncementRequest / 출력: AnnouncementResponse | 엔티티 변경 | ResourceNotFoundException | 쓰기 |
| deleteAnnouncement | 공지 삭제 | 입력: id / 출력: void | 삭제 | ResourceNotFoundException | 쓰기 |

# 동작 정리
- 조회 결과는 `AnnouncementResponse.from`으로 변환한다.
- 수정은 `announcement.update(...)`로 제목/내용/카테고리를 갱신한다.
- `request.published()`가 null이 아니면 게시 상태를 변경한다.
- 삭제는 존재 여부 확인 후 `deleteById`를 호출한다.

# 관련 문서
- [[announcement/controller/AnnouncementController]]
- [[announcement/controller/AdminAnnouncementController]]
- [[announcement/repository/AnnouncementRepository]]
- [[announcement/entity/Announcement]]

