---
type: class
domain: announcement
layer: repository
package: com.devwebsite.backend.announcement.repository
source: backend/src/main/java/com/devwebsite/backend/announcement/repository/AnnouncementRepository.java
status: active
tags:
  - "#domain/announcement"
  - "#layer/repository"
---

# 요약
- 공지 조회를 위한 JPA 리포지토리다.
- 작성자 fetch join과 게시 조건을 포함한 쿼리를 제공한다.

# 역할 / 비목표
- 역할: 공지 조회용 커스텀 쿼리를 제공한다.
- 비목표: 비즈니스 규칙을 처리하지 않는다.

# 의존성
- Announcement

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| findAllPublished | 게시 공지 목록 조회(작성자 포함) | 입력: Pageable / 출력: Page<Announcement> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| findByIdAndPublishedTrue | 게시 공지 단건 조회(작성자 포함) | 입력: id / 출력: Optional<Announcement> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| findAllPublishedByCategory | 카테고리별 게시 공지 목록 조회(작성자 포함) | 입력: category, Pageable / 출력: Page<Announcement> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| findAllWithAuthor | 전체 공지 목록 조회(작성자 포함) | 입력: Pageable / 출력: Page<Announcement> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| findByIdWithAuthor | 공지 단건 조회(작성자 포함) | 입력: id / 출력: Optional<Announcement> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |

# 동작 정리
- 모든 쿼리는 `LEFT JOIN FETCH a.author`를 사용한다.
- 게시 목록 쿼리는 `publishedAt` 내림차순으로 정렬한다.

# 관련 문서
- [[announcement/entity/Announcement]]
- [[announcement/service/AnnouncementService]]

