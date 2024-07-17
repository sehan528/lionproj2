# Spring Boot 요구사항정의서 개인프로젝트

### 멋쟁이사자처럼 백엔드 스쿨 10기: 장세창 Java 개인프로젝트

<br>

## 프로젝트 소개
• Spring Boot 를 이용한 웹 애플리케이션 개발 프로젝트 <br>
• Spring Boot MVC 의 Template 엔진(Thymeleaf)을 이용해 View 를 구성하고 바닐라 JavaScript 로 API 를 호출하여 동적으로 동작하는 기능을 구현합니다. <br>
o 자바스크립트로 API 를 호출하여 목록을 보여주거나, email 이 존재하는지 검사하거나 등을 구현할 수 있습니다. <br>
• Spring Data JPA, Spring MVC 를 이용해 구현한다. <br>
 

<br>

## 주요 기능

### 1. 메인 페이지 
![image](https://github.com/user-attachments/assets/c7396435-29d7-44cf-ac70-cbed8db3bee2)
- URL: `/vlog.io`
- 트렌딩 포스트를 주간/월간 단위로 표시
- 좋아요 수와 업데이트 날짜 기준으로 정렬
- 비공개 게시글은 제외

### 2. 최근 게시물
![image](https://github.com/user-attachments/assets/9c3ca73b-e2d9-4fbd-ab2c-9f9c1582eefc)
- URL: `/vlog.io/read`
- 유저 별 개인 최근 조회 기록 저장 
- 무한 스크롤링 구현

### 3. 사용자 프로필 페이지
![image](https://github.com/user-attachments/assets/c6f05e84-b7d3-43ff-8918-00bfb23cdad6)
- URL: `/vlog.io/@{username}`
- 사용자의 게시물, 시리즈, 소개(About) 정보 표시
- 탭 형식으로 구현 (게시물, 시리즈, 소개)

### 4. 게시물 상세 페이지
![image](https://github.com/user-attachments/assets/f3e62837-29f8-4380-a4d1-346b880db9f7)
- URL: `/vlog.io/@{username}/{postname}`
- 게시물 내용, 태그, 시리즈 정보 표시
- 좋아요 기능 구현

### 5. 좋아요한 게시물 목록
![image](https://github.com/user-attachments/assets/3d51a27e-4a45-4a12-9355-483c898690a9)
- URL: `/vlog.io/lists`
- 사용자가 like를 누른 게시물 목록 표시

### 6. 사용자 설정
![image](https://github.com/user-attachments/assets/ad9c9b5a-c3cf-4dfe-aeb8-7ca47e5a0b8d)
- URL: `/vlog.io/setting`
- 프로필 이미지, 이름, 이메일 수정 기능
- 회원 탈퇴 기능

### 7. 검색 기능
![image](https://github.com/user-attachments/assets/0de65c8b-a245-450b-bfef-2f975132d0e5)
- URL: `/vlog/search`
- 제목, 내용, 태그 기반 검색
- 대소문자 구분 없는 검색

### 8. 글 작성 기능
![image](https://github.com/user-attachments/assets/95b9c8c0-b983-4f36-b6e8-2dcfad849f76)
- URL: `/vlog.io/write`
- 마크다운 에디터 (Toast UI) 사용
- 태그 추가 기능 (최대 3개)
- 임시 저장 기능 (로컬 스토리지 활용)
- 게시글 공개/비공개 설정
- 시리즈 추가 기능

### 10. 네비게이션 바
![image](https://github.com/user-attachments/assets/1fec75e7-6688-4233-a78a-a100b31de22f)
![image](https://github.com/user-attachments/assets/6fe070f7-0d1d-4558-904f-fc7b64e18404)
- 로그인 상태에 따른 동적 메뉴 표시
- 사용자 이름을 이용한 개인화된 로고 표시

  ### 11. 로그인 / 회원가입
  ![image](https://github.com/user-attachments/assets/5fd62936-0530-457f-a3a1-463fc385e308) 
  ![image](https://github.com/user-attachments/assets/1993f0fc-0bb6-4c7b-a00c-443e6e68201f) 
- URL: `/login` , `/signup` 
- 입력 정보 유효성 검사
- 비밀번호 암호화 후 서버로 전송
- 회원가입 시 기본 프로필 이미지 제공

<br>

## ERD
![image](https://github.com/user-attachments/assets/d6ca212f-3b69-458c-ba6e-e0666b19a30a)




