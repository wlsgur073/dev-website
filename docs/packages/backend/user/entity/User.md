---
type: class
domain: user
layer: entity
package: com.devwebsite.backend.user.entity
source: backend/src/main/java/com/devwebsite/backend/user/entity/User.java
status: active
tags:
  - "#domain/user"
  - "#layer/entity"
---

# 요약
- 사용자 정보를 표현하는 엔티티다.
- Spring Security `UserDetails`를 구현한다.

# 역할 / 비목표
- 역할: 사용자 상태(닉네임/비밀번호) 갱신과 권한 정보를 제공한다.
- 비목표: 인증/인가 흐름을 직접 처리하지 않는다.

# 의존성
- UserDetails
- SimpleGrantedAuthority
- LocalDateTime

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| updateNickname | 닉네임 변경 | 입력: nickname / 출력: void | nickname 변경 | 없음 | 해당 없음 |
| updatePassword | 비밀번호 변경 | 입력: encodedPassword / 출력: void | password 변경 | 없음 | 해당 없음 |
| getAuthorities | role 기반 단일 권한 반환 | 입력: 없음 / 출력: Collection<? extends GrantedAuthority> | 없음 | 없음 | 해당 없음 |
| getUsername | 이메일 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| getPassword | 저장된 비밀번호 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| isAccountNonExpired | 만료 정책 없음(항상 true) | 입력: 없음 / 출력: boolean | 없음 | 없음 | 해당 없음 |
| isAccountNonLocked | 잠금 정책 없음(항상 true) | 입력: 없음 / 출력: boolean | 없음 | 없음 | 해당 없음 |
| isCredentialsNonExpired | 자격 증명 만료 정책 없음(항상 true) | 입력: 없음 / 출력: boolean | 없음 | 없음 | 해당 없음 |
| isEnabled | 활성화 정책 없음(항상 true) | 입력: 없음 / 출력: boolean | 없음 | 없음 | 해당 없음 |

# 동작 정리
- Builder는 role이 null이면 `"ROLE_USER"`로 설정한다.
- `getAuthorities`는 role을 `SimpleGrantedAuthority`로 감싼다.
- `@PrePersist`에서 `createdAt`, `updatedAt`을 설정한다.

# 관련 문서
- [[user/service/UserService]]
