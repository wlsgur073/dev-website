---
type: class
domain: user
layer: dto
package: com.devwebsite.backend.user.dto
source: backend/src/main/java/com/devwebsite/backend/user/dto/UpdateUserRequest.java
status: active
tags:
  - "#domain/user"
  - "#layer/dto"
---

# 요약
- 사용자 정보 수정 요청 DTO다.
- 닉네임/비밀번호 변경 값을 전달한다.

# 역할 / 비목표
- 역할: 사용자 수정 요청의 입력 값을 보관한다.
- 비목표: 저장/검증 로직을 직접 수행하지 않는다.

# 의존성
- jakarta.validation

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| nickname() | 닉네임 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| password() | 비밀번호 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |

# 동작 정리
- nickname: 2~50자 (`@Size`)
- password: 8~100자 (`@Size`)

# 관련 문서
- [[user/controller/UserController]]

