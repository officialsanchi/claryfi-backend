package com.example.ClaryFi.infrastructure.adapter.input.rest;

import com.example.ClaryFi.application.port.input.AuditLoggingUseCase;
import com.example.ClaryFi.application.port.input.AuthenticationUseCase;
import com.example.ClaryFi.application.port.output.KeycloakAdminPort;
import com.example.ClaryFi.infrastructure.adapter.input.rest.data.request.ChangePasswordRequest;
import com.example.ClaryFi.infrastructure.adapter.input.rest.data.request.ForgotPasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password")
public class PasswordController {
    private final KeycloakAdminPort keycloakAdminPort;
    private final AuthenticationUseCase authenticationUseCase;
    private final AuditLoggingUseCase auditLoggingUseCase;

    public PasswordController(KeycloakAdminPort keycloakAdminPort,
                              AuthenticationUseCase authenticationUseCase,
                              AuditLoggingUseCase auditLoggingUseCase) {
        this.keycloakAdminPort = keycloakAdminPort;
        this.authenticationUseCase = authenticationUseCase;
        this.auditLoggingUseCase = auditLoggingUseCase;
    }

    // Forgot password: user sends email, app triggers Keycloak to send reset-link
    @PostMapping("/forgot")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        // Find user in database or Keycloak, then trigger execute-actions-email with UPDATE_PASSWORD
        // Here assume request contains keycloakUserId or email (you may need to lookup)
        // For example:
        keycloakAdminPort.executeActionsEmail(request.getUserId(), java.util.List.of("UPDATE_PASSWORD"));
        auditLoggingUseCase.logEvent("Forgot password requested for userId=" + request.getUserId());
        return ResponseEntity.accepted().build();
    }

    // Change password for logged in user (admin reset vs user change)
    @PostMapping("/change")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request) {
        // This endpoint must be authenticated; use Jwt token to identify user. For now assume userId provided.
        ((com.example.ClaryFi.application.service.AuthenticationService)authenticationUseCase)
                .changePassword(request.getUserId(), request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.noContent().build();
    }
}
