package com.techie.auth.server.config;



import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.techie.auth.server.util.RsaKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwkConfig {

    @Bean
    public RSAKey rsaKey(RsaKeyGenerator rsaKeyGenerator) {
        return rsaKeyGenerator.generateRsaKey();
    }

    @Bean
    public JWKSet jwkSet(RSAKey rsaKey) {
        return new JWKSet(rsaKey);
    }
}

