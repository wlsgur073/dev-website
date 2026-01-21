---
type: class
domain: auth
layer: service
package: com.devwebsite.backend.auth.service
source: backend/src/main/java/com/devwebsite/backend/auth/service/CustomUserDetailsService.java
status: active
tags:
  - "#domain/auth"
  - "#layer/service"
---

# 요약
- 스프링 시큐리티 `UserDetailsService` 구현체다.
- 이메일로 사용자 정보를 조회한다.

# 역할 / 비목표
- 역할: 인증 과정에서 사용자 로딩을 수행한다.
- 비목표: 사용자 생성/수정 로직을 처리하지 않는다.

# 의존성
- UserRepository

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| loadUserByUsername | 이메일 기반 사용자 조회 | 입력: email / 출력: UserDetails | 없음 | UsernameNotFoundException | 읽기(readOnly) |

# 동작 정리
- 조회 실패 시 `UsernameNotFoundException`을 발생시킨다.

# 관련 문서
- [[auth/service/AuthService]]

