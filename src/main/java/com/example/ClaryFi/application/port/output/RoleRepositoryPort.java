package com.example.ClaryFi.application.port.output;

import com.example.ClaryFi.domain.model.Role;
import com.example.ClaryFi.domain.model.RoleType;

import java.util.Optional;

public interface RoleRepositoryPort {
    Optional<Role> findByRoleType(RoleType roleType);


}
