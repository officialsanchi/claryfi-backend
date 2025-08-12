package com.example.ClaryFi.infrastructure.adapter.input.rest.data.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiKeyResponse {
    private Long id;
    private String key;
    private LocalDateTime createdAt;
}
