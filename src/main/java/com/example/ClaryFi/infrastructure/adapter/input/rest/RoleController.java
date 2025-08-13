package com.example.ClaryFi.infrastructure.adapter.input.rest;

import com.example.ClaryFi.application.port.input.AuditLoggingUseCase;
import com.example.ClaryFi.application.service.AuthorizationService;
import com.example.ClaryFi.domain.model.RoleType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final AuthorizationService authorizationService;
    private final AuditLoggingUseCase auditLoggingUseCase;

    public RoleController(AuthorizationService authorizationService, AuditLoggingUseCase auditLoggingUseCase) {
        this.authorizationService = authorizationService;
        this.auditLoggingUseCase = auditLoggingUseCase;
    }

    @PostMapping("/define")
    public ResponseEntity<Void> defineRole(@RequestParam RoleType roleType, @RequestParam String description) {
        authorizationService.defineRole(roleType, description);
        auditLoggingUseCase.logEvent("Role defined: " + roleType);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignRole(@RequestParam String userId, @RequestParam RoleType roleType) {
        authorizationService.assignRoleToUser(userId, roleType);
        auditLoggingUseCase.logEvent("Role assigned: " + roleType + " to userId=" + userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removeRole(@RequestParam String userId, @RequestParam RoleType roleType) {
        authorizationService.removeRoleFromUser(userId, roleType);
        auditLoggingUseCase.logEvent("Role removed: " + roleType + " from userId=" + userId);
        return ResponseEntity.noContent().build();
    }
}
