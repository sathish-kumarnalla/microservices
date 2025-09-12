package com.techie.auth.server.service;

import com.techie.auth.server.dto.ClientDto;
import com.techie.auth.server.entity.UserEntity;
import com.techie.auth.server.exception.AdminServiceException;
import com.techie.auth.server.repository.UserRepository;
import com.techie.auth.server.specification.UserSpecifications;
import jakarta.persistence.QueryTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AdminService {

    private final OAuth2AuthorizationService authorizationService;
    private final RegisteredClientRepository registeredClientRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminService(OAuth2AuthorizationService authorizationService,
                        RegisteredClientRepository registeredClientRepository,
                        UserRepository userRepository) {
        this.authorizationService = authorizationService;
        this.registeredClientRepository = registeredClientRepository;
        this.userRepository = userRepository;
    }


    @PreAuthorize("hasAuthority('SCOPE_client:register')")
    public void registerClient(ClientDto dto) {
        try {
            RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId(dto.getClientId())
                    .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri(dto.getRedirectUri())
                    .scope("openid")
                    .scope("profile")
                    .scope("read")
                    .scope("write")
                    .clientSettings(ClientSettings.builder().requireProofKey(true).build())
                    .build();

            registeredClientRepository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new AdminServiceException("Client ID already exists", 409, "CLIENT_DUPLICATE");
        } catch (JpaObjectRetrievalFailureException e) {
            throw new AdminServiceException("Invalid redirect URI or related entity", 400, "CLIENT_ENTITY_ERROR");
        } catch (Exception e) {
            throw new AdminServiceException("Failed to register client", 500, "REGISTRATION_FAILED");
        }
    }

    @PreAuthorize("hasAuthority('SCOPE_token:view')")
    public boolean revokeToken(String token) {
        try {
            OAuth2Authorization authorization = authorizationService.findByToken(token, null);
            if (authorization != null) {
                authorizationService.remove(authorization);
                return true;
            }
            return false;
        } catch (EmptyResultDataAccessException e) {
            throw new AdminServiceException("Token not found", 404, "TOKEN_NOT_FOUND");
        } catch (Exception e) {
            throw new AdminServiceException("Error revoking token", 500, "REVOKE_TOKEN_ERROR");
        }
    }

    @PreAuthorize("hasAuthority('SCOPE_introspect')")
    public Map<String, Object> introspect(String token) {
        try {
            boolean active = authorizationService.findByToken(token, null) != null;
            return Map.of("active", active);
        } catch (Exception e) {
            throw new AdminServiceException("Error during token introspection", 500, "INTROSPECT_ERROR");
        }
    }

    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    public Map<String, Object> userInfo(Authentication authentication) {
        try {
            return Map.of(
                    "sub", authentication.getName(),
                    "email", authentication.getName(),
                    "roles", authentication.getAuthorities()
            );
        } catch (Exception e) {
            throw new AdminServiceException("Failed to retrieve user info", 500, "USER_INFO_ERROR");
        }
    }

    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    public List<UserEntity> searchUsers(String role, String tenantId, String usernameContains, int page, int size) {
        try {
            Specification<UserEntity> spec = Specification
                    .where(UserSpecifications.hasRole(role))
                    .and(UserSpecifications.inTenant(tenantId))
                    .and(UserSpecifications.usernameContains(usernameContains));

            Page<UserEntity> result = userRepository.findAll(spec, PageRequest.of(page, size));
            return result.getContent();
        } catch (QueryTimeoutException e) {
            throw new AdminServiceException("Search query timed out", 408, "SEARCH_TIMEOUT");
        } catch (Exception e) {
            throw new AdminServiceException("Error searching users", 500, "SEARCH_ERROR");
        }
    }
}