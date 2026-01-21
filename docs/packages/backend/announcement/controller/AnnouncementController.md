---
type: class
domain: announcement
layer: controller
package: com.devwebsite.backend.announcement.controller
source: backend/src/main/java/com/devwebsite/backend/announcement/controller/AnnouncementController.java
status: active
tags:
  - "#domain/announcement"
  - "#layer/controller"
---

# 요약
- 공개 공지 조회 API를 제공한다.
- 카테고리 필터와 페이징 정렬을 지원한다.

# 역할 / 비목표
- 역할: 공개 공지 목록/단건 조회 요청을 처리하고 서비스로 위임한다.
- 비목표: 공지 생성/수정/삭제와 같은 관리자 기능을 처리하지 않는다.

# 의존성
- AnnouncementService

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| getAnnouncements | 공개 공지 목록 조회 | 입력: category?, pageable / 출력: ResponseEntity<Page<AnnouncementResponse>> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| getAnnouncement | 공개 공지 단건 조회 | 입력: id / 출력: ResponseEntity<AnnouncementResponse> | 없음 | ResourceNotFoundException(서비스) | 코드로 확인 불가 |

# 동작 정리
- category가 비어있지 않으면 카테고리 필터 조회를 호출한다.
- 기본 페이지 크기는 10이고 정렬은 `publishedAt` 내림차순이다.

# 관련 문서
- [[announcement/service/AnnouncementService]]
- [[announcement/announcement-Index]]

