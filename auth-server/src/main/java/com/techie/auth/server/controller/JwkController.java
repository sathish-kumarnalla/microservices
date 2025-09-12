package com.techie.auth.server.controller;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
class JwkController {

    private final JWKSet jwkSet;

    public JwkController(JWKSet jwkSet) {
        this.jwkSet = jwkSet;
    }

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        return jwkSet.toJSONObject();
    }
}




