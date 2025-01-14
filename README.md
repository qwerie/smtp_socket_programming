# smtp_socket_programming
SMTP를 [RFC5321](https://www.rfc-editor.org/rfc/rfc5321.html)과 ESMTP (Extended SMTP)기반으로 JAVA 상에서 메일 전송을 구현한 코드입니다.

## 통신 내용 및 Status Code
![image](https://github.com/user-attachments/assets/b9a1436e-7565-4632-bd6a-6a43b1cb2005)

**응답값 해석 (base64로 인코딩됨)**
- d9443c01a7336-21a9f10de6dsm64283235ad.17 = 세션 또는 트랜잭션 ID
- VXNlcm5hbWU6 = Username:
- UGFzc3dvcmQ6 = Password:
- 1736842911 = Google SMTP 서버의 메시지 ID 또는 상태 추적용 값

## 결과
![image](https://github.com/user-attachments/assets/ac8e2ff3-4da4-4b19-b05f-23e9be59cb5e)

## 코드 내용 설명
```
content = "Subject: SMTP로 메일보내기\n"
                    + "From: 전유진 <qwerie8899@gmail.com>\r\n"
                    + "To: 전유진 <qwerie8899@gmail.com>\n"
                    + "Content-Type: text/plain; charset=utf-8\n"
                    + "Hello, this is SMTP mail test.\n"
                    + "안녕하세요. 본 메일은 SMTP로 보내진 메일입니다.\n"
```
- 위 메일내용에서 Subject, From, To, Content-Type 등의 서식은 안넣어줘도 메일전송에는 무방합니다.

```
String id = Base64.getEncoder().encodeToString("your_own_id".getBytes());
String password1 = Base64.getEncoder().encodeToString("your_own_pw".getBytes()); 
```
- 본인의 구글 아이디와 발급받은 앱 비밀번호를 넣어주면 됩니다.
