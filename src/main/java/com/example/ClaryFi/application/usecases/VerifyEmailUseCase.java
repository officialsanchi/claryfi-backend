package com.example.ClaryFi.application.usecases;

import com.example.ClaryFi.domain.model.User;
import com.example.ClaryFi.domain.repositories.UserRepository;
import com.example.ClaryFi.infrastructure.authentication.service.AuthService;
import com.example.ClaryFi.utils.roles.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerifyEmailUseCase {
    private final AuthService authService;
    private final UserRepository userRepository;

    @Autowired
    public VerifyEmailUseCase(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    public void execute(String token) {
        // Parse or validate token if JWT; but for Keycloak, use user context or query
        // Assume we have userId from token or session; for demo:
        // 1. Redirect from email to app, app calls Keycloak to verify
        // 2. On success, update DB
        String userId = extractUserIdFromToken(token); // Custom impl
        String realm = "sandbox"; // Determine from user

        authService.executeEmailVerification(userId, token, realm);

        User user = userRepository.findUserById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
    }

    private String extractUserIdFromToken(String token) {
        return token.split(":")[1];
    }
}
