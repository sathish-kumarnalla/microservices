package com.techie.auth.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class UserConsent {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;
    private String clientId;
    private String scopes;
    private LocalDateTime grantedAt;
}
