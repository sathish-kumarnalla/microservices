package com.techie.auth.server.config;

import com.techie.auth.server.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class TokenCustomizerConfig {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(UserRepository userRepository) {
        return context -> {
            if (context.getPrincipal() != null) {
                String username = context.getPrincipal().getName();
                userRepository.findByUsername(username).ifPresent(user -> {
                    context.getClaims().claim("tenant_id", user.getTenantId());
                    context.getClaims().claim("roles", Arrays.asList(user.getRoles().split(",")));
                });
            }
        };
    }
}