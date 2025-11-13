package com.example.portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mata_pelajaran")
public class MataPelajaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;

    @ManyToOne
    @JoinColumn(name = "grade_level_id")
    private GradeLevel gradeLevel;

    @ManyToOne
    @JoinColumn(name = "guru_id")
    private Guru guru;

    // Constructors
    public MataPelajaran() {
    }

    public MataPelajaran(String nama, GradeLevel gradeLevel, Guru guru) {
        this.nama = nama;
        this.gradeLevel = gradeLevel;
        this.guru = guru;
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

    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Guru getGuru() {
        return guru;
    }

    public void setGuru(Guru guru) {
        this.guru = guru;
    }
}