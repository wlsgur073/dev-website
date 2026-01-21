---
type: class
domain: release
layer: dto
package: com.devwebsite.backend.release.dto
source: backend/src/main/java/com/devwebsite/backend/release/dto/ReleaseResponse.java
status: active
tags:
  - "#domain/release"
  - "#layer/dto"
---

# 요약
- 릴리스 조회 응답 DTO다.
- 작성자 정보를 포함한다.

# 역할 / 비목표
- 역할: Release 엔티티를 응답 형태로 변환한다.
- 비목표: 릴리스 관리 로직을 수행하지 않는다.

# 의존성
- Release

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| from | 엔티티를 응답 DTO로 변환 | 입력: Release / 출력: ReleaseResponse | 없음 | 코드로 확인 불가 | 해당 없음 |
| id() | 식별자 반환 | 입력: 없음 / 출력: Long | 없음 | 없음 | 해당 없음 |
| version() | 버전 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| title() | 제목 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| content() | 내용 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| releaseType() | 릴리스 타입 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| releasedAt() | 릴리스 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |
| author() | 작성자 정보 반환 | 입력: 없음 / 출력: AuthorInfo | 없음 | 없음 | 해당 없음 |
| createdAt() | 생성 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |
| updatedAt() | 수정 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |

# 동작 정리
- 작성자가 없으면 `author`를 null로 설정한다.
- 작성자 정보는 `AuthorInfo(id, nickname)` 형태다.

# 관련 문서
- [[release/entity/Release]]
- [[release/service/ReleaseService]]

