package com.example.portal.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nomor Induk: bisa NIS (siswa) atau NIP (guru/admin)
    @Column(name = "nomor_induk", unique = true, nullable = false)
    private String nomorInduk;

    // Tanggal lahir untuk login awal
    @Column(name = "tanggal_lahir", nullable = false)
    private LocalDate tanggalLahir;

    // Password bisa null saat login awal, diisi setelah ubah password
    @Column(nullable = true)
    private String password;

    // Role: SISWA, GURU, ADMIN
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "nama", nullable = false)
    private String nama; // ← kolom baru

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = true)
    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}