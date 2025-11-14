package com.example.portal.dto.auth;

public class ForgotPasswordRequest {
    private String nomorInduk;
    private String email;


    public void setNomorInduk(String nomorInduk) {
        this.nomorInduk = nomorInduk;
    }

    public String getNomorInduk() {
        return nomorInduk;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}