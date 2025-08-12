package com.example.ClaryFi.infrastructure.adapter.input.rest.data.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginResponse {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    private String token;
    private String refreshToken;
}
