package com.techie.auth.server.dto;

import lombok.Data;

@Data
public class UserFilter {
    private String role;
    private String tenantId;
    private String usernameContains;

    // Getters and setters
}
