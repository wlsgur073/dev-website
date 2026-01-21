---
type: class
domain: release
layer: service
package: com.devwebsite.backend.release.service
source: backend/src/main/java/com/devwebsite/backend/release/service/ReleaseService.java
status: active
tags:
  - "#domain/release"
  - "#layer/service"
---

# 요약
- 릴리스 조회/생성/수정/삭제 로직을 제공한다.
- 버전 중복을 검증한다.

# 역할 / 비목표
- 역할: 릴리스 엔티티 조회/저장 및 응답 변환.
- 비목표: HTTP 응답 구성은 처리하지 않는다.

# 의존성
- ReleaseRepository
- ResourceNotFoundException

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| getAllReleases | 전체 릴리스 조회 | 입력: Pageable / 출력: Page<ReleaseResponse> | 없음 | 코드로 확인 불가 | 읽기(readOnly) |
| getReleasesByType | 타입별 릴리스 조회 | 입력: ReleaseType, Pageable / 출력: Page<ReleaseResponse> | 없음 | 코드로 확인 불가 | 읽기(readOnly) |
| getRelease | 릴리스 단건 조회 | 입력: id / 출력: ReleaseResponse | 없음 | ResourceNotFoundException | 읽기(readOnly) |
| createRelease | 릴리스 생성 | 입력: CreateReleaseRequest, User / 출력: ReleaseResponse | 저장 | IllegalArgumentException(버전 중복) | 쓰기 |
| updateRelease | 릴리스 수정 | 입력: id, UpdateReleaseRequest / 출력: ReleaseResponse | 엔티티 변경 | ResourceNotFoundException, IllegalArgumentException(버전 중복) | 쓰기 |
| deleteRelease | 릴리스 삭제 | 입력: id / 출력: void | 삭제 | ResourceNotFoundException | 쓰기 |

# 동작 정리
- 생성/수정 시 버전 중복 여부를 확인한다.
- 수정은 `release.update(...)`로 필드를 갱신한다.
- 삭제는 존재 여부 확인 후 `deleteById`를 호출한다.

# 관련 문서
- [[release/controller/ReleaseController]]
- [[release/repository/ReleaseRepository]]

