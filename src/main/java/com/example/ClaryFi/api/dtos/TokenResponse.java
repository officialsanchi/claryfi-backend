package com.example.ClaryFi.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class TokenResponse {
    private String tokenId;
    private String accessToken;
    private String refreshToken;
    private int expiresIn;

    public TokenResponse(String accessToken, String refreshToken, int expiresIn) {
    }
}
