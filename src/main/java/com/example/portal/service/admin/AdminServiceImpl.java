package com.example.portal.service.admin;

import com.example.portal.dto.UserResponseDTO;
import com.example.portal.dto.admin.CreateAdminRequest;
import com.example.portal.dto.admin.UpdateAdminRequest;
import com.example.portal.exception.ResourceNotFoundException;
import com.example.portal.mapper.UserMapper;
import com.example.portal.model.Role;
import com.example.portal.model.User;
import com.example.portal.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    // private static final Logger log =
    // LoggerFactory.getLogger(AdminServiceImpl.class);

    // cretae admin
    @Override
    public User createAdmin(CreateAdminRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email sudah digunakan");
        }

        if (userRepository.existsByNomorInduk(request.getNomorInduk())) {
            throw new RuntimeException("Nomor Induk sudah digunakan");
        }

        User user = UserMapper.fromCreateAdminRequest(request);
        return userRepository.save(user);
    }

    // get all admins: ga dipake
    @Override
    public List<UserResponseDTO> getAllAdmins() {
        return userRepository.findByRole(Role.ADMIN).stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    // get all admin
    @Override
    public Map<String, Object> getAdmins(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<User> admins = userRepository.findByRole(Role.ADMIN, pageable);

        List<UserResponseDTO> dtoList = admins.getContent().stream()
                .map(UserMapper::toDTO)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("content", dtoList);
        response.put("totalElements", admins.getTotalElements());
        response.put("totalPages", admins.getTotalPages());
        response.put("currentPage", admins.getNumber());

        return response;
    }

    // update admin
    @Override
    public void updateAdmin(Long id, UpdateAdminRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin dengan ID " + id + " tidak ditemukan"));

        // if (!user.getRole().equals("ADMIN")) {
        // throw new IllegalArgumentException("User ini bukan admin");
        // }
        if (user.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("User ini bukan admin");
        }

        if (!user.getEmail().equalsIgnoreCase(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email sudah digunakan oleh user lain");
        }

        if (!user.getNomorInduk().equalsIgnoreCase(request.getNomorInduk())
                && userRepository.existsByNomorInduk(request.getNomorInduk())) {
            throw new RuntimeException("Nomor Induk sudah digunakan oleh user lain");
        }

        user.setNomorInduk(request.getNomorInduk());
        user.setNama(request.getNama());
        user.setEmail(request.getEmail());
        user.setIsActive(request.getIsActive());

        log.info("Tanggal lahir diterima: {}", request.getTanggalLahir());

        if (request.getTanggalLahir() != null && !request.getTanggalLahir().isBlank()) {
            try {
                user.setTanggalLahir(LocalDate.parse(request.getTanggalLahir()));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Format tanggal lahir tidak valid. Gunakan yyyy-MM-dd");
            }
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    // delete admin
    @Override
    public void deleteAdmin(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin dengan ID " + id + " tidak ditemukan"));

        if (user.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("User ini bukan admin");
        }

        userRepository.delete(user);
    }

    // search admin
    @Override
    public Map<String, Object> searchAdmins(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<User> result = userRepository.findByRoleAndNamaContainingIgnoreCase(Role.ADMIN, keyword, pageable);

        List<UserResponseDTO> dtoList = result.getContent().stream()
                .map(UserMapper::toDTO)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("content", dtoList);
        response.put("totalElements", result.getTotalElements());
        response.put("totalPages", result.getTotalPages());
        response.put("currentPage", result.getNumber());

        return response;
    }

}