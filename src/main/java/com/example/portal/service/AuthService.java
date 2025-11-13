package com.example.portal.service;

import com.example.portal.dto.LoginRequest;
import com.example.portal.model.User;
import com.example.portal.security.JwtUtil;
import com.example.portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
        // jika login tidak menggunakan NIS/NIP dan tanggal lahir
        // @Autowired
        // private UserRepository userRepository;

        // public ResponseEntity<?> login(LoginRequest request) {
        // return userRepository.findByUsername(request.getUsername())
        // .filter(user -> user.getPassword().equals(request.getPassword()))
        // .map(user -> ResponseEntity.ok(Map.of(
        // "token", "mock-token-abc123", // nanti bisa diganti JWT
        // "role", user.getRole(),
        // "username", user.getUsername())))
        // .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        // .body(Map.of("error", "Username atau password salah")));
        // }

        private final UserRepository userRepository;
        private final JwtUtil jwtUtil;

        @Autowired
        public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
                this.userRepository = userRepository;
                this.jwtUtil = jwtUtil;
        }

        @Autowired
        private PasswordEncoder passwordEncoder;

        // public ResponseEntity<?> login(LoginRequest request) {
        // return userRepository.findByNomorInduk(request.getNomorInduk())
        // .map(user -> {
        // System.out.println("Nomor Induk: " + request.getNomorInduk());
        // System.out.println("Password input: " + request.getPassword());
        // System.out.println("Tanggal lahir user: " + user.getTanggalLahir());
        // System.out.println("Password hash: " + user.getPassword());
        // String raw = request.getPassword();

        // if (user.getPassword() == null) {
        // // Login awal: password = tanggal lahir
        // if (user.getTanggalLahir().toString().equals(raw)) {
        // String token = jwtUtil.generateToken(user.getNomorInduk(),
        // user.getRole().name());
        // return ResponseEntity.ok(Map.of(
        // "token", token,
        // "role", user.getRole(),
        // "nomorInduk", user.getNomorInduk(),
        // "nama", user.getNama(),
        // "tglLahir", user.getTanggalLahir().toString(),
        // "fotoProfil", user.getFotoUrl(),
        // "email", user.getEmail(),
        // "loginAwal", true));
        // } else {
        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        // .body(Map.of("error",
        // "Tanggal lahir tidak cocok"));
        // }
        // } else {
        // // Login biasa: password = hash
        // if (passwordEncoder.matches(raw, user.getPassword())) {
        // String token = jwtUtil.generateToken(user.getNomorInduk(),
        // user.getRole().name());
        // return ResponseEntity.ok(Map.of(
        // "token", token,
        // "role", user.getRole(),
        // "nomorInduk", user.getNomorInduk(),
        // "nama", user.getNama(),
        // "tglLahir", user.getTanggalLahir().toString(),
        // "fotoProfil", user.getFotoUrl(),
        // "email", user.getEmail(),
        // "loginAwal", false));
        // } else {
        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        // .body(Map.of("error", "Password salah"));
        // }
        // }
        // })
        // .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        // .body(Map.of("error", "Nomor Induk tidak ditemukan")));
        // }

        public ResponseEntity<?> login(LoginRequest request) {
                return userRepository.findByNomorInduk(request.getNomorInduk().trim())
                                .map(user -> {
                                        System.out.println("Nomor Induk: " + request.getNomorInduk());
                                        System.out.println("Password input: " + request.getPassword());
                                        System.out.println("Tanggal lahir user: " + user.getTanggalLahir());
                                        System.out.println("Password hash: " + user.getPassword());

                                        String raw = request.getPassword().trim();

                                        if (user.getPassword() == null) {
                                                try {
                                                        LocalDate inputDate = LocalDate.parse(raw);
                                                        System.out.println("Parsed input date: " + inputDate);
                                                        System.out.println("User date: " + user.getTanggalLahir());

                                                        if (user.getTanggalLahir().equals(inputDate)) {
                                                                String token = jwtUtil.generateToken(
                                                                                user.getNomorInduk(),
                                                                                user.getRole().name());

                                                                Map<String, Object> response = new HashMap<>();
                                                                response.put("token", token);
                                                                response.put("role", user.getRole());
                                                                response.put("nomorInduk", user.getNomorInduk());
                                                                response.put("nama", user.getNama());
                                                                response.put("tglLahir",
                                                                                user.getTanggalLahir().toString());
                                                                response.put("fotoProfil", user.getFotoUrl());
                                                                response.put("email", user.getEmail());
                                                                response.put("loginAwal", true);

                                                                return ResponseEntity.ok(response);
                                                        } else {
                                                                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                                                                .body(Map.of("error",
                                                                                                "Tanggal lahir tidak cocok"));
                                                        }
                                                } catch (DateTimeParseException e) {
                                                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                                                        .body(Map.of("error",
                                                                                        "Format tanggal lahir tidak valid"));
                                                }
                                        } else {
                                                if (passwordEncoder.matches(raw, user.getPassword())) {
                                                        String token = jwtUtil.generateToken(user.getNomorInduk(),
                                                                        user.getRole().name());

                                                        Map<String, Object> response = new HashMap<>();
                                                        response.put("token", token);
                                                        response.put("role", user.getRole());
                                                        response.put("nomorInduk", user.getNomorInduk());
                                                        response.put("nama", user.getNama());
                                                        response.put("tglLahir", user.getTanggalLahir().toString());
                                                        response.put("fotoProfil", user.getFotoUrl());
                                                        response.put("email", user.getEmail());
                                                        response.put("loginAwal", false);

                                                        return ResponseEntity.ok(response);
                                                } else {
                                                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                                                        .body(Map.of("error", "Password salah"));
                                                }
                                        }
                                })
                                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                                .body(Map.of("error", "Nomor Induk tidak ditemukan")));
        }
}