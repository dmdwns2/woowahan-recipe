# π μ°μν λ μνΌ

- κΈ°μ‘΄μ μ‘΄μ¬νλ λ μνΌ μ¬μ΄νΈλ€μ κ΄λ ¨ μ¬λ£λ₯Ό κ΅¬λ§€νλ κΈ°λ₯μ΄ μμ΅λλ€
- κΈ°μ‘΄μ μ‘΄μ¬νλ μν μ΄μ»€λ¨Έμ€ μ¬μ΄νΈλ€μ μ¬λ£μ κ΄λ ¨λ λ μνΌλ₯Ό μ κ³΅νμ§ μμ΅λλ€
- μ°μν λ¨λ§€λ€μ μ΄λ¬ν λΆνΈν μ μ λͺ¨λ κ°μ ν μ¬μ΄νΈλ‘ λ€μν λ μνΌλ₯Ό μ κ³΅νκ³  κ·Έμ κ΄λ ¨λ μ¬λ£λ₯Ό νλ²μ κ΅¬λ§€ν  μ μμ΅λλ€ π

<br />

### π± μ κ³΅νλ κΈ°λ₯

π [μ°μν λ μνΌ κΈ°λ₯ λ³΄λ¬κ°κΈ°](https://www.notion.so/Front-Rreview-7eb64f2ba6344b1e90dd8a6703fddeda)

μμ°μμ : https://drive.google.com/file/d/1NDytIlL2FpUXkLb5VuWMQAvFzA3mX-l_/view?usp=share_link


### π’ μ€ν λ°©λ²

---

μλμ Environment Variableμ μ€μ  ν μ€ν

|      Environment Variable       |                         form                          |
| :-----------------------------: | :---------------------------------------------------: |
|      SPRING_DATASOURCE_URL      | jdbc:mysql://[EC2 Public IPv4 DNS]:3306/[Schema Name] |
|      SPRING_DATASOUCE_USER      |                         root                          |
|   SPRING_DATASOURCE_PASSWORD    |                         root                          |
|        JWT_SECRET_TOKEN         |                    aa.bb.cc.dd.ee                     |
|       CLOUD_AWS_S3_BUCKET       |                     S3 Bucket λͺ                      |
|     CLOUD_AWS_REGION_STATIC     |                        S3 λ¦¬μ                         |
| CLOUD_AWS_CREDENTIALS_ACCESSKEY |  AmazonS3FullAccess κΆνμ΄ μλ IAM μ¬μ©μ AccessKey  |
| CLOUD_AWS_CREDENTIALS_SECRETKEY |  AmazonS3FullAccess κΆνμ΄ μλ IAM μ¬μ©μ SecretKey  |
|             IMP_KEY             |                  123456789012345678                   |

<br />

### π κ³μ  λ° λ°°ν¬ μ£Όμ

----

**HEAD κ³μ ** : head / head1234

**ADMIN κ³μ ** : admin / admin1234

**SELLER κ³μ ** : seller1 / seller1234

**USER κ³μ ** : user1 / user1234

λ°°ν¬ μ£Όμ : http://woowahan.store:8080/ λ‘ μ μνμ¬ μ°μν λ μνΌ μ€ν

Swagger: http://woowahan.store:8080/swagger-ui/ λ‘ μ μνμ¬ Swagger μ€ν

<br />

### π» κΈ°μ  μ€ν λ° κ°λ° νκ²½

---

**μΈμ΄ / ν΄ **

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

**λ°°ν¬**

- GitLab Runner
- Docker
- EC2

<br />

### β¨ Service Architecture

![image1](https://user-images.githubusercontent.com/105894868/219577763-1510e1fb-fb0e-4880-b4bc-04a1edd75d3f.png)


<br />

### π§© ERD

![ERD](./assets/ERD.png)

<br />

### π± μ°μν λ¨λ§€λ€μ΄ λ§νλ νλ‘μ νΈ νκΈ°

β [κ°λ°μ-νλ©΄μ-κ³ λ―Όνκ±°λ-λΈλ ₯ν-λΆλΆ.md](./readme/κ°λ°μ-νλ©΄μ-κ³ λ―Όνκ±°λ-λΈλ ₯ν-λΆλΆ.md)

β  [κ°λ°μ-νλ©°-κ²ͺμ-μ΄λ €μκ³Ό-ν΄κ²°-λ°©λ².md](./readme/κ°λ°μ-νλ©°-κ²ͺμ-μ΄λ €μκ³Ό-ν΄κ²°-λ°©λ².md)

β  [μμ¬μ λ-μ -&-νλ‘μ νΈ-κ°μ -λ°©ν₯.md](./readme/μμ¬μ λ-μ -&-νλ‘μ νΈ-κ°μ -λ°©ν₯.md)



### π¬ μ°μν λ¨λ§€λ€ νμ κ°λ° μ΄μΌκΈ°

---

| Nμ£Όμ°¨ |                        μ§ν μν© κ³΅μ                         |
| :---: | :----------------------------------------------------------: |
| 1μ£Όμ°¨ | [[1μ£Όμ°¨] idea-10ν-μ§νμν©-κ³΅μ ](./readme/[1μ£Όμ°¨]-idea-10ν-μ§νμν©-κ³΅μ .md) |
| 2μ£Όμ°¨ | [[2μ£Όμ°¨] idea-10ν-μ§νμν©-κ³΅μ ](./readme/[2μ£Όμ°¨]-idea-10ν-μ§νμν©-κ³΅μ .md) |
| 3μ£Όμ°¨ | [[3μ£Όμ°¨] idea-10ν-μ§νμν©-κ³΅μ ](./readme/[3μ£Όμ°¨]-idea-10ν-μ§νμν©-κ³΅μ .md) |
| 4μ£Όμ°¨ | [[4μ£Όμ°¨] idea-10ν-μ§νμν©-κ³΅μ ](./readme/[4μ£Όμ°¨]-idea-10ν-μ§νμν©-κ³΅μ .md) |

<br />

### π³ Team Notion

[μ°μν λ¨λ§€λ€ Team Notion](https://www.notion.so/23-01-13-23-02-16-12ddd64750ad46a0b1547e64ab6fbf5c)

