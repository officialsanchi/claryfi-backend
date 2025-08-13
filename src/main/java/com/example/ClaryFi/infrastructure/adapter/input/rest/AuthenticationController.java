package com.example.ClaryFi.infrastructure.adapter.input.rest;

import com.example.ClaryFi.application.port.input.AuthenticationUseCase;
import com.example.ClaryFi.infrastructure.adapter.input.rest.data.request.LoginRequest;
import com.example.ClaryFi.infrastructure.adapter.input.rest.data.responses.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationUseCase authenticationUseCase;

    public AuthenticationController(AuthenticationUseCase authenticationUseCase) {
        this.authenticationUseCase = authenticationUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        var user = authenticationUseCase.login(request.getUsername(), request.getPassword());

        // Assume you generate JWT token here (or from service)
        String token = "dummy-jwt-token";
        String refreshToken = "dummy-refresh-token";

        return ResponseEntity.ok(new LoginResponse ());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam String userId) {
        authenticationUseCase.logout(userId);
        return ResponseEntity.noContent().build();
    }
}
