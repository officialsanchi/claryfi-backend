package com.example.ClaryFi.application.port.output;

import com.example.ClaryFi.domain.model.ApiKey;

import java.util.List;
import java.util.Optional;

public interface ApiKeyRepositoryPort {
    ApiKey save(ApiKey apiKey);
    Optional<ApiKey> findById(String id);
    List<ApiKey> findByUserId(String userId);
    void revoke(String apiKey);

}
