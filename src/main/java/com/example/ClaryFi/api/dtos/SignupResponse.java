package com.example.ClaryFi.api.dtos;

import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;

@Setter
@Getter
@AllArgsConstructor
public class SignupResponse {
    private String message;
    private String userId;
    private String status;
}
