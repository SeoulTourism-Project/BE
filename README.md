# 🏙 Seoul Tourism

## 1. 소개와 기능
### 1.1 소개
최근 K-POP과 대형 OTT 콘텐츠를 통해 한국에 대한 관심이 급증하며, 방문 외국인 관광객의 수가 꾸준히 늘고 있습니다. 그러나 이들은 실제 여행 계획 단계에서 **"미디어에 소개된 장소 외에는 어디를 가야 할지 모른다"** 는 문제에 부딪힙니다. SNS와 검색 결과에 의존하다 보니 기존 여행객들의 루트를 그대로 따라가는 단편적인 일정이 반복되고, 진정한 한국의 매력을 경험하지 못하는 경우가 많습니다.

**"Seoul Tourism"** 는 이러한 문제를 해결하기 위해 탄생한 맞춤형 관광 플랫폼입니다. 단순히 유명 관광지만 소개하는 것을 넘어, **서울의 숨겨진 자연 경관, 역사적 문화재, 체험형 프로그램, 지역 특색 축제, 테마 거리** 등을 종합적으로 추천합니다. 이를 통해 여행객들은 기성 루트에서 벗어나 다양성과 깊이를 갖춘 한국만의 경험을 만들 수 있습니다.

### 1.2 기능
- 📍 여행지 찜하기: DB와 지도 API를 활용하여 사용자가 원하는 관광지를 저장 및 관리  
- 📆 여행 캘린더: 일정 및 세부 내용을 기록하고 관리하는 캘린더 기능 제공  
- 🛒 굿즈 상품 & 결제: 여행 관련 기념품 및 상품을 구매하고 결제 진행  
- 📊 관광 통계 분석: 공공 데이터 OpenAPI를 활용하여 서울 관광 통계를 제공  
- ⭐ 인기 관광지 추천: 사용자 선호도를 반영한 TOP 5 관광지 추천 기능  
- 🔑 회원가입 & 로그인: JWT 기반 사용자 인증 및 보안 강화 (Spring Security)

### 1.3 팀 구성
#### 팀원 소개 
- [B.E]김도현 
- [B.E]우비주 
- [B.E]안성현 
- [B.E]김보경 


## 2. 개발 환경 및 배포 URL 
### 2.1 개발 환경 

#### 🛠 기술 스택
- Backend: Java 17, Spring Boot 3.x, Spring Security, JPA, QueryDSL    
- Database: MariaDB 
- Cloud/Deploy: Docker, AWS EC2 
- Authentication: JWT (Spring Security 필터 방식)  
- API: 지도 API, 공공 데이터 OpenAPI 활용  
- Payment: PortOne


### 2.2 배포 URL
#### Frontend
- https://seoultourismweb.vercel.app
#### Backend
- http://ec2-3-36-66-32.ap-northeast-2.compute.amazonaws.com:8080/
- 테스트용 계정
    ```
    id : bbb@abcdef.com
    pw : super1234!
    ```

### 2.3  URL 구조 ※나중에 프론트 배포가 끝나면 프론트 URL추가 작성

- **User**

|App|Method|URL|Views Class|Note|Authentication|
|---|---|---|---|---|---|
|seoulTourism|POST|'/auth/email'|Auth|이메일 중복확인||
|seoulTourism|POST|'/auth/signup'|Auth|회원가입||
|seoulTourism|POST|'/auth/login'|Auth|로그인||
|seoulTourism|POST|'/reissue'|Auth|accessToken 재발급 및 refreshToken 갱신||
|seoulTourism|POST|'/logout'|Auth|로그아웃||
|seoulTourism|GET|'/auth/delete'|Auth|회원탈퇴||

- **Goods**

|App|Method|URL|Views Class|Note|Authentication|
|---|---|---|---|---|---|
|seoulTourism|GET|'/api/goods'|Goods|전체 목록 조회||
|seoulTourism|GET|'/api/goods/category/{category}'|Goods|카테고리별 조회||
|seoulTourism|GET|'/api/goods/{goodId}'|Goods|상세조회||
|seoulTourism|GET|'/api/goods/available'|Goods|판매중인 굿즈 조회||

- **Cart**

|App|Method|URL|Views Class|Note|Authentication|
|---|---|---|---|---|---|
|seoulTourism|POST|'/cart/add'|Cart|장바구니 담기|✅|
|seoulTourism|GET|'/cart/check'|Cart|장바구니 전체조회|✅|
|seoulTourism|POST|'/cart/update'|Cart|장바구니 수량 조절|✅|
|seoulTourism|DElETE|'/cart/delete'|Cart|장바구니 목록 삭제(단건)|✅|

