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
        message.setText(
                "Klik link berikut untuk reset password Anda:\n" + resetUrl + "\n\nLink berlaku selama 2 Menit.");

        mailSender.send(message);
    }

    // email ppdb
    public void sendRegistrationEmail(String to, String noPendaftaran, String nama) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Bukti Registrasi PPDB");
        message.setText("Halo " + nama + ",\n\n"
                + "Terima kasih sudah mendaftar PPDB.\n"
                + "Nomor pendaftaran Anda adalah: " + noPendaftaran + "\n\n"
                + "Silakan datang ke sekolah dengan membawa berkas sesuai ketentuan.\n\n"
                + "Salam,\nAdmin PPDB");
        mailSender.send(message);
    }

}
