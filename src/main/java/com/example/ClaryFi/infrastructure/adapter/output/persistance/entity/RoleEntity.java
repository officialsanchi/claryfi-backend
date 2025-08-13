package com.example.ClaryFi.infrastructure.adapter.output.persistance.entity;

import com.example.ClaryFi.domain.model.Role;
import com.example.ClaryFi.domain.model.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_type", nullable = false, unique = true)
    private RoleType roleType;

    @Column(name = "description")
    private String description;
}
