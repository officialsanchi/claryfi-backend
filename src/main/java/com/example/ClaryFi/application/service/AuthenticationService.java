package com.example.ClaryFi.application.service;

import com.example.ClaryFi.application.port.input.AuthenticationUseCase;
import com.example.ClaryFi.application.port.output.AuditLogPort;
import com.example.ClaryFi.application.port.output.KeycloakAdminPort;
import com.example.ClaryFi.application.port.output.UserRepositoryPort;
import com.example.ClaryFi.domain.exception.UserNotFoundException;
import com.example.ClaryFi.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service


public class AuthenticationService implements AuthenticationUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final KeycloakAdminPort keycloakAdminPort;

    public AuthenticationService(UserRepositoryPort userRepositoryPort, KeycloakAdminPort keycloakAdminPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.keycloakAdminPort = keycloakAdminPort;
    }

    @Override
    public User login(String username, String password) {
        Optional<User> userOpt = userRepositoryPort.findByUsername(username);
        User user = userOpt.orElseThrow(() -> new UserNotFoundException(null));

        // Validate password, MFA, etc. (depends on integration with Keycloak or own logic)
        // Example:
        // if (!passwordEncoder.matches(password, user.getPasswordHash())) {
        //    throw new AuthenticationFailedException("Invalid credentials");
        //}

        // For Keycloak, authentication usually delegated to Keycloak server

        return user;
    }

    @Override
    public void logout(String userId) {

    }

    @Override
    public void enableMfa(String userId) {

    }

    @Override
    public void disableMfa(String userId) {

    }
}
