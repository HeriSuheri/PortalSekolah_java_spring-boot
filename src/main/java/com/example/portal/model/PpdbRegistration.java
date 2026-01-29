package com.example.portal.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.portal.model.enums.JenisKelamin;
import com.example.portal.model.enums.StatusPembayaran;
import com.example.portal.model.enums.StatusValidasi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ppdb_registration")
public class PpdbRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "no_pendaftaran", unique = true, nullable = false)
    private String noPendaftaran;

    private String nama;

    private LocalDate tanggalLahir;

    private String alamat;

    private String noHandphone;

    @Column(unique = true, nullable = false)
    private String email;

    // // status validasi: menunggu validasi, diterima, ditolak
    // private String status = "menunggu validasi";

    // // status pembayaran: menunggu pembayaran, lunas, belum lunas
    // private String statusPembayaran = "menunggu pembayaran";

    @Enumerated(EnumType.STRING)
    private StatusValidasi status = StatusValidasi.MENUNGGU_VALIDASI;

    @Enumerated(EnumType.STRING)
    private StatusPembayaran statusPembayaran = StatusPembayaran.MENUNGGU_PEMBAYARAN;

    private BigDecimal jumlahDibayar = BigDecimal.ZERO;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "has_classroom", nullable = false)
    private Boolean hasClassroom = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_kelamin", nullable = true)
    private JenisKelamin jenisKelamin;

    @Column(name = "nama_ayah")
    private String namaAyah;

    @Column(name = "nama_ibu")
    private String namaIbu;

    @Column(name = "tahun_ppdb", nullable = false)
    private Integer tahunPpdb;

    // validasi admin
    private Long validatedByAdminId;
    private LocalDateTime validatedAt;
    private String catatanValidasi;

    // --- Getter & Setter ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BigDecimal getJumlahDibayar() {
        return jumlahDibayar;
    }

    public void setJumlahDibayar(BigDecimal jumlahDibayar) {
        this.jumlahDibayar = jumlahDibayar;
    }

    public Long getValidatedByAdminId() {
        return validatedByAdminId;
    }

    public void setValidatedByAdminId(Long validatedByAdminId) {
        this.validatedByAdminId = validatedByAdminId;
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

    public Integer getTahunPpdb() {
        return tahunPpdb;
    }

    public void setTahunPpdb(Integer tahunPpdb) {
        this.tahunPpdb = tahunPpdb;
    }

}