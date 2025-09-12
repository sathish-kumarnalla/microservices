package com.techie.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfoDTO {
    private String clientId;
    private String principalName;
    private String grantType;
    private Instant issuedAt;
    private Instant expiresAt;
    private String tokenType;
    private String tokenValue; // Optional: mask or omit in production



    public TokenInfoDTO(String principalName, String accessTokenValue, Instant accessTokenExpiresAt) {
    }
}