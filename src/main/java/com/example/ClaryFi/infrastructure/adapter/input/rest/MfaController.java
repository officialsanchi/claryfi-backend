package com.example.ClaryFi.infrastructure.adapter.input.rest;

import com.example.ClaryFi.application.port.input.AuditLoggingUseCase;
import com.example.ClaryFi.application.port.input.AuthenticationUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mfa")
public class MfaController {
    private final AuthenticationUseCase authenticationUseCase;
    private final AuditLoggingUseCase auditLoggingUseCase;

    public MfaController(AuthenticationUseCase authenticationUseCase, AuditLoggingUseCase auditLoggingUseCase) {
        this.authenticationUseCase = authenticationUseCase;
        this.auditLoggingUseCase = auditLoggingUseCase;
    }

    @PostMapping("/enable/{userId}")
    public ResponseEntity<Void> enableMfa(@PathVariable String userId) {
        authenticationUseCase.enableMfa(userId);
        auditLoggingUseCase.logEvent("MFA enable called for userId=" + userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/disable/{userId}")
    public ResponseEntity<Void> disableMfa(@PathVariable String userId) {
        authenticationUseCase.disableMfa(userId);
        auditLoggingUseCase.logEvent("MFA disable called for userId=" + userId);
        return ResponseEntity.noContent().build();
    }
}
