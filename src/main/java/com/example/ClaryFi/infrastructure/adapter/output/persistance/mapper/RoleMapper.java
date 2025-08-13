package com.example.ClaryFi.infrastructure.adapter.output.persistance.mapper;

import com.example.ClaryFi.domain.model.Role;
import com.example.ClaryFi.infrastructure.adapter.output.persistance.entity.RoleEntity;

public class RoleMapper {
    public static Role toDomain(RoleEntity entity) {
        if (entity == null) return null;
        Role role = new Role();
        role.setId(entity.getId());
        role.setRoleType(entity.getRoleType());
        role.setDescription(entity.getDescription());
        return role;
    }

    public static RoleEntity toEntity(Role domain) {
        if (domain == null) return null;
        RoleEntity entity = new RoleEntity();
        entity.setId(domain.getId());
        entity.setRoleType(  domain.getRoleType() );
        entity.setDescription(domain.getDescription());
        return entity;



    }
}
