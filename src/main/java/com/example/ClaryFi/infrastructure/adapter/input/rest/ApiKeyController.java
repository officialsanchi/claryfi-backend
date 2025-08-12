package com.example.ClaryFi.infrastructure.adapter.input.rest;

import com.example.ClaryFi.application.port.input.ApiKeyManagementUseCase;
import com.example.ClaryFi.application.service.ApiKeyService;
import com.example.ClaryFi.domain.model.ApiKey;
import com.example.ClaryFi.infrastructure.adapter.input.rest.data.responses.ApiKeyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/apikeys")
public class ApiKeyController {
    private final ApiKeyManagementUseCase apiKeyManagementUseCase;
    private final ApiKeyService   apiKeyService;

    public ApiKeyController(ApiKeyManagementUseCase apiKeyManagementUseCase, ApiKeyService apiKeyService) {
        this.apiKeyManagementUseCase = apiKeyManagementUseCase;
        this.apiKeyService = apiKeyService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiKeyResponse> createApiKey(@PathVariable String userId) {
        String apiKey = apiKeyService.generateApiKey(userId);
        ApiKeyResponse response = new ApiKeyResponse();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ApiKeyResponse>> listApiKeys(@PathVariable String userId) {
        List<ApiKeyResponse> keys = apiKeyManagementUseCase.listApiKeys(userId).stream()
                .map(k -> new ApiKeyResponse())
                .collect( Collectors.toList());
        return ResponseEntity.ok(keys);
    }

    @DeleteMapping("/{apiKeyId}")
    public ResponseEntity<Void> revokeApiKey(@PathVariable String apiKeyId) {
        apiKeyManagementUseCase.revokeApiKey(apiKeyId);
        return ResponseEntity.noContent().build();
    }
}
