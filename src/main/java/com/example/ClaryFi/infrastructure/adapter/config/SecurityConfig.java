package com.example.ClaryFi.infrastructure.adapter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;
    
    private final KeycloakProperties keycloakProperties;

    public SecurityConfig(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/actuator/**", "/health").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        // stateless JWT but you can still configure session creation policy if required
                        .sessionFixation(sessionFixation -> sessionFixation.migrateSession())
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        // optional: customize logout endpoint behavior if you have a logout controller
        // http.logout(logout -> logout.logoutUrl("/api/auth/logout").logoutSuccessHandler((req, res, auth) -> res.setStatus(204)));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String issuer = keycloakProperties.getIssuerUri();
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuerUri);
        jwtDecoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(issuerUri));
        return jwtDecoder;
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            // map scopes/aud claims
            authorities.addAll(defaultGrantedAuthoritiesConverter.convert(jwt));

            // realm roles
            Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
            if (realmAccess != null && realmAccess.get("roles") instanceof Collection) {
                @SuppressWarnings("unchecked")
                Collection<String> realmRoles = (Collection<String>) realmAccess.get("roles");
                authorities.addAll(realmRoles.stream()
                        .map(r -> (GrantedAuthority) () -> "ROLE_" + r.toUpperCase())
                        .collect(Collectors.toList()));
            }

            // client roles under resource_access.{clientId}.roles
            Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
            if (resourceAccess != null) {
                Object clientObj = resourceAccess.get(keycloakProperties.getClientId());
                if (clientObj instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> clientMap = (Map<String, Object>) clientObj;
                    @SuppressWarnings("unchecked")
                    Collection<String> clientRoles = (Collection<String>) clientMap.get("roles");
                    if (clientRoles != null) {
                        authorities.addAll(clientRoles.stream()
                                .map(r -> (GrantedAuthority) () -> "ROLE_" + r.toUpperCase())
                                .collect(Collectors.toList()));
                    }
                }
            }

            return authorities;
        });

        return converter;
    }
}

