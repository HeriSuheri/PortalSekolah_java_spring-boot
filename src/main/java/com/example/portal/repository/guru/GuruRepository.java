package com.example.portal.repository.guru;

import com.example.portal.model.Guru;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GuruRepository extends JpaRepository<Guru, Long> {
    Optional<Guru> findByNip(String nip);

    boolean existsByNip(String nip);

    Page<Guru> findByNamaContainingIgnoreCase(
            String nama, Pageable pageable);

}
