package com.example.portal.service.auth;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.example.portal.exception.ResourceNotFoundException;
import com.example.portal.model.PasswordResetToken;
import com.example.portal.model.User;
import com.example.portal.repository.PasswordResetTokenRepository;
import com.example.portal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceForgotImpl implements AuthServiceForgot {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceForgotImpl(UserRepository userRepository,
            PasswordResetTokenRepository passwordResetTokenRepository,
            EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void sendResetPasswordLink(String nomorInduk, String email) {
        Optional<User> optionalUser = userRepository.findByNomorInduk(nomorInduk);

        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("Nomor Induk tidak ditemukan");
        }

        User user = optionalUser.get();

        if (!user.getEmail().equalsIgnoreCase(email)) {
            throw new IllegalArgumentException("Email tidak cocok dengan Nomor Induk");
        }

        String token = UUID.randomUUID().toString();

        // Cek apakah token sudah ada untuk user ini
        Optional<PasswordResetToken> existingToken = passwordResetTokenRepository.findByUser(user);

        PasswordResetToken resetToken = existingToken.orElse(new PasswordResetToken());
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        passwordResetTokenRepository.save(resetToken);

        log.info("Token reset untuk {}: {}", email, token);

        // emailService.sendResetLink(user.getEmail(), token);
        try {
            emailService.sendResetLink(user.getEmail(), token);
        } catch (Exception e) {
            log.error("Gagal mengirim email ke {}: {}", user.getEmail(), e.getMessage());
            throw new IllegalStateException("Gagal mengirim email reset password");
        }

    }

    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token tidak valid"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token sudah kadaluarsa");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);

        log.info("Password berhasil direset untuk {}", user.getEmail());
    }

}
