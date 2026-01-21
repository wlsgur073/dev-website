---
type: class
domain: announcement
layer: entity
package: com.devwebsite.backend.announcement.entity
source: backend/src/main/java/com/devwebsite/backend/announcement/entity/Announcement.java
status: active
tags:
  - "#domain/announcement"
  - "#layer/entity"
---

# 요약
- 공지 정보를 표현하는 엔티티다.
- 게시 상태와 게시 시각을 관리한다.

# 역할 / 비목표
- 역할: 공지의 상태 변경과 기본 속성 업데이트를 제공한다.
- 비목표: 조회/저장 같은 영속성 처리를 직접 담당하지 않는다.

# 의존성
- User
- LocalDateTime

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| update | 제목/내용/카테고리 갱신 | 입력: title, content, category / 출력: void | 필드 변경 | 없음 | 코드로 확인 불가 |
| publish | 게시 상태로 전환 | 입력: 없음 / 출력: void | published, publishedAt 변경 | 없음 | 코드로 확인 불가 |
| unpublish | 미게시 상태로 전환 | 입력: 없음 / 출력: void | published, publishedAt 변경 | 없음 | 코드로 확인 불가 |
| setPublished | 게시 상태 설정 | 입력: published / 출력: void | 상태 변경 | 없음 | 코드로 확인 불가 |

# 동작 정리
- Builder에서 published가 true면 `publishedAt`을 현재 시각으로 설정한다.
- `@PrePersist`에서 `createdAt`, `updatedAt`을 현재 시각으로 설정한다.
- `publish`는 이미 게시 상태일 때는 변경하지 않는다.

# 관련 문서
- [[announcement/service/AnnouncementService]]

