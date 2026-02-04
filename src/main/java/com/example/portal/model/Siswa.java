package com.example.portal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.example.portal.model.enums.JenisKelamin;
import com.example.portal.model.enums.StatusPembayaran;
import com.example.portal.model.enums.StatusValidasi;

@Entity
@Table(name = "siswa")
public class Siswa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nama;

    @Column(unique = true, nullable = false, length = 50)
    private String nis;

    @Column(name = "tanggal_lahir", nullable = false)
    private LocalDate tanggalLahir;

    @Column(columnDefinition = "TEXT")
    private String alamat;

    @Column(name = "nama_ayah", length = 255)
    private String namaAyah;

    @Column(name = "nama_ibu", length = 255)
    private String namaIbu;

    @Column(name = "no_handphone", length = 20)
    private String noHandphone;

    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_kelamin", nullable = true) // pakai true kalau boleh kosong
    private JenisKelamin jenisKelamin;

    // Relasi ke Classroom (banyak siswa bisa di satu kelas)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;

    // Relasi ke User (satu siswa punya satu akun user)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppdb_registration_id")
    private PpdbRegistration ppdbRegistration;

    //
    // Status siswa: AKTIF, BERHENTI, LULUS
    @Column(name = "status_siswa", length = 20, nullable = false)
    private String statusSiswa = "AKTIF";

    // Flag login aktif/tidak
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Flag arsip (khusus berhenti sementara)
    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived = false;

    // Catatan alasan berhenti
    @Column(columnDefinition = "TEXT")
    private String catatan;

    // Audit trail
    @Column(name = "archived_at")
    private java.time.LocalDateTime archivedAt;

    @Column(name = "restored_at")
    private java.time.LocalDateTime restoredAt;

    @Column(name = "graduated_at")
    private java.time.LocalDateTime graduatedAt;

    // Tahun angkatan (misalnya 2026)
    @Column(name = "angkatan", nullable = true)
    private Integer angkatan;

    // Audit umum
    @Column(name = "created_at", nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private java.time.LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = java.time.LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }

    @Column(name = "tahun_berhenti")
    private Integer tahunBerhenti;
    //

    // Constructors
    public Siswa() {
    }

    public Siswa(String nama, String nis, LocalDate tanggalLahir,
            String alamat, String namaAyah, String namaIbu, String noHandphone, JenisKelamin jenisKelamin,
            Classroom classroom, User user, PpdbRegistration ppdbRegistration) {
        this.nama = nama;
        this.nis = nis;
        this.tanggalLahir = tanggalLahir;
        this.alamat = alamat;
        this.namaAyah = namaAyah;
        this.namaIbu = namaIbu;
        this.noHandphone = noHandphone;
        this.jenisKelamin = jenisKelamin;
        this.classroom = classroom;
        this.user = user;
        this.ppdbRegistration = ppdbRegistration;
    }

    // Getters and Setters
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

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PpdbRegistration getPpdbRegistration() {
        return ppdbRegistration;
    }

    public void setPpdbRegistration(PpdbRegistration ppdbRegistration) {
        this.ppdbRegistration = ppdbRegistration;
    }

    public JenisKelamin getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(JenisKelamin jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getStatusSiswa() {
        return statusSiswa;
    }

    public void setStatusSiswa(String statusSiswa) {
        this.statusSiswa = statusSiswa;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public java.time.LocalDateTime getArchivedAt() {
        return archivedAt;
    }

    public void setArchivedAt(java.time.LocalDateTime archivedAt) {
        this.archivedAt = archivedAt;
    }

    public java.time.LocalDateTime getRestoredAt() {
        return restoredAt;
    }

    public void setRestoredAt(java.time.LocalDateTime restoredAt) {
        this.restoredAt = restoredAt;
    }

    public java.time.LocalDateTime getGraduatedAt() {
        return graduatedAt;
    }

    public void setGraduatedAt(java.time.LocalDateTime graduatedAt) {
        this.graduatedAt = graduatedAt;
    }

    public Integer getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(Integer angkatan) {
        this.angkatan = angkatan;
    }

    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public java.time.LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.time.LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getTahunBerhenti() {
        return tahunBerhenti;
    }

    public void setTahunBerhenti(Integer tahunBerhenti) {
        this.tahunBerhenti = tahunBerhenti;
    }

}