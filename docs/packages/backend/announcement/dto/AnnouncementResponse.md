---
type: class
domain: announcement
layer: dto
package: com.devwebsite.backend.announcement.dto
source: backend/src/main/java/com/devwebsite/backend/announcement/dto/AnnouncementResponse.java
status: active
tags:
  - "#domain/announcement"
  - "#layer/dto"
---

# 요약
- 공지 응답 DTO다.
- 작성자 정보를 포함한 응답 형태를 제공한다.

# 역할 / 비목표
- 역할: Announcement 엔티티를 응답 형태로 변환한다.
- 비목표: 비즈니스 로직을 수행하지 않는다.

# 의존성
- Announcement

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| from | 엔티티를 응답 DTO로 변환 | 입력: Announcement / 출력: AnnouncementResponse | 없음 | 코드로 확인 불가 | 해당 없음 |
| id() | 식별자 반환 | 입력: 없음 / 출력: Long | 없음 | 없음 | 해당 없음 |
| title() | 제목 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| content() | 내용 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| category() | 카테고리 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| published() | 게시 여부 반환 | 입력: 없음 / 출력: boolean | 없음 | 없음 | 해당 없음 |
| publishedAt() | 게시 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |
| author() | 작성자 정보 반환 | 입력: 없음 / 출력: AuthorInfo | 없음 | 없음 | 해당 없음 |
| createdAt() | 생성 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |
| updatedAt() | 수정 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |

# 동작 정리
- 작성자가 없으면 `author`를 null로 설정한다.
- 작성자 정보는 `AuthorInfo(id, nickname)` 형태다.

# 관련 문서
- [[announcement/entity/Announcement]]
- [[announcement/service/AnnouncementService]]

