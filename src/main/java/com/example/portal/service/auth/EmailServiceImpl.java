package com.example.portal.service.auth;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendResetLink(String toEmail, String token) {
        String resetUrl = "http://localhost:3000/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Reset Password Portal Sekolah");
        message.setText("Klik link berikut untuk reset password Anda:\n" + resetUrl + "\n\nLink berlaku selama 1 jam.");

        mailSender.send(message);
    }
}
