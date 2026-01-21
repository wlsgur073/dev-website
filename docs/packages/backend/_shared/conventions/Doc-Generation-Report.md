# 문서 생성/검증 리포트

## 수정 사항
- 클래스 문서 frontmatter의 source 경로를 실제 파일 경로에 맞게 `backend/src/main/java/...`로 정합화했다.
- 링크 규칙 문서에서 예시용 위키링크 표기를 제거하고 실제 링크 예시만 남겼다.

## 코드로 확인 불가 항목 요약
- 전역 예외 문서는 현재 문서 없음(코드로 확인 불가).
- 도메인별 에러코드 정의가 없어 에러 문서가 없음: [[announcement/announcement-Index]], [[apikey/apikey-Index]], [[auth/auth-Index]], [[billing/billing-Index]], [[release/release-Index]], [[user/user-Index]]
- 주요 메서드 표에서 예외/트랜잭션/부작용 일부는 코드에 근거가 없어 `코드로 확인 불가`로 남음(Controller/Service/Repository/DTO/Entity 문서 전반).
- 플로우 문서의 실패 케이스/관측/트랜잭션 항목 중 일부는 코드 근거가 없어 `코드로 확인 불가`로 남음: [[announcement/announcement-Index]], [[apikey/apikey-Index]], [[auth/auth-Index]], [[billing/billing-Index]], [[release/release-Index]], [[user/user-Index]]
