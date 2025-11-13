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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

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

    // get all admins
    @Override
    public List<UserResponseDTO> getAllAdmins() {
        return userRepository.findByRole(Role.ADMIN).stream()
                .map(UserMapper::toDTO)
                .toList();
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

        user.setNama(request.getNama());
        user.setEmail(request.getEmail());

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

}