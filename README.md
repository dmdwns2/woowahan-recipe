# 📖 우아한 레시피


⚠️ gitlab 과 github 의 이메일 주소가 달라서 contribute 동기화가 되지 않아 기여도가 나오지 않습니다.
gitlab 의 contribute 로 대체합니다.
![스크린샷 2023-04-27 오전 12 27 10](https://user-images.githubusercontent.com/105894868/234625001-88a8856d-7138-4944-a6f6-cebf5510db76.png)

gitlab 주소 : https://gitlab.com/th42500/woowahan_recipe_team10

- 기존에 존재하는 레시피 사이트들은 관련 재료를 구매하는 기능이 없습니다
- 기존에 존재하는 식품 이커머스 사이트들은 재료와 관련된 레시피를 제공하지 않습니다
- 우아한 남매들은 이러한 불편한 점을 모두 개선한 사이트로 다양한 레시피를 제공하고 그와 관련된 재료를 한번에 구매할 수 있습니다 😊

<br />

### 🌱 제공하는 기능

👉 [우아한 레시피 기능 보러가기](https://www.notion.so/Front-Rreview-7eb64f2ba6344b1e90dd8a6703fddeda)

시연영상 : https://drive.google.com/file/d/1NDytIlL2FpUXkLb5VuWMQAvFzA3mX-l_/view?usp=share_link


### 📢 실행 방법

---

아래의 Environment Variable을 설정 후 실행

|      Environment Variable       |                         form                          |
| :-----------------------------: | :---------------------------------------------------: |
|      SPRING_DATASOURCE_URL      | jdbc:mysql://[EC2 Public IPv4 DNS]:3306/[Schema Name] |
|      SPRING_DATASOUCE_USER      |                         root                          |
|   SPRING_DATASOURCE_PASSWORD    |                         root                          |
|        JWT_SECRET_TOKEN         |                    aa.bb.cc.dd.ee                     |
|       CLOUD_AWS_S3_BUCKET       |                     S3 Bucket 명                      |
|     CLOUD_AWS_REGION_STATIC     |                        S3 리전                        |
| CLOUD_AWS_CREDENTIALS_ACCESSKEY |  AmazonS3FullAccess 권한이 있는 IAM 사용자 AccessKey  |
| CLOUD_AWS_CREDENTIALS_SECRETKEY |  AmazonS3FullAccess 권한이 있는 IAM 사용자 SecretKey  |
|             IMP_KEY             |                  123456789012345678                   |

<br />

### 💻 기술 스택 및 개발 환경

---

**언어 / 툴 **

- Java 11
- Intellij Ultimate
- Gradle

**Backend**

- Spring Boot 2.7.5
- Spring Data JPA
- Spring Security
- QueryDsl
- MySQL

**Frontend**

- Thymeleaf 2.7.5

**배포**

- GitLab Runner
- Docker
- EC2

<br />

### ✨ Service Architecture

![image1](https://user-images.githubusercontent.com/105894868/219577763-1510e1fb-fb0e-4880-b4bc-04a1edd75d3f.png)


<br />

### 🧩 ERD

![ERD](./assets/ERD.png)

<br />

### 🌱 우아한 남매들이 말하는 프로젝트 후기

✔ [개발을-하면서-고민하거나-노력한-부분.md](./readme/개발을-하면서-고민하거나-노력한-부분.md)

✔  [개발을-하며-겪은-어려움과-해결-방법.md](./readme/개발을-하며-겪은-어려움과-해결-방법.md)

✔  [아쉬웠던-점-&-프로젝트-개선-방향.md](./readme/아쉬웠던-점-&-프로젝트-개선-방향.md)



### 💬 우아한 남매들 팀의 개발 이야기

---

| N주차 |                        진행 상황 공유                        |
| :---: | :----------------------------------------------------------: |
| 1주차 | [[1주차] idea-10팀-진행상황-공유](./readme/[1주차]-idea-10팀-진행상황-공유.md) |
| 2주차 | [[2주차] idea-10팀-진행상황-공유](./readme/[2주차]-idea-10팀-진행상황-공유.md) |
| 3주차 | [[3주차] idea-10팀-진행상황-공유](./readme/[3주차]-idea-10팀-진행상황-공유.md) |
| 4주차 | [[4주차] idea-10팀-진행상황-공유](./readme/[4주차]-idea-10팀-진행상황-공유.md) |

<br />

### 🌳 Team Notion

[우아한 남매들 Team Notion](https://www.notion.so/23-01-13-23-02-16-12ddd64750ad46a0b1547e64ab6fbf5c)

