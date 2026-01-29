package com.example.portal.dto.ppdb;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.portal.model.enums.JenisKelamin;
import com.example.portal.model.enums.StatusPembayaran;
import com.example.portal.model.enums.StatusValidasi;

public class PpdbRegistrationResponse {
    private Long id;
    private String noPendaftaran;
    private String nama;
    private String email;
    private LocalDate tanggalLahir;
    private String alamat;
    private String noHandphone;
    private StatusValidasi status;
    private StatusPembayaran statusPembayaran;
    private BigDecimal jumlahDibayar;
    private LocalDateTime validatedAt;
    private String catatanValidasi;
    private Long validatedByAdminId;
    private Boolean hasClassroom;
    private JenisKelamin jenisKelamin;
    private String namaAyah;
    private String namaIbu;

    public String getNoPendaftaran() {
        return noPendaftaran;
    }

    public void setNoPendaftaran(String noPendaftaran) {
        this.noPendaftaran = noPendaftaran;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public BigDecimal getJumlahDibayar() {
        return jumlahDibayar;
    }

    public void setJumlahDibayar(BigDecimal jumlahDibayar) {
        this.jumlahDibayar = jumlahDibayar;
    }

    public LocalDateTime getValidatedAt() {
        return validatedAt;
    }

    public void setValidatedAt(LocalDateTime validatedAt) {
        this.validatedAt = validatedAt;
    }

    public String getCatatanValidasi() {
        return catatanValidasi;
    }

    public void setCatatanValidasi(String catatanValidasi) {
        this.catatanValidasi = catatanValidasi;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValidatedByAdminId() {
        return validatedByAdminId;
    }

    public void setValidatedByAdminId(Long validatedByAdminId) {
        this.validatedByAdminId = validatedByAdminId;
    }

    public Boolean getHasClassroom() {
        return hasClassroom;
    }

    public void setHasClassroom(Boolean hasClassroom) {
        this.hasClassroom = hasClassroom;
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