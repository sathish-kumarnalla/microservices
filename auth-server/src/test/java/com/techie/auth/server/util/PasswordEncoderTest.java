package com.techie.auth.server.util;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncoderTest {

    private PasswordEncoder createDelegatingPasswordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("plain", new PlainTextPasswordEncoder());

        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }

    @Test
    void testBcryptEncoding() {
        PasswordEncoder encoder = createDelegatingPasswordEncoder();
        String rawPassword = "securePassword";
        String encoded = encoder.encode(rawPassword);

        assertTrue(encoder.matches(rawPassword, encoded));
        assertTrue(encoded.startsWith("{bcrypt}"));
    }

    @Test
    void testPlainTextEncoding() {
        PasswordEncoder encoder = createDelegatingPasswordEncoder();
        String rawSecret = "clientSecret";
        String encoded = "{plain}" + rawSecret;

        assertTrue(encoder.matches(rawSecret, encoded));
    }

    @Test
    void testInvalidMatchFails() {
        PasswordEncoder encoder = createDelegatingPasswordEncoder();
        String encoded = "{plain}clientSecret";

        assertFalse(encoder.matches("wrongSecret", encoded));
    }
}
