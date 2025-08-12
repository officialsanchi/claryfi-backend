package com.example.ClaryFi.infrastructure.adapter.output.persistance.repository;

import com.example.ClaryFi.domain.model.Role;
import com.example.ClaryFi.domain.model.RoleType;
import com.example.ClaryFi.infrastructure.adapter.output.persistance.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