- **Order**

|App|Method|URL|Views Class|Note|Authentication|
|---|---|---|---|---|---|
|seoulTourism|POST|'/order/create'|Order|주문 생성|✅|

- **Calendar**
  
|App|Method|URL|Views Class|Note|Authentication|
|---|---|---|---|---|---|
|seoulTourism|GET|'/calendar/schedule/{userId}?tourStartDate={tourDate}'|Calendar|유저와 투어시작일 기준으로 스케줄 조회|✅|
|seoulTourism|GET|'/calendar/schedule/all/{userId}'|Calendar|유저의 모든 스케줄 조회|✅|
|seoulTourism|DELETE|'/calendar/schedule/{calendarId}?userId={userId}'|Calendar|캘린더 삭제|✅|
|seoulTourism|POST|'/calendar/single'|Calendar|단일 캘린더 생성|✅|
|seoulTourism|GET|'/calendar/multipale'|Calendar|여러 캘린더 생성|✅|
|seoulTourism|GET|'/calendar/dates/{userId}'|Calendar|사용자에 따른 캘린더에 등록된 날짜만 조회|✅|

- **Fav-place**

|App|Method|URL|Views Class|Note|Authentication|
|---|---|---|---|---|---|
|seoulTourism|POST|'/fav-places/{mapId}/like?userId={userId}'|Fav-place|찜 추가기능||
|seoulTourism|GET|'/fav-places/{userId}'|Fav-place|추가한 찜 장소 내용 보여주기, 찜수 증가||
|seoulTourism|DELETE|'/fav-places/{mapId}/unlike?userId=10'|Fav-place|찜 해체시, 찜수 감소기능 및 찜 삭제||

- **Payment**

|App|Method|URL|Views Class|Note|Authentication|
|---|---|---|---|---|---|
|seoulTourism|POST|'/payment/process'|Payment|결제처리|✅|
|seoulTourism|GET|'/payment/history'|Payment|결제 내역 조회|✅|
|seoulTourism|POST|'/payment/cancel/{impUid}'|Payment|결제처리|✅|

- **Map**

|App|Method|URL|Views Class|Note|Authentication|
|---|---|---|---|---|---|
|seoulTourism|GET|'/api/maps'|Map|전체 맵 조회||
|seoulTourism|GET|'/api/maps/{mapId}'|Map|맵 상세조회||
|seoulTourism|POST|'/api/maps/1/new'|Map|찜 토글하기||
|seoulTourism|GET|'/maps/category/{category}'|Map|카테고리별로 맵 조회||

- **Statistic**
  
|App|Method|URL|Views Class|Note|Authentication|
|---|---|---|---|---|---|
|seoulTourism|GET|'/statistic/genderTop7Population?year={year}&month={month}'|Statistic|방문객(성별)중에서 월별 상위 7개 나라 조회||
|seoulTourism|GET|'/statistic/genderTop7ByYear?year={year}'|Statistic|방문객(성별)중에서 연도별 상위 7개 나라 조회||


## 3. 요구사항 명세와 기능 명세
### 3 - 1. 데이터 기반 통계

방문객 수, 여행지 선호도, 관심 지역 등 실제 데이터를 기반으로 한 통계 분석이 필요합니다.  이를 통해 정확한 데이터를 기반으로 해당 사이트를 사용하는 사용자에게 신뢰와 계획에 도움이 되는 선택지를 제공할 수 있도록 합니다.

### 3 - 2. 사용자 선호도 조사

사용자가 사이트를 사용하여 여행 계획을 수립할 때 저장한 여행지를 수집하여, 통계적으로 유의미한 인사이트를 도출합니다.  이를 통해 해당 사이트를 사용하는 사용자의 선호도를 확인하고 차트를 활용하여 통계 결과를 직관적으로 이해할 수 있도록 합니다.

## 4. 메인 기능
### 4-1. 공공데이터 Open API를 이용한 통계 분석 및 시각화
- 한국 관광 데이터랩에서 제공한 데이터를 가공하고 통계를 차트로 시각화
- Selenium과 Spring WebClient, Spring Batch를 이용한 정기적인 업데이트
- 캘린더에 저장된 여행지를 수집하여 사용자의 여행지 선호도 순위를 시각화

### 4-2. 캘린더를 통한 일정 관리
- 여행지를 찜(장바구니 개념)한다
- 찜한 여행지를 이용해 날짜와시간을 설정하여 타임 테이블을 생성
- 생성된 타임 테이블을 캘린더에서 확인 가능

