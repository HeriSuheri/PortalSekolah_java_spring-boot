package com.example.portal.dto.admin;

import lombok.Data;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

@Data
public class UpdateAdminRequest {
    private String nomorInduk;
    private String nama;
    private String email;
    // private LocalDate tanggalLahir;
    // private String tanggalLahir; // Format: yyyy-MM-dd
    @NotBlank(message = "Tanggal lahir wajib diisi")
    private String tanggalLahir;
    private Boolean isActive;
}