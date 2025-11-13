package com.example.portal.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAdminRequest {

    @NotBlank(message = "Email wajib diisi")
    @Email(message = "Format email tidak valid")
    private String email;

    // @NotBlank(message = "Password wajib diisi")
    // private String password;

    @NotBlank(message = "Nama wajib diisi")
    private String nama;

    @NotBlank(message = "Nomor Induk wajib diisi")
    private String nomorInduk;

    @NotBlank(message = "Tanggal lahir wajib diisi")
    private String tanggalLahir; // Format: yyyy-MM-dd
}

