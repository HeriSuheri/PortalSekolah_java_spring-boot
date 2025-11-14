package com.example.portal.service.auth;

public interface AuthServiceForgot {
    void sendResetPasswordLink(String nomorInduk, String email);

    void resetPassword(String token, String newPassword);

}
