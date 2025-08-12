package com.example.ClaryFi.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder

public class User {
    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private Set<Role> roles;
    private boolean mfaEnabled;
    private boolean active;

//
//    public void setPasswordHash(String passwordHash) {
//        // validate password complexity (or move validation to application layer)
//        this.passwordHash = passwordHash;
//    }
}
