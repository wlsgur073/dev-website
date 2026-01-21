---
type: class
domain: auth
layer: filter
package: com.devwebsite.backend.auth.filter
source: backend/src/main/java/com/devwebsite/backend/auth/filter/CsrfProtectionFilter.java
status: active
tags:
  - "#domain/auth"
  - "#layer/filter"
---

# 요약
- 쿠키 기반 인증 엔드포인트에 대한 CSRF 보호 필터다.
- Origin/Referer 헤더를 허용 목록과 비교한다.

# 역할 / 비목표
- 역할: 보호 대상 경로에 대해 Origin/Referer를 검증한다.
- 비목표: 인증/인가 자체를 처리하지 않는다.

# 의존성
- app.cors.allowed-origins 설정 값

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| 없음 | public 메서드 없음 | - | - | - | - |

# 동작 정리
- 보호 경로는 `/api/v1/auth/refresh`, `/api/v1/auth/logout`이며 POST 요청만 검증한다.
- 허용 Origin 목록은 설정값을 쉼표로 분리해 구성한다.
- 검증 실패 시 403과 `application/problem+json` 응답을 작성하고 체인을 종료한다.

# 관련 문서
- [[auth/controller/AuthController]]

