package com.example.portal.controller;

import com.example.portal.dto.RegisterUserRequest;
import com.example.portal.dto.UpdateProfileRequest;
import com.example.portal.dto.UserResponseDTO;
import com.example.portal.model.Role;
import com.example.portal.model.User;
import com.example.portal.repository.UserRepository;
import com.example.portal.service.FileStorageService;
import com.example.portal.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Arrays;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public UserController(UserService userService, UserRepository userRepository,
            FileStorageService fileStorageService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    // CREATE USER
    @PostMapping
    public UserResponseDTO createUser(@RequestBody RegisterUserRequest request) {
        return userService.createUser(request);
    }

    // GET ALL USERS
    @GetMapping
    public List<UserResponseDTO> getAllUsers(@RequestParam(required = false) Role role) {
        return userService.getAllUsers(role);
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // EDIT USER PROFILE
    @PutMapping("/edit/{nomorInduk}")
    public ResponseEntity<?> updateProfile(@PathVariable String nomorInduk,
            @Valid @RequestBody UpdateProfileRequest req) {
        return userRepository.findByNomorInduk(nomorInduk)
                .map(user -> {
                    user.setNama(req.getNama());

                    try {
                        Role roleEnum = Role.valueOf(req.getRole().toUpperCase());
                        user.setRole(roleEnum);
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body(Map.of("error", "Role tidak valid"));
                    }

                    user.setTanggalLahir(LocalDate.parse(req.getTanggalLahir()));
                    userRepository.save(user);
                    return ResponseEntity.ok(Map.of("message", "Profil berhasil diperbarui"));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User tidak ditemukan")));
    }

    // get roles all users
    @GetMapping("/roles")
    public List<String> getRoles() {
        return Arrays.stream(Role.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    // foto profile
    // @PostMapping("/foto/{nomorInduk}")
    // public ResponseEntity<?> uploadFotoProfil(
    // @PathVariable String nomorInduk,
    // @RequestParam("foto") MultipartFile file) throws IOException {

    // if (file.isEmpty()) {
    // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File tidak boleh
    // kosong");
    // }

    // if (file.getSize() > 2 * 1024 * 1024) {
    // throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Ukuran file
    // maksimal 2MB");
    // }

    // if (file.getContentType() == null ||
    // !file.getContentType().startsWith("image/")) {
    // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hanya file gambar
    // yang diperbolehkan");
    // }

    // User user = userRepository.findByNomorInduk(nomorInduk)
    // .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User
    // tidak ditemukan"));

    // String url = fileStorageService.store(file);
    // user.setFotoUrl(url);
    // userRepository.save(user);

    // return ResponseEntity.ok(Map.of("foto_url", url));
    // }
    @PostMapping("/foto/{nomorInduk}")
    public ResponseEntity<?> uploadFotoProfil(
            @PathVariable String nomorInduk,
            @RequestParam("foto") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File tidak boleh kosong");
        }

        if (file.getSize() > 2 * 1024 * 1024) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Ukuran file maksimal 2MB");
        }

        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hanya file gambar yang diperbolehkan");
        }

        User user = userRepository.findByNomorInduk(nomorInduk)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        // ✅ Hapus foto lama jika ada
        // String oldFotoUrl = user.getFotoUrl();
        // if (oldFotoUrl != null && !oldFotoUrl.isBlank()) {
        // Path oldPath = Paths.get(System.getProperty("user.dir") + oldFotoUrl);
        // try {
        // Files.deleteIfExists(oldPath);
        // } catch (IOException e) {
        // System.err.println("Gagal hapus foto lama: " + e.getMessage());
        // }
        // }

        // ✅ Hapus foto lama jika ada
        if (user.getFotoUrl() != null && !user.getFotoUrl().isBlank()) {
            fileStorageService.delete(user.getFotoUrl());
            System.out.println("Menghapus foto lama: " + user.getFotoUrl());
        }

        // ✅ Simpan foto baru
        String url = fileStorageService.store(file); // hasilnya: /uploads/foto/xxx.jpg
        user.setFotoUrl(url);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("foto_url", url));
    }
}