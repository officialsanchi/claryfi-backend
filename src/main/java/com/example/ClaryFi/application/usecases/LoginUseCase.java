package com.example.ClaryFi.application.usecases;

import com.example.ClaryFi.api.dtos.LoginRequest;
import com.example.ClaryFi.api.dtos.TokenResponse;
import com.example.ClaryFi.domain.model.User;
import com.example.ClaryFi.domain.repositories.UserRepository;
import com.example.ClaryFi.infrastructure.authentication.service.AuthService;
import com.example.ClaryFi.utils.roles.Environment;
import com.example.ClaryFi.utils.roles.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {
    private final AuthService authService;
    private final UserRepository userRepository;

    @Autowired
    public LoginUseCase(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    public TokenResponse execute(LoginRequest request) {
        // Default to sandbox; later logic for realm based on user/org
        String realm = "sandbox";
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getStatus() != Status.ACTIVE) {
            throw new RuntimeException("Account not verified or suspended");
        }

        // Optionally check org environment for realm
        if (user.getOrganization().getEnvironment() == Environment.LIVE) {
            realm = "live";
        }

        return authService.login(request.getEmail(), request.getPassword(), realm);
    }
}
