---
type: class
domain: auth
layer: dto
package: com.devwebsite.backend.auth.dto
source: backend/src/main/java/com/devwebsite/backend/auth/dto/LoginRequest.java
status: active
tags:
  - "#domain/auth"
  - "#layer/dto"
---

# 요약
- 로그인 요청 DTO다.
- 이메일과 비밀번호를 입력으로 받는다.

# 역할 / 비목표
- 역할: 로그인 요청의 입력 값을 보관한다.
- 비목표: 인증/검증 로직을 직접 수행하지 않는다.

# 의존성
- jakarta.validation

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| email() | 이메일 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| password() | 비밀번호 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |

# 동작 정리
- email: 필수, 이메일 형식 (`@NotBlank`, `@Email`)
- password: 필수 (`@NotBlank`)

# 관련 문서
- [[auth/controller/AuthController]]

