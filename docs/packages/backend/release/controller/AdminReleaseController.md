---
type: class
domain: release
layer: controller
package: com.devwebsite.backend.release.controller
source: backend/src/main/java/com/devwebsite/backend/release/controller/AdminReleaseController.java
status: active
tags:
  - "#domain/release"
  - "#layer/controller"
---

# 요약
- 관리자용 릴리스 관리 API를 제공한다.
- 조회/생성/수정/삭제를 서비스로 위임한다.

# 역할 / 비목표
- 역할: 관리자 릴리스 관리 엔드포인트 제공.
- 비목표: 릴리스 비즈니스 규칙을 직접 처리하지 않는다.

# 의존성
- ReleaseService

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| getAllReleases | 전체 릴리스 조회 | 입력: Pageable / 출력: ResponseEntity<Page<ReleaseResponse>> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| getRelease | 릴리스 단건 조회 | 입력: id / 출력: ResponseEntity<ReleaseResponse> | 없음 | ResourceNotFoundException(서비스) | 코드로 확인 불가 |
| createRelease | 릴리스 생성 | 입력: CreateReleaseRequest, User / 출력: ResponseEntity<ReleaseResponse> | 저장 | IllegalArgumentException(버전 중복) | 코드로 확인 불가 |
| updateRelease | 릴리스 수정 | 입력: id, UpdateReleaseRequest / 출력: ResponseEntity<ReleaseResponse> | 상태 변경 | ResourceNotFoundException, IllegalArgumentException(버전 중복) | 코드로 확인 불가 |
| deleteRelease | 릴리스 삭제 | 입력: id / 출력: ResponseEntity<Map<String, String>> | 삭제 | ResourceNotFoundException(서비스) | 코드로 확인 불가 |

# 동작 정리
- 생성 시 HTTP 201을 반환한다.
- 삭제 응답은 `{"message": "Release deleted successfully"}` 형태의 Map이다.
- 목록 기본 정렬은 `createdAt` 내림차순, 페이지 크기는 10이다.

# 관련 문서
- [[release/service/ReleaseService]]

