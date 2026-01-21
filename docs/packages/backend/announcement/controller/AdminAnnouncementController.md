---
type: class
domain: announcement
layer: controller
package: com.devwebsite.backend.announcement.controller
source: backend/src/main/java/com/devwebsite/backend/announcement/controller/AdminAnnouncementController.java
status: active
tags:
  - "#domain/announcement"
  - "#layer/controller"
---

# 요약
- 관리자 공지 관리 API를 제공한다.
- 조회/생성/수정/삭제를 서비스로 위임한다.

# 역할 / 비목표
- 역할: 관리자 공지 관리 엔드포인트를 제공하고 요청을 처리한다.
- 비목표: 공지 비즈니스 규칙을 직접 처리하지 않는다.

# 의존성
- AnnouncementService

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| getAllAnnouncements | 전체 공지 조회(미게시 포함) | 입력: pageable / 출력: ResponseEntity<Page<AnnouncementResponse>> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| getAnnouncement | 공지 단건 조회(미게시 포함) | 입력: id / 출력: ResponseEntity<AnnouncementResponse> | 없음 | ResourceNotFoundException(서비스) | 코드로 확인 불가 |
| createAnnouncement | 공지 생성 | 입력: CreateAnnouncementRequest, User / 출력: ResponseEntity<AnnouncementResponse> | 저장 | 코드로 확인 불가 | 코드로 확인 불가 |
| updateAnnouncement | 공지 수정 | 입력: id, UpdateAnnouncementRequest / 출력: ResponseEntity<AnnouncementResponse> | 상태 변경 | ResourceNotFoundException(서비스) | 코드로 확인 불가 |
| deleteAnnouncement | 공지 삭제 | 입력: id / 출력: ResponseEntity<Map<String, String>> | 삭제 | ResourceNotFoundException(서비스) | 코드로 확인 불가 |

# 동작 정리
- 생성 시 HTTP 201을 반환한다.
- 삭제 응답은 `{"message": "Announcement deleted successfully"}` 형태의 Map이다.

# 관련 문서
- [[announcement/service/AnnouncementService]]
- [[announcement/announcement-Index]]

