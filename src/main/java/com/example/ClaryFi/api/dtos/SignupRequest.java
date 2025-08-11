package com.example.ClaryFi.api.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {
    private String fullName ;
    private String email;
    private String password;
    private String username;
    private String businessName;
}
