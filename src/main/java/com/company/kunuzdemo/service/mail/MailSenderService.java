package com.company.kunuzdemo.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendVerificationCode(String email, String verificationCode) {
        try {
            String message = "This is your verification code: " + verificationCode;
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setText(message);
            mailSender.send(simpleMailMessage);
            return "Verification code sent successfully";
        } catch (Exception e) {
            return "Email to send the verification code";
        }

    }

}