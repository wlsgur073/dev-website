---
type: class
domain: announcement
layer: dto
package: com.devwebsite.backend.announcement.dto
source: backend/src/main/java/com/devwebsite/backend/announcement/dto/UpdateAnnouncementRequest.java
status: active
tags:
  - "#domain/announcement"
  - "#layer/dto"
---

# 요약
- 공지 수정 요청 DTO다.
- 제목/내용/카테고리/게시 여부 변경 값을 전달한다.

# 역할 / 비목표
- 역할: 공지 수정 요청의 입력 값을 보관한다.
- 비목표: 저장/검증 로직을 직접 수행하지 않는다.

# 의존성
- jakarta.validation

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| title() | 제목 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| content() | 내용 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| category() | 카테고리 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| published() | 게시 여부 반환 | 입력: 없음 / 출력: Boolean | 없음 | 없음 | 해당 없음 |

# 동작 정리
- title: 최대 255자 (`@Size`)
- category: 최대 50자 (`@Size`)
- published는 null일 수 있다.

# 관련 문서
- [[announcement/controller/AdminAnnouncementController]]

