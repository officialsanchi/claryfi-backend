package com.example.ClaryFi.application.service;

import com.example.ClaryFi.application.port.input.PasswordManagementUseCase;
import com.example.ClaryFi.application.port.output.EmailPort;
import com.example.ClaryFi.application.port.output.KeycloakAdminPort;
import com.example.ClaryFi.application.port.output.PasswordResetTokenRepositoryPort;
import com.example.ClaryFi.domain.model.PasswordResetToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordService implements PasswordManagementUseCase {
    private final KeycloakAdminPort keycloakAdminPort;
    private final EmailPort emailPort;
    private final PasswordResetTokenRepositoryPort tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordService(KeycloakAdminPort keycloakAdminPort,
                           EmailPort emailPort,
                           PasswordResetTokenRepositoryPort tokenRepository,
                           PasswordEncoder passwordEncoder) {
        this.keycloakAdminPort = keycloakAdminPort;
        this.emailPort = emailPort;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void forgotPassword(String email) {
        // Generate token
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(email, token, LocalDateTime.now().plusMinutes(15));
        tokenRepository.save(resetToken);

        // Send email with token
        String resetLink = "https://your-frontend.com/reset-password?token=" + token;
        emailPort.sendEmail(email, "Password Reset Request",
                "Click the following link to reset your password: " + resetLink);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        // Validate token
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expired");
        }

        // Update password in Keycloak
        keycloakAdminPort.updatePasswordByEmail(resetToken.getEmail(), newPassword);

        // Remove token after use
        tokenRepository.delete(resetToken);
    }

    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) {
        // Validate old password in Keycloak
        if (!keycloakAdminPort.validatePassword(userId, oldPassword)) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        // Update password in Keycloak
        keycloakAdminPort.updatePassword(userId, newPassword);
    }
}
