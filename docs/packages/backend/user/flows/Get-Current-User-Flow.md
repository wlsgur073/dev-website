# 현재 사용자 조회 플로우

## 사전조건
- User 파라미터가 주입된다(@AuthenticationPrincipal).

## Mermaid 시퀀스 다이어그램
```mermaid
sequenceDiagram
  participant UserController
  participant UserService
  participant UserRepository
  participant UserResponse
  UserController->>UserService: getCurrentUser(user.email)
  UserService->>UserRepository: findByEmail(email)
  UserRepository-->>UserService: Optional<User>
  alt 존재
    UserService->>UserResponse: from(User)
    UserService-->>UserController: UserResponse
  else 없음
    UserService-->>UserController: ResourceNotFoundException
  end
```

## 메모
- 실패 케이스: ResourceNotFoundException("User not found")
- 관측: 코드로 확인 불가
- 트랜잭션: UserService.getCurrentUser는 readOnly 트랜잭션
