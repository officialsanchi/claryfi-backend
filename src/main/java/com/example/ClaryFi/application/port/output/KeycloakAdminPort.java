package com.example.ClaryFi.application.port.output;

import org.springframework.stereotype.Component;

@Component
public interface KeycloakAdminPort {
    void assignRoleToUser(String userId, String roleName);
    void removeRoleFromUser(String userId, String roleName);
}
