# smtp_socket_programming
SMTP를 [RFC5321](https://www.rfc-editor.org/rfc/rfc5321.html)과 ESMTP (Extended SMTP)기반으로 JAVA 상에서 메일 전송을 구현한 코드입니다.
<br>

### 1. 개요
이 프로그램은 Java 기반의 SMTP(메일 전송 프로토콜) 클라이언트를 구현한 것으로, SSL(Secure Sockets Layer) 기반의 Socket Programming을 사용하여 Gmail SMTP 서버를 통해 메일을 전송하는 콘솔 애플리케이션이다. Java 내장 라이브러리만을 활용하여 구현되었으며, **SMTP 기본 프로토콜 명령어(HELO, AUTH, MAIL FROM, RCPT TO, DATA, QUIT 등)** 를 직접 주고받는다.
<br><br>

### 2. 주요 기능 및 동작 과정


1. SMTP 서버 연결

   - `smtp.gmail.com`을 SSL을 사용하여 `465 포트` 로 연결 (Socket + SSLSocket)

   - 서버 응답 status code `220`을 확인하여 연결이 정상적으로 이루어졌는지 확인


2. HELO 명령 전송
   

   - `HELO smtp.gmail.com` 명령을 전송하여 SMTP 서버와 초기 통신을 설정

   - 서버 응답 status code `250`을 확인하여 정상작동 확인

3. SMTP 인증 (AUTH LOGIN)
   
   - `AUTH LOGIN` 명령을 전송하여 로그인 인증 시작

   - 아이디와 비밀번호를 Base64 인코딩하여 전송

   - Gmail SMTP 서버의 응답 status code `235`를 확인하여 최종 사용자 인증 성공 여부 판단
   
4. 메일 송·수신자 지정
   
   - `MAIL FROM:<송신자 이메일>` 명령으로 발신자 정보 전달

   - `RCPT TO:<수신자 이메일>` 명령으로 수신자 정보 전달
  
   - Gmail SMTP 서버의 응답 status code `250`를 확인하여 정상 수신 여부 확인
   
5. 메일 본문 전송
   
   - `DATA 명령`을 전송하여 [본문](#본문-내용-설명) 입력 시작

   - 제목, 발신자, 수신자, 본문 내용을 SMTP 포맷에 맞춰 작성하여 전송

   - 본문 끝을 `.(마침표)`로 표시하여 서버가 인식하도록 함
  
   - Gmail SMTP 서버의 응답 status code `354`를 확인하여 본문 내용 정상 수신 여부 확인
   
6. 메일 전송 완료 및 연결 종료
   
   - `QUIT 명령`을 전송하여 SMTP 세션 종료

   - 서버 응답이 `221`인지 확인하여 정상적으로 종료되었는지 검증


<br><br><hr>


### 통신 내용 및 Status Code
![image](https://github.com/user-attachments/assets/b9a1436e-7565-4632-bd6a-6a43b1cb2005)

**응답값 해석 (base64로 인코딩됨)**
- d9443c01a7336-21a9f10de6dsm64283235ad.17 = 세션 또는 트랜잭션 ID
- VXNlcm5hbWU6 = Username:
- UGFzc3dvcmQ6 = Password:
- 1736842911 = Google SMTP 서버의 메시지 ID 또는 상태 추적용 값


### 본문 내용 설명
```
content = "Subject: SMTP로 메일보내기\n"
                    + "From: 송신자 <qwerie8899@gmail.com>\r\n"
                    + "To: 수신자 <qwerie8899@gmail.com>\n"
                    + "Content-Type: text/plain; charset=utf-8\n"
                    + "Hello, this is SMTP mail test.\n"
                    + "안녕하세요. 본 메일은 SMTP로 보내진 메일입니다.\n"
```
- 위 메일내용에서 Subject, From, To, Content-Type 등의 서식은 안넣어줘도 메일전송에는 무방합니다.

```
String id = Base64.getEncoder().encodeToString("your_own_id".getBytes());
String password = Base64.getEncoder().encodeToString("your_own_pw".getBytes()); 
```
- 본인의 구글 아이디와 발급받은 앱 비밀번호를 넣어주면 됩니다.
