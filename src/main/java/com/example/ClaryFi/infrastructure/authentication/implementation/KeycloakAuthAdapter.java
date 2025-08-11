package com.example.ClaryFi.infrastructure.authentication.implementation;

import com.example.ClaryFi.api.dtos.SignupRequest;
import com.example.ClaryFi.api.dtos.TokenResponse;
import com.example.ClaryFi.domain.model.User;
import com.example.ClaryFi.infrastructure.authentication.service.AuthService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class KeycloakAuthAdapter implements AuthService {
    private final Keycloak keycloak;

    @Autowired
    public KeycloakAuthAdapter(@Value("${keycloak.auth-server-url}") String serverUrl,
                               @Value("${keycloak.realm}") String realm,
                               @Value("${keycloak.resource}") String clientId,
                               @Value("${keycloak.credentials.secret}") String clientSecret) {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }

    @Override
    public String createUser(SignupRequest request) {
        User user = new UserRepresentation();
        user.setFirstName(request.getFullName().split(" ")[0]);
        user.setLastName(request.getFullName().split(" ")[1]);
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        user.setEmailVerified(false); // Trigger verification
        user.setUsername(request.getEmail());

        CredentialRepresentation password = new CredentialRepresentation();
        password.setType(CredentialRepresentation.PASSWORD);
        password.setValue(request.getPassword());
        password.setTemporary(false);
        user.setCredentials(List.of(password));

        // Custom attributes: business_name, etc.
        user.singleAttribute("business_name", request.getBusinessName());

        Response response = keycloak.realm("sandbox").users().create(user);
        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            // Send verification email via Keycloak
            keycloak.realm("sandbox").users().get(userId).sendVerifyEmail();
            return userId;
        }
        throw new RuntimeException("User creation failed");
    }

    @Override
    public void verifyEmail(String token) {
        // Keycloak handles verification via email link; if custom, implement callback
        // For now, assume Keycloak updates user.emailVerified = true
    }

    @Override
    public TokenResponse login(String email, String password, String realm) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(keycloak.getAuthServerUrl() + "/realms/" + realm + "/protocol/openid-connect/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=password" +
                                "&client_id=" + keycloak.getClientId() +
                                "&client_secret=" + keycloak.getClientSecret() +
                                "&username=" + email +
                                "&password=" + password
                ))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(response.body());
                return new TokenResponse(
                        json.get("access_token").asText(),
                        json.get("refresh_token").asText(),
                        json.get("expires_in").asInt()
                );
            } else {
                throw new RuntimeException("Login failed: " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during login", e);
        }
    }

    // Implement login, role assignment, etc.
}