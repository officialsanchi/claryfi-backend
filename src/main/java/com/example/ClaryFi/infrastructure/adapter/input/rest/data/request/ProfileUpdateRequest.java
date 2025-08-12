package com.example.ClaryFi.infrastructure.adapter.input.rest.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateRequest {
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

}
