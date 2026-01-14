package com.example.portal.repository.ppdb;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.portal.model.PpdbRegistration;

public interface PpdbRegistrationRepository extends JpaRepository<PpdbRegistration, Long> {
    Optional<PpdbRegistration> findByNoPendaftaran(String noPendaftaran);

    Optional<PpdbRegistration> findByEmail(String email);

    @Query("SELECT COUNT(r) FROM PpdbRegistration r WHERE YEAR(r.createdAt) = :year")
    long countByCreatedAtYear(@Param("year") int year);
}
