package com.example.portal.dto.siswa;

import java.time.LocalDate;

public class SiswaDTO {
    private Long id;
    private String nama;
    private String nis;
    private LocalDate tanggalLahir;
    private String alamat;
    private String namaAyah;
    private String namaIbu;
    private String noHandphone;
    private Long classroomId;
    private String classroomName;
    private String email; // dari tabel users
    private String fotoUrl; // dari tabel users

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getNis() {
        return nis;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setNamaAyah(String namaAyah) {
        this.namaAyah = namaAyah;
    }

    public String getNamaAyah() {
        return namaAyah;
    }

    public void setNamaIbu(String namaIbu) {
        this.namaIbu = namaIbu;
    }

    public String getNamaIbu() {
        return namaIbu;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setNoHandphone(String noHandphone) {
        this.noHandphone = noHandphone;
    }

    public String getNoHandphone() {
        return noHandphone;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

}