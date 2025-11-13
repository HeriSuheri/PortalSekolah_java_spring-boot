package com.example.portal.repository;

import com.example.portal.model.Role;
import com.example.portal.model.User;

import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    // Cari user berdasarkan nomor induk (NIS/NIP)
    Optional<User> findByNomorInduk(String nomorInduk);

    // Cari user berdasarkan nomor induk dan tanggal lahir (untuk login awal)
    Optional<User> findByNomorIndukAndTanggalLahir(String nomorInduk, LocalDate tanggalLahir);

    // Cek apakah nomor induk sudah dipakai
    boolean existsByNomorInduk(String nomorInduk);

    // Filter user berdasarkan role
    List<User> findByRole(Role role);
    Page<User> findByRole(Role role, Pageable pageable);
}