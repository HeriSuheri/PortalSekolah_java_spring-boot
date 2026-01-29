package com.example.portal.dto.ppdb;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.portal.model.enums.JenisKelamin;
import com.example.portal.model.enums.StatusPembayaran;
import com.example.portal.model.enums.StatusValidasi;

public class UpdatePpdbRegistrationRequest {
    private String nama;
    private LocalDate tanggalLahir;
    private String alamat;
    private String noHandphone;
    private String email;

    private BigDecimal jumlahDibayar;
    private StatusValidasi status; // menunggu validasi, diterima, ditolak
    private StatusPembayaran statusPembayaran; // menunggu pembayaran, lunas, belum lunas
    private String catatanValidasi; // opsional, catatan admin
    private Long validatedByAdminId; // id admin yang memverifikasi
    private Boolean isSendEmail;
    private JenisKelamin jenisKelamin;
    private String namaAyah;
    private String namaIbu;

    public StatusValidasi getStatus() {
        return status;
    }

    public void setStatus(StatusValidasi status) {
        this.status = status;
    }

    public StatusPembayaran getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(StatusPembayaran statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public String getCatatanValidasi() {
        return catatanValidasi;
    }

    public void setCatatanValidasi(String catatanValidasi) {
        this.catatanValidasi = catatanValidasi;
    }

    public Long getValidatedByAdminId() {
        return validatedByAdminId;
    }

    public void setValidatedByAdminId(Long validatedByAdminId) {
        this.validatedByAdminId = validatedByAdminId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public String getNoHandphone() {
        return noHandphone;
    }

    public void setNoHandphone(String noHandphone) {
        this.noHandphone = noHandphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJumlahDibayar(BigDecimal jumlahDibayar) {
        this.jumlahDibayar = jumlahDibayar;
    }

    public BigDecimal getJumlahDibayar() {
        return jumlahDibayar;
    }

    public Boolean getIsSendEmail() {
        return isSendEmail;
    }

    public void setIsSendEmail(Boolean isSendEmail) {
        this.isSendEmail = isSendEmail;
    }

    public JenisKelamin getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(JenisKelamin jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
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

}