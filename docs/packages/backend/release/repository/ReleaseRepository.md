---
type: class
domain: release
layer: repository
package: com.devwebsite.backend.release.repository
source: backend/src/main/java/com/devwebsite/backend/release/repository/ReleaseRepository.java
status: active
tags:
  - "#domain/release"
  - "#layer/repository"
---

# 요약
- 릴리스 조회를 위한 JPA 리포지토리다.
- 작성자 fetch와 타입 필터 쿼리를 제공한다.

# 역할 / 비목표
- 역할: 릴리스 조회용 커스텀 쿼리를 제공한다.
- 비목표: 비즈니스 규칙을 처리하지 않는다.

# 의존성
- Release

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| findAllWithAuthor | 릴리스 목록 조회(작성자 포함) | 입력: Pageable / 출력: Page<Release> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| findByIdWithAuthor | 릴리스 단건 조회(작성자 포함) | 입력: id / 출력: Optional<Release> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| findAllByReleaseType | 타입별 릴리스 조회(작성자 포함) | 입력: releaseType, Pageable / 출력: Page<Release> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| existsByVersion | 버전 중복 여부 확인 | 입력: version / 출력: boolean | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |

# 동작 정리
- 쿼리는 `LEFT JOIN FETCH r.author`를 사용한다.
- 목록 쿼리는 `releasedAt` 내림차순으로 정렬한다.

# 관련 문서
- [[release/entity/Release]]
- [[release/service/ReleaseService]]

