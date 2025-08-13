package com.example.ClaryFi.application.port.input;

import com.example.ClaryFi.domain.model.RoleType;

public interface AuthorizationUseCase {
    void defineRole(RoleType roleType, String description);
    void assignRoleToUser(String userId, RoleType roleType);
    void removeRoleFromUser(String userId,RoleType roleType);
    boolean hasPermission(Long userId, String permission);
}
