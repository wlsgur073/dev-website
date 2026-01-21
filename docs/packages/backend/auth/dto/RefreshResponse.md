---
type: class
domain: auth
layer: dto
package: com.devwebsite.backend.auth.dto
source: backend/src/main/java/com/devwebsite/backend/auth/dto/RefreshResponse.java
status: active
tags:
  - "#domain/auth"
  - "#layer/dto"
---

# 요약
- 토큰 갱신 응답 DTO다.
- 액세스 토큰과 토큰 타입을 포함한다.

# 역할 / 비목표
- 역할: 토큰 갱신 응답 형태를 제공한다.
- 비목표: 인증 로직을 수행하지 않는다.

# 의존성
- 없음

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| of | 액세스 토큰으로 응답 DTO 생성 | 입력: accessToken / 출력: RefreshResponse | 없음 | 코드로 확인 불가 | 해당 없음 |
| accessToken() | 액세스 토큰 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |
| tokenType() | 토큰 타입 반환 | 입력: 없음 / 출력: String | 없음 | 없음 | 해당 없음 |

# 동작 정리
- of는 tokenType을 `"Bearer"`로 설정한다.

# 관련 문서
- [[auth/controller/AuthController]]

