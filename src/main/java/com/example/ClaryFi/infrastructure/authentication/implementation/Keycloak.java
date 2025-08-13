package com.example.ClaryFi.infrastructure.authentication.implementation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Keycloak {
    private String clientId;
    private String clientSecret;
    private String clientName;
    private String grantType;
}
