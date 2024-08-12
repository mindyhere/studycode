package com.demo.studycode.service;

import com.demo.studycode.dto.MailDTO;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;

    // 메일 발송
    public String sendEmail(MailDTO dto) {

        String result = "";

        try {
            MimeMessage mimeMsg = mailSender.createMimeMessage();
            mimeMsg.addRecipient(RecipientType.TO, new InternetAddress(dto.getRecipient()));
            mimeMsg.addFrom(new InternetAddress[]{new InternetAddress(dto.getSender(), dto.getSenderName())});
            mimeMsg.setSubject(dto.getSubject(), "utf-8");
            mimeMsg.setText(dto.getMessage(), "utf-8");
            mailSender.send(mimeMsg);
            result = "success";
        } catch (Exception e) {
            e.printStackTrace();
            result = "fail";
        }

        return result;

    }

    // 임시 인증코드 생성
    public static String getTempCode() {

        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;

        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;

    }

    public MailDTO setTempCodeEmail(String email, String tempCode) {

        MailDTO mail = new MailDTO();
        mail.setSubject("[Studycode] 이메일 인증 안내");
        mail.setMessage("이메일 인증요청 확인코드가 발급되었습니다.\n인증코드 입력 후 회원가입 절차를 완료해주세요.\n\n\rCODE : " + tempCode);
        mail.setRecipient(email);
        mail.setSenderName("Notice");
        mail.setSender("notice@gmail.com");

        return mail;

    }

}
