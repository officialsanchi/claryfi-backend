package com.example.ClaryFi.infrastructure.adapter.input.rest.data.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private Set<String> roles;
    private boolean mfaEnabled;
}
