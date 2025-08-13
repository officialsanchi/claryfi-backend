package com.example.ClaryFi.application.port.input;

import com.example.ClaryFi.domain.model.ApiKey;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ApiKeyManagementUseCase {
    String generateApiKey(String userId);
    void revokeApiKey(String apiKeyId);
    List<ApiKey> listApiKeys(String userId);
}
