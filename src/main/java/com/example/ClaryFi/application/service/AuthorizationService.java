package com.example.ClaryFi.application.service;

import com.example.ClaryFi.application.port.input.AuthorizationUseCase;
import com.example.ClaryFi.application.port.output.KeycloakAdminPort;
import com.example.ClaryFi.application.port.output.RoleRepositoryPort;
import com.example.ClaryFi.domain.model.RoleType;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements AuthorizationUseCase {
    private final KeycloakAdminPort keycloakAdminPort;
    private final RoleRepositoryPort roleRepositoryPort;

    public AuthorizationService(KeycloakAdminPort keycloakAdminPort, RoleRepositoryPort roleRepositoryPort) {
        this.keycloakAdminPort = keycloakAdminPort;
        this.roleRepositoryPort = roleRepositoryPort;
    }

    @Override
    public void defineRole(RoleType roleType, String description) {

    }

    @Override
    public void assignRoleToUser(String userId, RoleType roleType) {
        roleRepositoryPort.findByRoleType(roleType)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        keycloakAdminPort.assignRoleToUser(userId.toString(), String.valueOf( roleType ) );

    }

    @Override
    public void removeRoleFromUser(String userId, RoleType roleType) {

    }

    @Override
    public boolean hasPermission(Long userId, String permission) {
        return true;
    }
}
