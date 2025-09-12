package com.techie.auth.server.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashes {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin";
        String hashed = encoder.encode(rawPassword);
        System.out.println("Hashed password: " + hashed);
    }
}