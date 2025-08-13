package com.example.ClaryFi.infrastructure.adapter.output;

import com.example.ClaryFi.application.port.output.ApiKeyRepositoryPort;
import com.example.ClaryFi.domain.model.ApiKey;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class ApiKeyRepositoryAdapter implements ApiKeyRepositoryPort {


    @Override
    public ApiKey save(ApiKey apiKey) {
        return null;
    }

    @Override
    public Optional<ApiKey> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<ApiKey> findByUserId(String userId) {
        return List.of();
    }

    @Override
    public void revoke(String apiKey) {

    }
}
