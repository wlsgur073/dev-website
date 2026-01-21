---
type: class
domain: release
layer: controller
package: com.devwebsite.backend.release.controller
source: backend/src/main/java/com/devwebsite/backend/release/controller/ReleaseController.java
status: active
tags:
  - "#domain/release"
  - "#layer/controller"
---

# 요약
- 공개 릴리스 조회 API를 제공한다.
- 릴리스 타입 필터와 페이징 정렬을 처리한다.

# 역할 / 비목표
- 역할: 공개 릴리스 목록/단건 조회를 서비스로 위임한다.
- 비목표: 릴리스 생성/수정/삭제를 처리하지 않는다.

# 의존성
- ReleaseService

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| getReleases | 릴리스 목록 조회(타입 필터 가능) | 입력: type?, Pageable / 출력: ResponseEntity<Page<ReleaseResponse>> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| getRelease | 릴리스 단건 조회 | 입력: id / 출력: ResponseEntity<ReleaseResponse> | 없음 | ResourceNotFoundException(서비스) | 코드로 확인 불가 |

# 동작 정리
- 기본 정렬은 `releasedAt` 내림차순, 페이지 크기는 10이다.
- 타입 필터가 있으면 `getReleasesByType`을 호출한다.

# 관련 문서
- [[release/service/ReleaseService]]

