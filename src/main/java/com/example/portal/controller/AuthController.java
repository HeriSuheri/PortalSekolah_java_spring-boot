package com.example.portal.controller;

import com.example.portal.service.AuthService;
import com.example.portal.dto.ChangePasswordRequest;
import com.example.portal.dto.LoginRequest;
import com.example.portal.model.User;
import com.example.portal.repository.UserRepository;
import com.example.portal.dto.auth.ForgotPasswordRequest;
import com.example.portal.dto.auth.ResetPasswordRequest;
import com.example.portal.exception.ResourceNotFoundException;
import com.example.portal.service.auth.AuthServiceForgot;
import com.example.portal.security.JwtUtil;

import java.util.Map;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
// @CrossOrigin(origins = "*") // supaya bisa diakses dari FE
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthServiceForgot authServiceForgot;

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    // CHANGE PASSWORD
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        Optional<User> userOpt = userRepository.findByNomorInduk(request.getNomorInduk());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User tidak ditemukan"));
        }

        User user = userOpt.get();

        // Cek apakah password lama cocok
        boolean validOld = false;

        if (user.getPassword() == null) {
            // Login awal: password = tanggal lahir
            validOld = user.getTanggalLahir().toString().equals(request.getOldPassword());
        } else {
            // Login biasa: password cocok
            // validOld = user.getPassword().equals(request.getOldPassword());
            validOld = passwordEncoder.matches(request.getOldPassword(), user.getPassword());
        }

        if (!validOld) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Password lama tidak cocok"));
        }

        // Set password baru
        // user.setPassword(request.getNewPassword()); // nanti hash
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Password berhasil diubah"));
    }

    // forgot password
    // @PostMapping("/forgot-password")
    // public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest
    // request) {
    // authServiceForgot.sendResetPasswordLink(request.getEmail());
    // return ResponseEntity.ok(Map.of("message", "Link reset telah dikirim ke
    // email"));
    // }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authServiceForgot.sendResetPasswordLink(request.getNomorInduk(), request.getEmail());
        return ResponseEntity.ok(Map.of("message", "Link reset password telah dikirim ke email Anda"));
    }

    // reset password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        authServiceForgot.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(Map.of("message", "Password berhasil direset"));
    }

}
