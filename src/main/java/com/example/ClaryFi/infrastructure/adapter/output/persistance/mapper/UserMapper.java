package com.example.ClaryFi.infrastructure.adapter.output.persistance.mapper;

import com.example.ClaryFi.domain.model.User;
import com.example.ClaryFi.infrastructure.adapter.output.persistance.entity.UserEntity;

import java.util.stream.Collectors;

public class UserMapper {
    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;
        User user = User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .mfaEnabled(entity.isMfaEnabled())
                .active(entity.isActive())
                .roles(entity.getRoles().stream()
                        .map(RoleMapper::toDomain)
                        .collect( Collectors.toSet()))
                .build();
        return user;
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) return null;
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setMfaEnabled(domain.isMfaEnabled());
        entity.setActive(domain.isActive());
        entity.setRoles(domain.getRoles().stream()
                .map(RoleMapper::toEntity)
                .collect(Collectors.toSet()));
        return entity;
    }
}
