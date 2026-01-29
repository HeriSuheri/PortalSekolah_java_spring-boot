package com.example.portal.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "classroom")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String name;

    // Relasi ke grade level
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_level_id", nullable = false)
    private GradeLevel gradeLevel;

    // 🔑 Wali kelas (FK ke guru)
    @OneToOne
    @JoinColumn(name = "wali_guru_id")
    private Guru waliGuru;

    // Relasi ke siswa (FK classroom_id di tabel siswa)
    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Siswa> siswaList = new ArrayList<>();

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Constructors
    public Classroom() {
    }

    public Classroom(String name, GradeLevel gradeLevel) {
        this.name = name;
        this.gradeLevel = gradeLevel;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Guru getWaliGuru() {
        return waliGuru;
    }

    public void setWaliGuru(Guru waliGuru) {
        this.waliGuru = waliGuru;
    }

    public List<Siswa> getSiswaList() {
        return siswaList;
    }

    public void setSiswaList(List<Siswa> siswaList) {
        this.siswaList = siswaList;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}