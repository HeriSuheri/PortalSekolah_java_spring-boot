package com.example.portal.repository.siswa;

import com.example.portal.model.Siswa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    Page<Siswa> findByClassroomId(Long classroomId, Pageable pageable);

    @Query("SELECT s FROM Siswa s " +
            "WHERE s.classroom.id = :classroomId " +
            "AND LOWER(s.nama) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Siswa> searchByClassroomAndNama(Long classroomId, String keyword, Pageable pageable);
}