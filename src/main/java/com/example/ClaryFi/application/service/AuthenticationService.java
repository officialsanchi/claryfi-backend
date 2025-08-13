package com.example.ClaryFi.application.service;

import com.example.ClaryFi.application.port.input.AuditLoggingUseCase;
import com.example.ClaryFi.application.port.input.AuthenticationUseCase;
import com.example.ClaryFi.application.port.output.AuditLogPort;
import com.example.ClaryFi.application.port.output.KeycloakAdminPort;
import com.example.ClaryFi.application.port.output.UserRepositoryPort;
import com.example.ClaryFi.domain.exception.UserNotFoundException;
import com.example.ClaryFi.domain.model.User;
import com.example.ClaryFi.infrastructure.adapter.input.eventlistener.PasswordPolicyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service


public class AuthenticationService implements AuthenticationUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final KeycloakAdminPort keycloakAdminPort;
    private final AuditLoggingUseCase auditLoggingUseCase;

    public AuthenticationService(UserRepositoryPort userRepositoryPort,
                                 KeycloakAdminPort keycloakAdminPort,
                                 AuditLoggingUseCase auditLoggingUseCase) {
        this.userRepositoryPort = userRepositoryPort;
        this.keycloakAdminPort = keycloakAdminPort;
        this.auditLoggingUseCase = auditLoggingUseCase;
    }

    @Override
    public User login(String email, String password) {
        Optional<User> userOpt = userRepositoryPort.findByUsername(email);
        User user = userOpt.orElseThrow(() -> new UserNotFoundException(1L));

        // We do not validate password here because Keycloak issues token and validates credentials.
        // But we can log login attempt.
        auditLoggingUseCase.logEvent("Login attempt for username=" + email);

        return user;
    }

    @Override
    public void logout(String userId) {
        // ask Keycloak to logout user sessions
        try {
            keycloakAdminPort.logoutUserSessions(userId);
            auditLoggingUseCase.logEvent("Logout performed for userId=" + userId);
        } catch (Exception e) {
            auditLoggingUseCase.logEvent("Logout failed for userId=" + userId + " reason=" + e.getMessage());
        }
    }

    @Override
    public void enableMfa(String userId) {
        // In Keycloak: set required action to CONFIGURE_TOTP or set user attribute
        keycloakAdminPort.addRequiredAction(userId, "CONFIGURE_TOTP");
        auditLoggingUseCase.logEvent("MFA enable requested for userId=" + userId);
    }

    @Override
    public void disableMfa(String userId) {
        keycloakAdminPort.removeRequiredAction(userId, "CONFIGURE_TOTP");
        auditLoggingUseCase.logEvent("MFA disable requested for userId=" + userId);
    }

    // New helper for change password (you might have changePassword in PasswordManagementUseCase)
    public void changePassword(String userId, String oldPassword, String newPassword) {
        PasswordPolicyValidator.validate(newPassword);

        // Use Keycloak to update password (admin operation) or require old password flow
        keycloakAdminPort.resetPassword(userId, newPassword);
        auditLoggingUseCase.logEvent("Password changed for userId=" + userId);
    }
}
