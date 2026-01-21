---
type: class
domain: release
layer: entity
package: com.devwebsite.backend.release.entity
source: backend/src/main/java/com/devwebsite/backend/release/entity/Release.java
status: active
tags:
  - "#domain/release"
  - "#layer/entity"
---

# 요약
- 릴리스 정보를 표현하는 엔티티다.
- 버전/타입/릴리스 시각 정보를 관리한다.

# 역할 / 비목표
- 역할: 릴리스 속성 업데이트 로직을 제공한다.
- 비목표: 조회/저장 로직을 처리하지 않는다.

# 의존성
- User
- LocalDateTime

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| update | 릴리스 정보 갱신 | 입력: version, title, content, releaseType, releasedAt / 출력: void | 필드 변경 | 없음 | 코드로 확인 불가 |

# 동작 정리
- Builder는 releaseType이 null이면 MINOR로 설정한다.
- releasedAt이 null이면 현재 시각으로 설정한다.
- `@PrePersist`에서 `createdAt`, `updatedAt`을 설정한다.

# 관련 문서
- [[release/service/ReleaseService]]

