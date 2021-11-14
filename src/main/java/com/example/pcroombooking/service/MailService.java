package com.example.pcroombooking.service;

import com.example.pcroombooking.OuterProperties.EmailProperties;
import com.example.pcroombooking.domain.Cryptogram;
import com.example.pcroombooking.dto.EmailSendRequest;
import com.example.pcroombooking.dto.EmailSendResponse;
import com.example.pcroombooking.exception.SuperException;
import com.example.pcroombooking.exception.exceptionType.CustomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileReader;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final CryptogramService cryptogramService;
    private final EmailProperties emailProperties;

    public EmailSendResponse gmailSend(EmailSendRequest emailSendRequest) {

        // 외부 properties 에서 email, password 가져오기
        emailProperties.initEmailProperties();

        String user = emailProperties.getEmail(); // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
        String password = emailProperties.getPassword();   // 패스워드
        String targetEmail = emailSendRequest.getUserEmail();

        System.out.println("user1!!!!!!!" + user);
        System.out.println("password1!!!!!!!" + password);

        // SMTP 서버 정보를 설정한다.
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));

            //수신자메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(targetEmail));

            // Subject
            message.setSubject("Mail send test"); //메일 제목을 입력

            // Random 문자열 생성
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 8;
            Random random = new Random();
            String cryptogram = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString().toUpperCase();
            System.out.println(cryptogram);

            // Cryptogram db에 저장
            if(!cryptogramService.addCryptogram(cryptogram, targetEmail).getTargetEmail().equals(targetEmail)) {
                System.out.println("Cryptogram 저장 실패!!!!!!!!!!");
                throw new SuperException(CustomExceptionType.CRYPTOGRAM_SAVE_EXCEPTION);
            }

            // Text
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("안녕하세요!\n");
            stringBuffer.append(targetEmail + " 님의 인증문자열은 '" + cryptogram + "' 입니다.\n");
            stringBuffer.append("받으신 인증문자열을 인증칸에 넣어서 인증을 완료하시고 회원가입을 진행해주세요.\n");
            stringBuffer.append("감사합니다!");
            message.setText(stringBuffer.toString());    //메일 내용을 입력

            // send the message
            Transport.send(message); ////전송
            System.out.println("email sent successfully!!!");
        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return EmailSendResponse.builder()
                .resultCode(200)
                .result("Send Success")
                .message("메일을 성공적으로 발송했습니다.")
                .build();
    }

}

