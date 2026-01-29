package com.example.portal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "guru")
public class Guru {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nama;

    @Column(unique = true, nullable = false)
    private String nip;

    @Column(name = "tanggal_lahir", nullable = false)
    private LocalDate tanggalLahir;

    // 🔑 Relasi ke tabel users
    // @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Constructors
    public Guru() {
    }

    public Guru(String nama, String nip, LocalDate tanggalLahir, User user) {
        this.nama = nama;
        this.nip = nip;
        this.tanggalLahir = tanggalLahir;
        this.user = user;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}