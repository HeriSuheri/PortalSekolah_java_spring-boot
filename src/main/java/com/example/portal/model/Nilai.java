package com.example.portal.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "nilai")
public class Nilai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "siswa_id")
    private Siswa siswa;

    @ManyToOne
    @JoinColumn(name = "mata_pelajaran_id")
    private MataPelajaran mataPelajaran;

    private BigDecimal nilaiAngka;
    private String keterangan;
}
