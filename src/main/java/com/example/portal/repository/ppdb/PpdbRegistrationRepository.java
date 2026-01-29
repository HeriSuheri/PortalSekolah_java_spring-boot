package com.example.portal.repository.ppdb;

import java.util.List;
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
        // @Query("SELECT s FROM PpdbRegistration s " +
        // "WHERE LOWER(s.nama) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        // " OR LOWER(s.noPendaftaran) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        // Page<PpdbRegistration> searchByKeyword(@Param("keyword") String keyword,
        // Pageable pageable);

        @Query("SELECT COUNT(r) FROM PpdbRegistration r WHERE YEAR(r.createdAt) = :year")
        long countByCreatedAtYear(@Param("year") int year);

        @Query(value = "SELECT no_pendaftaran FROM ppdb_registration " +
                        "WHERE EXTRACT(YEAR FROM created_at) = :year " +
                        "ORDER BY no_pendaftaran DESC LIMIT 1", nativeQuery = true)
        String findLastNoPendaftaranByYear(@Param("year") int year);

        List<PpdbRegistration> findByTahunPpdb(int tahun);

        Page<PpdbRegistration> findByTahunPpdb(int tahun, Pageable pageable);

        @Query("SELECT r FROM PpdbRegistration r " +
                        "WHERE r.tahunPpdb = :tahun AND " +
                        "(LOWER(r.nama) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        "OR LOWER(r.noPendaftaran) LIKE LOWER(CONCAT('%', :keyword, '%')))")
        Page<PpdbRegistration> searchByKeywordAndYear(@Param("keyword") String keyword,
                        @Param("tahun") int tahun,
                        Pageable pageable);
}
