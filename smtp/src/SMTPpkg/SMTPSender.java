package SMTPpkg;

import java.net.*;
import java.io.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.util.Base64;

public class SMTPSender {
    public static void sendMail(String smtpServer, String sender, String recipient, String content) throws Exception {
        Socket socket = new Socket(smtpServer, 465); //SMTP 평문기본포트=25 // SMTP+SSL=465 // SMTP+TLS=587
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) factory.createSocket(socket,
                socket.getInetAddress().getHostName(),
                socket.getPort(), true); 	// true: autoClose - close the underlying socket when this socket is closed

        BufferedReader br = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
        PrintWriter pw = new PrintWriter(sslSocket.getOutputStream(), true);

        System.out.println("서버에 연결되었습니다.");

        String line = br.readLine();
        System.out.println("답변:" + line);
        if (!line.startsWith("220"))
            throw new Exception("SMTP 서버가 아닙니다:" + line);

        System.out.println("[HELO 명령을 전송하겠습니다.]");
        pw.println("HELO smtp.gmail.com");  // mydomain.name 도메인 이름을 전달해준다.
        line = br.readLine();
        System.out.println("답변:" + line);
        if (!line.startsWith("250"))
            throw new Exception("HELO 명령에서 실패했습니다:" + line);

        System.out.println("[로그인을 시도하겠습니다.]");
        pw.println("AUTH LOGIN"); // auth plain + "\id\pw"
        line = br.readLine();
        System.out.println("답변:" + line);
        if (!line.startsWith("334"))
            throw new Exception("로그인 시도에서 실패했습니다:" + line);

        String id = Base64.getEncoder().encodeToString("qwerie8899".getBytes());
        String password1 = Base64.getEncoder().encodeToString("nchhuewhjgsawcha".getBytes());

        System.out.println("[아이디를 전송하겠습니다.]");
        pw.println(id);
        line = br.readLine();
        System.out.println("답변:" + line);
        if (!line.startsWith("334"))
            throw new Exception("아이디 전송에서 실패했습니다:" + line);
        System.out.println("[password를 전송하겠습니다.]");
        pw.println(password1);
        line = br.readLine();
        System.out.println("답변:" + line);
        if (!line.startsWith("235"))
            throw new Exception("비밀번호 전송에서 실패했습니다." + line);


        System.out.println("[MAIL FROM 명령을 전송하겠습니다.]");
        pw.println("MAIL FROM:" + sender);
        line = br.readLine();
        System.out.println("답변:" + line);
        if (!line.startsWith("250"))
            throw new Exception("MAIL FROM 명령에서 실패했습니다:" + line);

        System.out.println("[RCPT 명령을 전송하겠습니다.]");
        pw.println("RCPT TO:" + recipient);
        line = br.readLine();
        System.out.println("답변:" + line);
        if (!line.startsWith("250"))
            throw new Exception("RCPT TO 명령에서 실패했습니다:" + line);

        System.out.println("[DATA 명령을 전송하겠습니다.]");
        pw.println("DATA");
        line = br.readLine();
        System.out.println("답변:" + line);
        if (!line.startsWith("354"))
            throw new Exception("DATA 명령에서 실패했습니다:" + line);



        System.out.println("[본문을 전송하겠습니다.]");
        pw.println(content);
        pw.println(".");
        line = br.readLine();
        System.out.println("답변:" + line);
        if (!line.startsWith("250"))
            throw new Exception("DATA(본문) 전송에서 실패했습니다:" + line);


        System.out.println("접속을 종료합니다.");
        pw.println("quit");
        line = br.readLine();
        System.out.println("답변:" + line);
        if (!line.startsWith("221"))
            throw new Exception("접속 종료에 실패하였습니다:" + line);

        pw.close();
        br.close();
    }

    public static void main(String args[]) {
        try {
            String content = new String();
            content = "Subject: 찐막\n"
                    + "From: 전유진 <qwerie8899@gmail.com>\r\n"
                    + "To: 이윤수 <lys201108@naver.com>\n"
                    + "Content-Type: text/plain; charset=utf-8\n"
                    + "Hello, this is smtp mail test.\n"
                    + "안녕하세요\n";
            SMTPSender.sendMail("smtp.gmail.com", "<qwerie8899@gmail.com>", "<lys201108@naver.com>", content);
            System.out.println("==========================");
            System.out.println("메일이 전송되었습니다.");

        } catch (Exception e) {
            System.out.println("==========================");
            System.out.println("메일이 발송되지 않았습니다.");
            System.out.println(e.toString());
        }
    }
}
