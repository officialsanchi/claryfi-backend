package com.example.ClaryFi.api.dtos;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class TokenResponse {
    private String tokenId;
    private String accessToken;
    private String refreshToken;
    private int expiresIn;


    public TokenResponse(String accessToken, String refreshToken, int expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }
}
