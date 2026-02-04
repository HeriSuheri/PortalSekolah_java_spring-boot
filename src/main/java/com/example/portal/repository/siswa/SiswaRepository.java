package com.example.portal.repository.siswa;

import com.example.portal.model.Siswa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiswaRepository extends JpaRepository<Siswa, Long> {

        boolean existsByNis(String nip);

        // Ambil semua siswa berdasarkan classroom
        List<Siswa> findByClassroomId(Long classroomId);

        // Search siswa berdasarkan nama atau nis
        @Query("SELECT s FROM Siswa s " +
                        "WHERE LOWER(s.nama) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        "   OR LOWER(s.nis) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        Page<Siswa> searchByKeyword(String keyword, Pageable pageable);

        // Page<Siswa> findByClassroomId(Long classroomId, Pageable pageable);

        // @Query("SELECT s FROM Siswa s " +
        // "WHERE s.classroom.id = :classroomId " +
        // "AND LOWER(s.nama) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        // Page<Siswa> searchByClassroomAndNama(Long classroomId, String keyword,
        // Pageable pageable);
        Page<Siswa> findByClassroomIdAndStatusSiswa(Long classroomId, String statusSiswa, Pageable pageable);

        Page<Siswa> findByClassroomIdAndNamaContainingIgnoreCaseAndStatusSiswa(
                        Long classroomId, String nama, String statusSiswa, Pageable pageable);

        List<Siswa> findByStatusSiswa(String statusSiswa);

        // Page<Siswa> findByClassroomIdAndStatusSiswa(Long classroomId, String
        // statusSiswa, Pageable pageable);

        // long countByClassroomId(Long classroomId);
        long countByClassroomIdAndStatusSiswa(Long classroomId, String statusSiswa);

        // MENU ARSIP BERHENTI
        Page<Siswa> findByStatusSiswaAndTahunBerhenti(String statusSiswa, Integer tahunBerhenti, Pageable pageable);

        Page<Siswa> findByStatusSiswaAndNamaContainingIgnoreCaseAndTahunBerhenti(
                        String statusSiswa, String nama, Integer tahunBerhenti, Pageable pageable);

        // MENU ARSIP LULUS
        Page<Siswa> findByStatusSiswaAndAngkatan(String statusSiswa, Integer angkatan, Pageable pageable);

        Page<Siswa> findByStatusSiswaAndNamaContainingIgnoreCaseAndAngkatan(
                        String statusSiswa, String nama, Integer angkatan, Pageable pageable);

        // untuk create NIS
        @Query("SELECT MAX(s.nis) FROM Siswa s WHERE s.nis LIKE CONCAT(:tahun, '%')")
        String findLastNisByYear(@Param("tahun") String tahun);
}