package com.example.ClaryFi.infrastructure.adapter.input.rest;

import com.example.ClaryFi.application.port.input.ProfileManagementUseCase;
import com.example.ClaryFi.domain.model.User;
import com.example.ClaryFi.infrastructure.adapter.input.rest.data.request.ProfileUpdateRequest;
import com.example.ClaryFi.infrastructure.adapter.input.rest.data.responses.UserProfileResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileManagementUseCase profileManagementUseCase;

    public ProfileController(ProfileManagementUseCase profileManagementUseCase) {
        this.profileManagementUseCase = profileManagementUseCase;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getProfile(@PathVariable Long userId) {
        User user = profileManagementUseCase.viewProfile(userId);

        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRoles(user.getRoles()
                .stream().map(r -> r.getRoleType().name()).collect(java.util.stream.Collectors.toSet()));
        response.setMfaEnabled(user.isMfaEnabled());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateProfile(@PathVariable Long userId,
                                              @Valid @RequestBody ProfileUpdateRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .build();

        profileManagementUseCase.updateProfile(userId, user);
        return ResponseEntity.noContent().build();
    }
}
