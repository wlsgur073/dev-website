---
type: class
domain: user
layer: controller
package: com.devwebsite.backend.user.controller
source: backend/src/main/java/com/devwebsite/backend/user/controller/UserController.java
status: active
tags:
  - "#domain/user"
  - "#layer/controller"
---

# 요약
- 현재 사용자 정보 조회/수정 API를 제공한다.

# 역할 / 비목표
- 역할: 현재 사용자 요청을 서비스로 위임한다.
- 비목표: 사용자 인증/인가 로직을 처리하지 않는다.

# 의존성
- UserService

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| getCurrentUser | 현재 사용자 정보 조회 | 입력: User / 출력: ResponseEntity<UserResponse> | 없음 | ResourceNotFoundException(서비스) | 코드로 확인 불가 |
| updateCurrentUser | 현재 사용자 정보 수정 | 입력: User, UpdateUserRequest / 출력: ResponseEntity<UserResponse> | 사용자 정보 변경 | ResourceNotFoundException(서비스) | 코드로 확인 불가 |

# 동작 정리
- 요청 경로는 `/api/v1/me`이다.

# 관련 문서
- [[user/service/UserService]]