### 4-3. 문화재 및 전통 기법을 이용한 굿즈 소개 및 판매
- 해당 굿즈에 대한 내용 및 수량을 확인
- 장바구니에 해당 품목을 저장
- 아임포트를 사용하여 결제 구현

## 5. 개발 일정(WBS) -->일정 작성(머메이드, 간트 차트, 엑셀 중 하나를 이용하여 시각화 자료), 화면을 캡쳐해서 사용하는 경우 gif,jpg 파일로 repository에 src/resources/image에 업로드해주세요.

**데일리 스크럼 시간 : 매일 오후 2시**

[![](https://github.com/weniv/project_sample_repo/raw/main/wbs_xlsx.png)](https://github.com/weniv/project_sample_repo/blob/main/wbs_xlsx.png)

- 좀 더 가벼운 프로젝트는 아래 일정표를 사용하세요.
- 아래 일정표는 [habitmaker.co.kr](https://habitmaker.co.kr/) 에서 작성되었습니다.
- 관련된 스택 표시는 [dev.habitmaker.co.kr](https://dev.habitmaker.co.kr/) 에서 작성되었습니다.

## 6. 데이터베이스 모델링(ERD) 및 변수명
- 아래 ERD는 [ERDCloud](https://www.erdcloud.com/)를 사용했습니다.

![](src/main/resources/image/ERD.png)

## 7. Architecture

![architecture-seoulTourism](https://github.com/user-attachments/assets/4d0e1d58-f510-41d3-af85-599cdca60648)



## 8. 와이어프레임 / UI / BM ※프론트에서 작업이 완료되는대로 추가/수정, 화면은 gif,jpg 파일로 repository에 src/resources/image에 업로드해주세요.
### 8.1 와이어프레임
- 아래 페이지별 상세 설명, 더 큰 이미지로 하나하나씩 설명 필요

[![](https://github.com/weniv/project_sample_repo/raw/main/ui.png)](https://github.com/weniv/project_sample_repo/blob/main/ui.png)

### 8.2 화면 설계
|   |   |
|---|---|
|메인|로그인|
|[![](https://github.com/weniv/project_sample_repo/raw/main/ui1.png)](https://github.com/weniv/project_sample_repo/blob/main/ui1.png)|[![](https://github.com/weniv/project_sample_repo/raw/main/ui2.png)](https://github.com/weniv/project_sample_repo/blob/main/ui2.png)|
|회원가입|정보수정|
|[![](https://github.com/weniv/project_sample_repo/raw/main/ui3.png)](https://github.com/weniv/project_sample_repo/blob/main/ui3.png)|[![](https://github.com/weniv/project_sample_repo/raw/main/ui3.png)](https://github.com/weniv/project_sample_repo/blob/main/ui3.png)|
|검색|번역|
|[![](https://github.com/weniv/project_sample_repo/raw/main/ui3.png)](https://github.com/weniv/project_sample_repo/blob/main/ui3.png)|[![](https://github.com/weniv/project_sample_repo/raw/main/ui3.png)](https://github.com/weniv/project_sample_repo/blob/main/ui3.png)|
|선택삭제|글쓰기|
|[![](https://github.com/weniv/project_sample_repo/raw/main/ui3.png)](https://github.com/weniv/project_sample_repo/blob/main/ui3.png)|[![](https://github.com/weniv/project_sample_repo/raw/main/ui3.png)](https://github.com/weniv/project_sample_repo/blob/main/ui3.png)|
|글 상세보기|댓글|
|[![](https://github.com/weniv/project_sample_repo/raw/main/ui3.png)](https://github.com/weniv/project_sample_repo/blob/main/ui3.png)|[![](https://github.com/weniv/project_sample_repo/raw/main/ui3.png)](https://github.com/weniv/project_sample_repo/blob/main/ui3.png)|


## 9. 트러블 슈팅
### - ec 서버에 MariaDB 서버 설치
  #### 1.디비버 접속 ec2 연결 오류
   **1) Access denied for user 'root'@'121.64.137.133' (using password: YES)<br> → MariaDB 서버에 외부에서 접속을 시도할 때 발생하는 권한 문제**

**해결시도1. AWS 인바운드 보안규칙 설정**
![image](src/main/resources/image/트러블슈팅1.png)

**해결시도2. SSH로 연결시도**
![image](src/main/resources/image/트러블슈팅2.png)

private Key 에 ppk 파일 넣어주면 됨 <br>
→ 하지만 연결 문제 해결되진 않았음

**해결시도3. 서버에서 접근 권한 부여**
```cmd
sudo mysql -u root -p select user, host, plugin from user; #(플러그인체크)

USE mysql;
update user set plugin='mysql_native_password' where user='root';

grant all privileges on *.* to '아이디'@'localhost';
#특정 사용자에게 전 권한 부여
grant all privileges on DB명.* to '아이디'@'localhost';
#특정 사용자에게 특정 DB접근권한 부여
FLUSH PRIVILEGES;
(재시동)

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '재설정 비밀번호';

select user, host, plugin from user; #호스트 재확인
grant all privileges on *.* to '계정명'@'%'; #권한부여
flush privileges; #재시작

create user '계정명'@'%' identified by 'password'; #'%' 모든 IP 접근 가능 호스트 생성
```
**2) dbeaver connection timeout**<br>
→ 주로 네트워크 연결, 방화벽 설정, 또는 서버 설정 문제가 원인 <br>
이 부분 에러는 Authentication/username 이 잘못들어가있어서 오류가 뜬걸로 예상됨 <br>
username을 root에서 ec2-user 로 변경해주니 서버 연결됨!

#### 참고자료&블로그
**1.AWS EC2에 MariaDB 설치하기**<br>
https://saml-planner.com/2024/02/19/aws-ec2%EC%97%90-mariadb-%EC%84%A4%EC%B9%98%ED%95%98%EA%B8%B0/ --> 해당 주소 접근이 안됨

**2.[AWS] EC2 Linux2에 MariaDB 설치 및 데이터 마이그레이션**<br>
https://gom20.tistory.com/293

**3.dbeaver EC2 Mysql 연결 오류 - 에러메시지별 해결방법**<br>
https://velog.io/@nowlee/dbeaver-EC2-Mysql-%EC%97%B0%EA%B2%B0-%EC%98%A4%EB%A5%98-%EC%97%90%EB%9F%AC%EB%A9%94%EC%8B%9C%EC%A7%80%EB%B3%84-%ED%95%B4%EA%B2%B0%EB%B0%A9%EB%B2%95


### - docker + ec2 서버 배포하기 
  ### ec2 서버에 docker를 이용하여 배포했으나 서버가 실행이 안됨. <br> 
*log 확인시 기존에는 인테리제이 환경변수 설정을 통해 주입했으나 ec2 서버에는 설정이 되어있지 않아 서버 실행이 안됨.*

**해결시도 1 .env 파일 생성하여 환경변수 주입**
1)ec2서버에서 .env 파일 생성  
```c
//예시
DATABASE_DRIVER=com.mysql.cj.jdbc.Driver
DATABASE_USERNAME=root
DATABASE_PASSWORD=yourpassword
DATABASE_URL=jdbc:mysql://your-database-endpoint:3306/tourism
JWT_SECRET_KEY=your-secret-key
ACCESS_KEY=your-aws-access-key
SECRET_KEY=your-aws-secret-key
IMP_KEY=your-imp-key
IMP_SECRET_KEY=your-imp-secret
```

2)ec2 서버에서 docker-compose.yml 작성
```c
version: '3.8'

services:
  tourism-app:
    image: tourism-app
    container_name: tourism-container
    restart: always
    ports:
      - "8080:8080"
    env_file:
      - .env
```

3)ec서버에서 docker-compose.yml을 이용하여 재빌드
```c
docker-compose up -d
```

-> 그래도 서버 실행이 되지않음. 여전히 환경변수 적용 안됨.

**해결시도 2. 환경변수 주입이 왜 안되는지 파악하여 재빌드**  <br>

메인 어플리케이션 시작하자 시스템 출력으로 환경변수 찍어보니 제대로 나옴  
그러나 스프링부트가 시작되면 환경변수가 null로 나옴  
문제는 프로파일을 사용하고 있었는데 적용이 안되어 어플리케이션 실행시는 환경변수가 주입되었으나,<br>스프링부트로 실행시 프로파일적용이 안되어 환경변수를 불러오지 못함.  
-> 프로파일을 적용한 doker 파일로 재빌드
```c
# 1. 기본 이미지 설정 (JDK 17 사용)
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 생성
WORKDIR /app

# 3. JAR 파일 복사
COPY ./build/libs/${JAR_FILE} app.jar

# 4. 환경 변수 설정(dev 프로파일을 활성화한 상태에서 실행됨.)
ENV SPRING_PROFILES_ACTIVE=dev

# 5. 컨테이너 실행 시 JAR 파일 실행
CMD ["java", "-jar", "app.jar"]
```
-> 재빌드하여 서버 실행시 문제 해결되어 환경변수 주입 되는것 확인


