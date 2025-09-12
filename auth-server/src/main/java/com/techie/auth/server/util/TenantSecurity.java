package com.techie.auth.server.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class TenantSecurity {
    public boolean check(Authentication authentication, String tenantId) {
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return tenantId.equals(jwt.getClaimAsString("tenant_id"));
        }
        return false;
    }
}