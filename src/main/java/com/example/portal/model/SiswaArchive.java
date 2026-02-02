package com.example.portal.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "siswa_archive")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiswaArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "siswa_id", nullable = false)
    private Long siswaId;

    @Column(name = "nis", length = 50)
    private String nis;

    @Column(name = "nama", nullable = false, length = 255)
    private String nama;

    @Column(name = "tanggal_lahir")
    private LocalDate tanggalLahir;

    @Column(name = "alamat", columnDefinition = "text")
    private String alamat;

    @Column(name = "no_handphone", length = 20)
    private String noHandphone;

    @Column(name = "nama_ayah", length = 255)
    private String namaAyah;

    @Column(name = "nama_ibu", length = 255)
    private String namaIbu;

    @Column(name = "classroom_id")
    private Long classroomId;

    @Column(name = "ppdb_registration_id")
    private Long ppdbRegistrationId;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "tahun_lulus")
    private Integer tahunLulus;

    @Column(name = "archived_at", columnDefinition = "timestamp")
    private LocalDateTime archivedAt = LocalDateTime.now();

    @Column(name = "restored_at", columnDefinition = "timestamp")
    private LocalDateTime restoredAt;

    @Column(name = "tahun_berhenti")
    private Integer tahunBerhenti;
}