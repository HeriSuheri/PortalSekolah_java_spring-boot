package com.example.portal.repository.ppdb;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.portal.model.PpdbRegistration;
import com.example.portal.model.Siswa;
import com.example.portal.model.enums.StatusValidasi;

public interface PpdbRegistrationRepository extends JpaRepository<PpdbRegistration, Long> {
    Optional<PpdbRegistration> findByNoPendaftaran(String noPendaftaran);

    boolean existsByNoPendaftaran(String noPendaftaran);

    Optional<PpdbRegistration> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndStatus(String email, StatusValidasi status);

    // @Query("SELECT s FROM PpdbRegistration s " +
    // "WHERE LOWER(s.nama) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    // Page<PpdbRegistration> searchByKeyword(String keyword, Pageable pageable);
    @Query("SELECT s FROM PpdbRegistration s " +
            "WHERE LOWER(s.nama) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(s.noPendaftaran) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<PpdbRegistration> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(r) FROM PpdbRegistration r WHERE YEAR(r.createdAt) = :year")
    long countByCreatedAtYear(@Param("year") int year);

    @Query("SELECT r.noPendaftaran FROM PpdbRegistration r " +
            "WHERE YEAR(r.createdAt) = :year " +
            "ORDER BY r.noPendaftaran DESC LIMIT 1")
    String findLastNoPendaftaranByYear(@Param("year") int year);
}
