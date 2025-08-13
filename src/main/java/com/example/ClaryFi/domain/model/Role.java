package com.example.ClaryFi.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Role {
    private Long id;
    private RoleType roleType;
    private String description;
}
