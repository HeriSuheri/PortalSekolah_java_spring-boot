package com.example.portal.dto.siswa.berhenti;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.portal.model.enums.JenisKelamin;

public class BerhentiDTO {
    private Long id;
    private String nama;
    private String nis;
    private LocalDate tanggalLahir;
    private String noHandphone;
    private JenisKelamin jenisKelamin;
    private Long classroomId;
    private String classroomName;
    private String email;
    // private java.time.LocalDateTime archivedAt;
    private String archivedDateOnly;
    private String catatan;
    private String statusSiswa;
    private String graduatedAt;

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

    public String getNoHandphone() {
        return noHandphone;
    }

    public void setNoHandphone(String noHandphone) {
        this.noHandphone = noHandphone;
    }

    public JenisKelamin getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(JenisKelamin jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getStatusSiswa() {
        return statusSiswa;
    }

    public void setStatusSiswa(String statusSiswa) {
        this.statusSiswa = statusSiswa;
    }

    public String getArchivedDateOnly() {
        return archivedDateOnly;
    }

    public void setArchivedDateOnly(String archivedDateOnly) {
        this.archivedDateOnly = archivedDateOnly;
    }

    public String getGraduatedAt() {
        return graduatedAt;
    }

    public void setGraduatedAt(String graduatedAt) {
        this.graduatedAt = graduatedAt;
    }

}
