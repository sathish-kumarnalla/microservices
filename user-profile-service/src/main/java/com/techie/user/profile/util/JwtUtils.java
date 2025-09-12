package com.techie.user.profile.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility class for extracting claims from JWT tokens and Authentication objects.
 */
public final class JwtUtils {

    // Private constructor to prevent instantiation
    private JwtUtils() {}

    /**
     * Extracts the 'email' claim from a Jwt token.
     */
    public static String extractEmailByJwt(Jwt jwt) {
        return getClaimAsString(jwt, "email");
    }

    /**
     * Extracts the 'sub' (subject/user ID) claim from a Jwt token.
     */
    public static String extractUserIdByJwt(Jwt jwt) {
        return getClaimAsString(jwt, "sub");
    }

    /**
     * Extracts the 'roles' claim as a list of strings from a Jwt token.
     */
    public static List<String> extractRolesByJwt(Jwt jwt) {
        Object rolesClaim = jwt.getClaims().get("roles");
        if (rolesClaim instanceof List<?>) {
            return ((List<?>) rolesClaim).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Extracts the 'tenant_id' claim as a list of strings from a Jwt token.
     */
    public static List<String> extractTenantIdByJwt(Jwt jwt) {
        Object tenantsClaim = jwt.getClaims().get("tenant_id");
        if (tenantsClaim instanceof List<?>) {
            return ((List<?>) tenantsClaim).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Safely retrieves a claim as a string from a Jwt token.
     */
    private static String getClaimAsString(Jwt jwt, String claimName) {
        return Optional.ofNullable(jwt.getClaims().get(claimName))
                .map(Object::toString)
                .orElse(null);
    }

    /**
     * Extracts the 'email' claim from an Authentication object.
     */
    public static String extractEmailByAuth(Authentication authentication) {
        return extractEmailByJwt(getJwtFromAuth(authentication));
    }

    /**
     * Extracts the 'sub' claim from an Authentication object.
     */
    public static String extractUserIdByAuth(Authentication authentication) {
        return extractUserIdByJwt(getJwtFromAuth(authentication));
    }

    /**
     * Extracts the 'roles' claim from an Authentication object.
     */
    public static List<String> extractRolesByAuth(Authentication authentication) {
        return extractRolesByJwt(getJwtFromAuth(authentication));
    }

    /**
     * Extracts the 'tenant_id' claim from an Authentication object.
     */
    public static List<String> extractTenantIdByAuth(Authentication authentication) {
        return extractTenantIdByJwt(getJwtFromAuth(authentication));
    }

    /**
     * Safely casts the principal of an Authentication object to Jwt.
     */
    private static Jwt getJwtFromAuth(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt jwt) {
            return jwt;
        }
        throw new IllegalArgumentException("Authentication principal is not of type Jwt");
    }
}