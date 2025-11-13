package com.example.portal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdateProfileRequest {
    // private String nama;
    // private String role;
    // private String tanggalLahir;
    @NotBlank(message = "Nama tidak boleh kosong")
    private String nama;

    @NotBlank(message = "Role tidak boleh kosong")
    private String role;

    @NotBlank(message = "Tanggal lahir tidak boleh kosong")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Format tanggal lahir harus YYYY-MM-DD")
    private String tanggalLahir;

    // Getters & Setters
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
}