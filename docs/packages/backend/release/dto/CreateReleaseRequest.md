---
type: class
domain: release
layer: dto
package: com.devwebsite.backend.release.dto
source: backend/src/main/java/com/devwebsite/backend/release/dto/CreateReleaseRequest.java
status: active
tags:
  - "#domain/release"
  - "#layer/dto"
---

# 요약
- 릴리스 생성 요청 DTO다.
- 버전/제목/내용/타입/릴리스 시각을 전달한다.

# 역할 / 비목표
- 역할: 릴리스 생성 요청의 입력 값을 보관한다.
- 비목표: 저장/검증 로직을 직접 수행하지 않는다.

# 의존성
- jakarta.validation
- Release.ReleaseType

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| version() | 버전 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| title() | 제목 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| content() | 내용 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| releaseType() | 릴리스 타입 반환 | 입력: 없음 / 출력: ReleaseType | 없음 | 없음 | 해당 없음 |
| releasedAt() | 릴리스 시각 반환 | 입력: 없음 / 출력: LocalDateTime | 없음 | 없음 | 해당 없음 |

# 동작 정리
- version: 필수, 최대 50자 (`@NotBlank`, `@Size`)
- title: 필수, 최대 255자 (`@NotBlank`, `@Size`)
- content: 필수 (`@NotBlank`)

# 관련 문서
- [[release/controller/AdminReleaseController]]

