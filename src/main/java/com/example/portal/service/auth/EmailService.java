package com.example.portal.service.auth;

public interface EmailService {
    void sendResetLink(String toEmail, String token);
}
