package com.example.portal.model;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "jadwal")
public class Jadwal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hari;

    private LocalTime jamMulai;
    private LocalTime jamSelesai;

    @ManyToOne
    @JoinColumn(name = "mata_pelajaran_id")
    private MataPelajaran mataPelajaran;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
}
