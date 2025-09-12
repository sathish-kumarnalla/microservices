package com.techie.auth.server.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RegisteredClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RegisteredClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (clientRepository.findByClientId("react-client") == null) {
            RegisteredClient reactClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("react-client")
                    .redirectUri("http://localhost:5173/callback")
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                    .scope(OidcScopes.OPENID)
                    .scope("profile")
                    .scope("email")
                    .clientSettings(ClientSettings.builder()
                            .requireProofKey(true)
                            .requireAuthorizationConsent(false)
                            .build())
                    .build();

            clientRepository.save(reactClient);
        }

        if (clientRepository.findByClientId("service-client") == null) {
            RegisteredClient serviceClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("service-client")
                    .clientSecret(passwordEncoder.encode("secret123"))
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .scope("token:view")
                    .scope("token:revoke")
                    .scope("user:read")
                    .scope("introspect")
                    .build();

            clientRepository.save(serviceClient);
        }

        if (clientRepository.findByClientId("admin-client") == null) {
            RegisteredClient adminClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("admin-client")
                    .clientSecret(passwordEncoder.encode("adminSecret"))
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri("http://localhost:9090" +
                            "/admin/callback")
                    .scope("client:register")
                    .scope("user:write")
                    .scope("token:view")
                    .scope("token:revoke")
                    .scope(OidcScopes.OPENID)
                    .scope("profile")
                    .clientSettings(ClientSettings.builder()
                            .requireProofKey(false)
                            .requireAuthorizationConsent(true)
                            .build())
                    .tokenSettings(TokenSettings.builder()
                            .accessTokenTimeToLive(Duration.ofMinutes(10))
                            .refreshTokenTimeToLive(Duration.ofHours(2))
                            .reuseRefreshTokens(true)
                            .build())
                    .build();

            clientRepository.save(adminClient);
        }
    }
}