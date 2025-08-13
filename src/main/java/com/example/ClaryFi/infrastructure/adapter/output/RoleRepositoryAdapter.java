package com.example.ClaryFi.infrastructure.adapter.output;

import com.example.ClaryFi.application.port.output.RoleRepositoryPort;
import com.example.ClaryFi.domain.model.Role;
import com.example.ClaryFi.domain.model.RoleType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryAdapter implements RoleRepositoryPort {
    @Override
    public Optional<Role> findByRoleType(RoleType roleType) {
        return Optional.empty();
    }


}
