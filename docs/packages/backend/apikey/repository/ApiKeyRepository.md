---
type: class
domain: apikey
layer: repository
package: com.devwebsite.backend.apikey.repository
source: backend/src/main/java/com/devwebsite/backend/apikey/repository/ApiKeyRepository.java
status: active
tags:
  - "#domain/apikey"
  - "#layer/repository"
---

# 요약
- API 키 조회를 위한 JPA 리포지토리다.
- 사용자 연관 조회 및 해시 기반 조회를 제공한다.

# 역할 / 비목표
- 역할: API 키 조회용 커스텀 쿼리를 제공한다.
- 비목표: 비즈니스 규칙을 처리하지 않는다.

# 의존성
- ApiKey
- User

# 주요 메서드
| Method | 목적 | 입력/출력 | 부작용 | 예외/에러 | 트랜잭션/락 |
| --- | --- | --- | --- | --- | --- |
| findAllByUserOrderByCreatedAtDesc | 사용자 API 키 목록 조회 | 입력: User / 출력: List<ApiKey> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| findByIdAndUser | 사용자 소유 키 단건 조회 | 입력: id, User / 출력: Optional<ApiKey> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| findByKeyHash | 해시로 키 조회 | 입력: keyHash / 출력: Optional<ApiKey> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| findByKeyHashWithUser | 해시로 키+사용자 조회 | 입력: keyHash / 출력: Optional<ApiKey> | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |
| countByUser | 사용자 키 개수 조회 | 입력: User / 출력: long | 없음 | 코드로 확인 불가 | 코드로 확인 불가 |

# 동작 정리
- `findByKeyHashWithUser`는 `JOIN FETCH a.user`를 사용한다.

# 관련 문서
- [[apikey/entity/ApiKey]]
- [[apikey/service/ApiKeyService]]

