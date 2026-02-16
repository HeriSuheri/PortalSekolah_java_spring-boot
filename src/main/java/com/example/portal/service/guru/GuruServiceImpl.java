package com.example.portal.service.guru;

import com.example.portal.dto.UserResponseDTO;
import com.example.portal.dto.guru.*;
import com.example.portal.exception.ResourceNotFoundException;
import com.example.portal.mapper.UserMapper;
import com.example.portal.mapper.guru.GuruMapper;
import com.example.portal.model.*;
import com.example.portal.repository.guru.GuruRepository;
import com.example.portal.repository.UserRepository;
import com.example.portal.repository.classroom.ClassroomRepository;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GuruServiceImpl implements GuruService {

    private final GuruRepository guruRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;

    public GuruServiceImpl(GuruRepository guruRepository, UserRepository userRepository,
            ClassroomRepository classroomRepository) {
        this.guruRepository = guruRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
    }

    @Override
    public GuruDTO createGuru(CreateGuruRequest request) {
        if (guruRepository.existsByNip(request.getNip())) {
            throw new IllegalArgumentException("NIP sudah digunakan");
        }

        if (userRepository.existsByNomorInduk(request.getNip())) {
            throw new IllegalArgumentException("Nomor Induk sudah digunakan di User");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email sudah digunakan di User");
        }

        User user = new User();
        user.setNomorInduk(request.getNip());
        user.setTanggalLahir(request.getTanggalLahir());
        user.setPassword(null); // first login pakai tanggal lahir
        user.setRole(Role.GURU);
        user.setNama(request.getNama());
        user.setEmail(request.getEmail());
        user.setIsActive(request.getIsActive());
        userRepository.save(user);

        Guru guru = new Guru();
        guru.setNip(request.getNip());
        guru.setNama(request.getNama());
        guru.setTanggalLahir(request.getTanggalLahir());
        guru.setIsActive(request.getIsActive());
        guru.setUser(user);
        guruRepository.save(guru);

        return GuruMapper.toDTO(guru);
    }

    @Override
    public GuruDTO updateGuru(Long id, UpdateGuruRequest request, Authentication auth) {
        // ambil username/email dari principal
        String username = auth.getName(); // ini lebih aman daripada cast
        User currentUser = userRepository.findByNomorInduk(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));

        Guru guru = guruRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guru dengan ID " + id + " tidak ditemukan"));

        // Guru tidak boleh edit
        if (currentUser.getRole() == Role.GURU) {
            throw new AccessDeniedException("Guru tidak boleh edit data");
        }

        // ✅ Validasi: kalau request ingin set isActive = false
        if (request.getIsActive() != null && !request.getIsActive()) {
            boolean masihWaliKelas = classroomRepository.existsByWaliGuru(guru);
            if (masihWaliKelas) {
                throw new IllegalStateException(
                        "Guru masih menjadi wali kelas di salah satu kelas, tidak bisa di-nonaktifkan");
            }
        }

        // Admin
        if (currentUser.getRole() == Role.ADMIN) {
            boolean isSuperAdmin = currentUser.getNomorInduk().equals("A0000001") ||
                    currentUser.getNomorInduk().equals("A0000002");

            if (isSuperAdmin) {
                // super admin boleh edit semua
                if (request.getNip() != null && !request.getNip().isBlank()
                        && !request.getNip().equals(guru.getNip())) {
                    if (guruRepository.existsByNip(request.getNip())) {
                        throw new IllegalArgumentException("NIP sudah digunakan");
                    }
                    guru.setNip(request.getNip());
                    guru.getUser().setNomorInduk(request.getNip()); // sync ke User
                }

                if (request.getEmail() != null && !request.getEmail().isBlank()
                        && !request.getEmail().equalsIgnoreCase(guru.getUser().getEmail())) {
                    if (userRepository.existsByEmail(request.getEmail())) {
                        throw new IllegalArgumentException("Email sudah digunakan");
                    }
                    guru.getUser().setEmail(request.getEmail());
                }
            }

            // semua admin boleh edit nama & tanggal lahir
            if (request.getNama() != null && !request.getNama().isBlank()) {
                guru.setNama(request.getNama());
                guru.getUser().setNama(request.getNama()); // sync ke User
            }

            if (request.getTanggalLahir() != null) {
                guru.setTanggalLahir(request.getTanggalLahir());
                guru.getUser().setTanggalLahir(request.getTanggalLahir()); // sync ke User
            }

            guru.getUser().setIsActive(request.getIsActive());
            guru.setIsActive(request.getIsActive());
        }

        // karena cascade ALL sudah ada, cukup save guru → user ikut tersimpan
        guruRepository.save(guru);

        return GuruMapper.toDTO(guru);
    }

    @Override
    public void deleteGuru(Long id) {
        Guru guru = guruRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guru tidak ditemukan"));
        guruRepository.delete(guru);
    }

    @Override
    public GuruDTO getGuruById(Long id) {
        Guru guru = guruRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guru tidak ditemukan"));
        return GuruMapper.toDTO(guru);
    }

    @Override
    public List<GuruDTO> getAllGuru() {
        return guruRepository.findAll()
                .stream()
                .filter(guru -> guru.getIsActive())
                .map(GuruMapper::toDTO)
                .toList();
    }

    @Override
    public Map<String, Object> getGurus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Guru> gurus = guruRepository.findAll(pageable);

        List<GuruDTO> dtoList = gurus.getContent().stream()
                .map(GuruMapper::toDTO)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("content", dtoList);
        response.put("totalElements", gurus.getTotalElements());
        response.put("totalPages", gurus.getTotalPages());
        response.put("currentPage", gurus.getNumber());

        return response;
    }

    // search guru
    @Override
    public Map<String, Object> searchGurus(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nama").ascending());

        Page<Guru> gurus = guruRepository.findByNamaContainingIgnoreCase(keyword, pageable);

        List<GuruDTO> dtoList = gurus.getContent().stream()
                .map(GuruMapper::toDTO)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("content", dtoList);
        response.put("totalElements", gurus.getTotalElements());
        response.put("totalPages", gurus.getTotalPages());
        response.put("currentPage", gurus.getNumber());

        return response;
    }

    // public List<GuruDTO> getAllGurus() {
    // return guruRepository.findAll()
    // .stream()
    // .map(guru -> {
    // GuruDTO dto = new GuruDTO();
    // dto.setId(guru.getId());
    // dto.setNip(guru.getNip());
    // dto.setNama(guru.getNama());
    // return dto;
    // })
    // .toList();
    // }
}