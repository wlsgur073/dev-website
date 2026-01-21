---
type: class
domain: user
layer: service
package: com.devwebsite.backend.user.service
source: backend/src/main/java/com/devwebsite/backend/user/service/UserService.java
status: active
tags:
  - "#domain/user"
  - "#layer/service"
---

# 요약
- 사용자 조회 및 현재 사용자 정보 수정 로직을 제공한다.

# 역할 / 비목표
- 역할: 사용자 조회, 닉네임/비밀번호 변경 처리.
- 비목표: 인증/인가 로직을 처리하지 않는다.

# 의존성
- UserRepository
- PasswordEncoder
- ResourceNotFoundException

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| getCurrentUser | 이메일로 사용자 조회 | 입력: email / 출력: UserResponse | 없음 | ResourceNotFoundException | 읽기(readOnly) |
| updateCurrentUser | 사용자 정보 업데이트 | 입력: email, UpdateUserRequest / 출력: UserResponse | 사용자 정보 변경 | ResourceNotFoundException | 쓰기 |

# 동작 정리
- 닉네임은 null/blank가 아닌 경우에만 변경한다.
- 비밀번호는 인코딩 후 저장한다.

# 관련 문서
- [[user/entity/User]]

