# 📊 MyPayList Backend

소비 기록을 관리하고 통계를 제공하는 백엔드 API 서버입니다.  
Spring Boot 기반으로 JWT 인증을 사용하며, 사용자 맞춤 카테고리 분류 및 월별/카테고리별 소비 통계를 제공합니다.

---

## 🔧 기술 스택

- Java 17
- Spring Boot 3
- Spring Data JPA + Hibernate
- MySQL 8
- JWT (JSON Web Token) 인증
- Gradle

---

## 📂 프로젝트 폴더 구조

```
backend/
├── domain/
│   ├── category/
│   ├── pay/
│   ├── statistics/
│   └── user/
├── global/
│   └── jwt/
│   └── config/
└── MyPayListApplication.java


---

## 🗂 ERD (Entity Relationship Diagram)
![mypaylist_ERD](https://github.com/user-attachments/assets/751d772a-342e-4047-a2f8-b0b9c9225ba2)

- **User** : 회원 정보 (id, 이메일, 비밀번호, 닉네임)
- **Category** : 사용자별 소비 항목 분류 (id, 이름, 색상)
- **PayRecord** : 소비 내역 (id, 제목, 날짜, 금액, 카테고리 연결)

---

## ✨ 주요 기능 소개

### 🔐 사용자 인증

| 메소드   | 엔드포인트              | 설명      |
|--------|----------------------|----------|
| POST   | `/api/users/signup`  | 회원가입    |
| POST   | `/api/users/login`   | 로그인     |

- JWT 토큰 기반 인증 방식 사용

### 📁 카테고리 관리

| 메소드 | 엔드포인트           | 설명          |
|--------|----------------------|---------------|
| POST   | `/api/categories`    | 카테고리 등록 |
| GET    | `/api/categories`    | 카테고리 조회 |

### 💸 소비 기록

| 메소드 | 엔드포인트                   | 설명               |
|--------|-------------------------|--------------------|
| POST   | `/api/payrecords`          | 소비 기록 등록     |
| PUT    | `/api/payrecords/{id}`     | 소비 기록 수정     |
| DELETE | `/api/payrecords/{id}`     | 소비 기록 삭제     |
| GET    | `/api/payrecords`          | 소비 기록 전체 조회 |

### 📈 통계 API

| 메소드 | 엔드포인트                                         | 설명             |
|--------|----------------------------------------------------|------------------|
| GET    | `/api/statistics?year=2025&month=4`       | 카테고리별 소비 통계 조회   |

---

## 📘 API 명세

### 🔐 사용자 인증

#### 🔸 회원가입
- `POST /api/users/signup`
- Request Body:
```json
{
  "email": "test@example.com",
  "password": "password123",
  "nickname": "tester"
}
```

#### 🔸 로그인
- `POST /api/users/login`
- Request Body:
```json
{
  "email": "test@example.com",
  "password": "password123"
}
```
- Response:
```json
{
  "accessToken": "jwt-token"
}
```

---

### 📁 카테고리 API

#### 🔸 카테고리 등록
- `POST /api/categories`
- Header: `Authorization: Bearer <accessToken>`
- Request Body:
```json
{
  "name": "식비",
  "color": "#FFAA00"
}
```

#### 🔸 카테고리 목록 조회
- `GET /api/categories`
- Header: `Authorization: Bearer <accessToken>`

---

### 💸 소비 기록 API

#### 🔸 소비 기록 등록
- `POST /api/payrecords`
- Header: `Authorization: Bearer <accessToken>`
- Request Body:
```json
{
  "title": "커피",
  "amount": 4500,
  "date": "2025-04-12",
  "categoryId": 1
}
```

#### 🔸 소비 기록 수정
- `PATCH /api/payrecords/{id}`
- Request Body:
```json
{
  "title": "아이스 아메리카노",
  "amount": 5000,
  "date": "2025-04-12",
  "categoryId": 1
}
```

#### 🔸 소비 기록 삭제
- `DELETE /api/payrecords/{id}`

#### 🔸 소비 기록 전체 조회
- `GET /api/payrecords`

---

### 📈 통계 API

#### 🔸 카테고리별 소비 통계
- `GET /api/statistics?year=2025&month=4`

---

## 🔐 인증 방식

모든 API는 JWT 토큰 기반 인증을 사용합니다.  
로그인 후 받은 토큰을 요청 헤더에 아래와 같이 포함시켜야 합니다.

```
Authorization: Bearer <access_token>
```

---

## 🧪 테스트 도구

- API 테스트는 **Thunder Client**로 수행하였습니다.
- 로그인, 회원가입, 소비 등록, 통계 API 등 모든 기능을 JWT 인증과 함께 테스트 완료

---

## ▶️ 로컬 실행 방법

```bash
# 의존성 설치
./gradlew build

# 로컬 서버 실행
./gradlew bootRun
```

---

## ⚙️ application.properties 예시

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mypaylist
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 📌 개발 일정 요약

- 사용자 인증 (회원가입/로그인): ✅ 완료
- 카테고리 등록/조회: ✅ 완료
- 소비 기록 등록/조회/수정/삭제: ✅ 완료
- 월별/카테고리별 통계 API: ✅ 완료
- 예외 처리 및 Swagger 문서화: ⏳ 생략 (간단한 로컬 테스트 목적)

---

## 🙌 기여 및 문의

프로젝트에 대한 제안, 기여, 피드백은 언제든 환영합니다!  
