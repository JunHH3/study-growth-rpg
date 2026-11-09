
<div align="center">

# 📚 Study Growth RPG

### 공부 시간을 성장 경험으로 바꾸는 학습 관리 서비스

![Status](https://img.shields.io/badge/STATUS-IN_PROGRESS-F4B400?style=for-the-badge)
![Java](https://img.shields.io/badge/Java_21-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)

</div>

## 🎯 Overview

학습 시간을 기록하고 경험치(XP)와 레벨 성장으로 연결하는 개인 프로젝트입니다. 현재 인증 도메인부터 단계적으로 구현 중입니다.

## ✅ Current Progress

- 회원가입 API `POST /api/auth/signup`
- 이메일·닉네임 중복 확인 및 비밀번호 해싱
- JPA 기반 `User`, `EmailVerificationToken` 모델
- 이메일 인증 토큰 생성 및 SHA-256 해싱 저장
- 인증 토큰 30분 만료·재발급·검증·삭제 로직
- JUnit 5 / Mockito 기반 인증 서비스 단위 테스트

## 🗓 Roadmap

- [x] 회원가입 및 사용자 도메인 구성
- [x] 이메일 인증 토큰 발급·검증 서비스 로직
- [ ] 이메일 발송 및 인증 API 연결
- [ ] 로그인·세션 관리
- [ ] 공부 타이머 및 중복 실행 제어
- [ ] XP 정산 및 레벨 성장
- [ ] 랭킹 및 통계 화면
- [ ] 통합 테스트 및 배포

## 🛠 Tech Stack

`Java 21` · `Spring Boot 4` · `Spring Data JPA` · `MySQL` · `Thymeleaf` · `Gradle` · `JUnit 5` · `Mockito`

## 🧱 Architecture

```text
AuthController
      ↓
AuthService
      ↓
UserRepository → MySQL

EmailVerificationService
      ↓
EmailVerificationTokenRepository
```

## ▶️ Getting Started

MySQL에 `study_growth_rpg` 데이터베이스를 생성하고 `DB_PASSWORD` 환경 변수를 설정합니다.

```bash
git clone https://github.com/JunHH3/study-growth-rpg.git
cd study-growth-rpg
./gradlew bootRun
```

테스트 실행:

```bash
./gradlew test
```

Windows에서는 `gradlew.bat`을 사용할 수 있습니다.

> 현재 개발 중인 프로젝트로 전체 서비스 기능은 아직 완성되지 않았습니다.

[🔗 GitHub Repository](https://github.com/JunHH3/study-growth-rpg)
