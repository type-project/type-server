# Type Server

> "청각 장애인 배리어프리 수업 필기 자동화 어플 Type"

[Source Code](https://github.com/type-project/type-server/tree/develop)

### 작품 소개

<table>
  <tr>
    <td><img width="778" alt="스크린샷 2023-04-09 오후 6 01 59" src="https://user-images.githubusercontent.com/69137469/230764912-baf10e0b-196a-4593-ab77-fc9f94031c29.png"></td>
    <td><img width="778" alt="스크린샷 2023-04-09 오후 6 02 19" src="https://user-images.githubusercontent.com/69137469/230764930-4f4f2815-e91d-49ab-b9c7-ad81bfd5edab.png"></td>
    <td><img width="777" alt="스크린샷 2023-04-09 오후 6 02 35" src="https://user-images.githubusercontent.com/69137469/230764927-6531248f-d040-4f07-a34a-c04139abdfb9.png"></td>
    <td><img width="779" alt="스크린샷 2023-04-09 오후 6 02 50" src="https://user-images.githubusercontent.com/69137469/230764925-af2c2d32-0db5-4bcf-9a0e-9abff1ba7401.png"></td>
    <td><img width="777" alt="스크린샷 2023-04-09 오후 6 03 05" src="https://user-images.githubusercontent.com/69137469/230764923-d42f8563-ee03-4aff-a825-f52797ffba25.png"></td>
  </tr>
 </table>
 
+ Type은 청각 장애인 학생이 제삼자의 도움 없이 능동적으로, 수업의 전달에 시차를 최소화해 실시간 상황에서 즉각적으로 수업에 집중할 수 있도록 도와주기 위한 기능을 제공하는 프로젝트입니다.

### 개발 환경
```bash
JVM 17.0.6
Gradle 7.6
Springboot 3.0.2
```

### 사용 기술
+ Java, Spring Boot, Spring Data JPA, MariaDB
+ Kotlin, Gradle
+ AWS EC2, AWS RDS, AWS S3, Google Cloud Platform Speech-to-Text

### ER Diagram
<img width="1324" alt="스크린샷 2023-03-30 오후 7 15 46" src="https://user-images.githubusercontent.com/69137469/230763616-57694eb5-1d6b-4004-ae50-38015a1c5a72.png">

+ user_profile : 사용자 정보를 관리합니다
+ note : 강의 노트 정보를 저장합니다
+ slide : 강의 노트의 슬라이드 별 요약을 저장합니다
+ speech_to_text : 강의 노트의 자막을 저장합니다
+ quiz : 강의 노트를 기반으로 한 빈칸 퀴즈를 저장합니다
+ problem : 문제 솔루션 요청과 그에 대한 gpt3.5의 답을 저장합니다

### 기능 구현
+ GCP의 Speech-to-Text API를 이용하여 강의 음성을 실시간으로 문자로 변환합니다.
+ 강의 내용 전체에 대한 요약을 제공합니다.
+ PPT, PDF 등의 수업 자료를 기반으로 하는 경우 각 슬라이드마다의 핵심 내용 요약을 제공합니다.
+ 강의 내용을 바탕으로 빈칸 채우기 문제를 생성하여 학습에 도움을 줍니다.
+ 강의 내용과 관련된 교재의 문제를 찍어 해답을 요청하면, 강의 내용과 GPT3.5의 지식을 바탕으로 한 답안을 제공합니다.

### 기대 효과
+ 이는 청각 장애를 가진 학생들에게 폭넓은 교육의 기회를 제공하는 발판이 될 것입니다.
+ 또한, 비청각장애인 학생도 Type이 제공하는 음성 문서화, 자동 요약, 복습 문제 생성 등의 기능을 자기주도 학습에 효과적으로 사용할 수 있습니다.
