package com.example.ClaryFi.infrastructure.authentication.implementation;

import com.example.ClaryFi.api.dtos.SignupRequest;
import com.example.ClaryFi.api.dtos.TokenResponse;
import com.example.ClaryFi.infrastructure.authentication.service.AuthService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class KeycloakAuthAdapter implements AuthService {
    private final Keycloak keycloak;
    private final String serverUrl;
    private final String clientId;
    private final String clientSecret;

    public KeycloakAuthAdapter(
            @Value("${keycloak.auth-server-url}") String serverUrl,
            @Value("${keycloak.realm}") String realm,
            @Value("${keycloak.resource}") String clientId,
            @Value("${keycloak.credentials.secret}") String clientSecret) {
        this.serverUrl = serverUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;

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
        try {
            UserRepresentation user = new UserRepresentation();
            String[] nameParts = request.getFullName().split(" ", 2);
            user.setFirstName(nameParts[0]);
            user.setLastName(nameParts.length > 1 ? nameParts[1] : "");
            user.setEmail(request.getEmail());
            user.setEnabled(true);
            user.setEmailVerified(false);
            user.setUsername(request.getEmail());

            CredentialRepresentation password = new CredentialRepresentation();
            password.setType(CredentialRepresentation.PASSWORD);
            password.setValue(request.getPassword());
            password.setTemporary(false);
            user.setCredentials(List.of(password));

            // Custom attributes
            user.singleAttribute("business_name", request.getBusinessName());

            Response response = keycloak.realm("sandbox").users().create(user);
            if (response.getStatus() == 201) {
                String userId = CreatedResponseUtil.getCreatedId(response);
                // Trigger verification email
                keycloak.realm("sandbox").users().get(userId).sendVerifyEmail();
                return userId;
            } else {
                throw new RuntimeException("User creation failed: " + response.readEntity(String.class));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating user", e);
        }
    }

    @Override
    public void verifyEmail(String token) {
        // If we needed a custom flow, we could decode/validate the token here
        // Keycloak handles this internally via its email verification links
        // So no extra code is needed unless we're doing an external verification process
    }

    @Override
    public String getAccessToken(String username, String password) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl + "/realms/sandbox/protocol/openid-connect/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "grant_type=password" +
                                    "&client_id=" + clientId +
                                    "&client_secret=" + clientSecret +
                                    "&username=" + username +
                                    "&password=" + password
                    ))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(response.body());
                return json.get("access_token").asText();
            }
            throw new RuntimeException("Failed to get access token: " + response.body());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching access token", e);
        }
    }

    @Override
    public TokenResponse login(String email, String password, String realm) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl + "/realms/" + realm + "/protocol/openid-connect/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "grant_type=password" +
                                    "&client_id=" + clientId +
                                    "&client_secret=" + clientSecret +
                                    "&username=" + email +
                                    "&password=" + password
                    ))
                    .build();

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

    @Override
    public void executeEmailVerification(String userId, String token, String realm) {
        // Default Keycloak behavior: trigger the VERIFY_EMAIL required action
        keycloak.realm(realm).users().get(userId).executeActionsEmail(List.of("VERIFY_EMAIL"));
        // If we wanted token-based manual verification:
        // call /realms/{realm}/login-actions/email-verification?key={token}&client_id={clientId}
    }

    @Override
    public void assignRole(String userId, String roleName, String realm) {
        try {
            RoleRepresentation role = keycloak.realm(realm).roles().get(roleName).toRepresentation();
            keycloak.realm(realm).users().get(userId).roles().realmLevel().add(List.of(role));
        } catch (Exception e) {
            throw new RuntimeException("Error assigning role " + roleName + " to user " + userId, e);
        }
    }
}
