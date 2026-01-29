package com.example.portal.dto.siswa;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.portal.model.enums.JenisKelamin;
import com.example.portal.model.enums.StatusPembayaran;
import com.example.portal.model.enums.StatusValidasi;

public class UpdateSiswaRequest {
    private String nama;
    private Long id;
    private String nis;
    private LocalDate tanggalLahir;
    private String alamat;
    private String namaAyah;
    private String namaIbu;
    private String noHandphone;
    private JenisKelamin jenisKelamin;
    private Long classroomId;
    private String email;
    private Long ppdbRegistrationId;

    // getters & setters

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaAyah() {
        return namaAyah;
    }

    public void setNamaAyah(String namaAyah) {
        this.namaAyah = namaAyah;
    }

    public String getNamaIbu() {
        return namaIbu;
    }

    public void setNamaIbu(String namaIbu) {
        this.namaIbu = namaIbu;
    }

    public String getNoHandphone() {
        return noHandphone;
    }

    public void setNoHandphone(String noHandphone) {
        this.noHandphone = noHandphone;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Long getPpdbRegistrationId() {
        return ppdbRegistrationId;
    }

    public void setPpdbRegistrationId(Long ppdbRegistrationId) {
        this.ppdbRegistrationId = ppdbRegistrationId;
    }

    public JenisKelamin getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(JenisKelamin jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

}