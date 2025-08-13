package com.example.ClaryFi.application.usecases;

import com.example.ClaryFi.api.dtos.SignupRequest;
import com.example.ClaryFi.domain.model.Organization;
import com.example.ClaryFi.domain.model.User;
import com.example.ClaryFi.domain.repositories.OrganizationRepository;
import com.example.ClaryFi.domain.repositories.UserRepository;
import com.example.ClaryFi.utils.roles.AuditActionType;
import com.example.ClaryFi.utils.roles.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SignupUseCase {
    private final UserRepository userRepo;
    private final OrganizationRepository orgRepo;
    private final EmailSender emailSender;
    private final PasswordEncoder encoder;
    private final AuditLog auditLogPort;
    private final GenerateApiKeyUseCase generateApiKeyUseCase;
    private final AuthPort authPort; // New dependency

    public Map<String, Object> execute(SignupRequest req) {
        // Create user in Keycloak
        String keycloakUserId = authPort.createUser(req);

        Organization org = new Organization();
        org.setName(req.getBusinessName());
        org.setEmail(req.getBusinessEmail());
        org.setWebsite(req.getBusinessWebsite());
        org.setPhoneNumber(req.getBusinessPhoneNumber());
        org.setIndustry(req.getBusinessIndustry());
        org = orgRepo.save(org);

        User user = new User();
        user.setId( UUID.fromString(keycloakUserId)); // Use Keycloak ID
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPasswordHash(encoder.encode(req.getPassword()));
        user.setOrganization(org);
        user.setStatus( Status.PENDING_VERIFICATION);
        user = userRepo.save(user);

        auditLogPort.log(user.getId(), org.getId(), AuditActionType.ACCOUNT_CREATED.name(),
                "User account created for " + user.getEmail(),
                Map.of("user_email", user.getEmail(), "org_name", org.getName()));

        // Keycloak sends verification email automatically in createUser
        return Map.of("message", "Account created. Verify email.", "user_id", user.getId().toString(), "status", "pending_verification");
    }

}
