package com.techie.auth.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String roles;
    private String tenantId;

    public UserEntity(String username, String password, String roles, String tenantId) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.tenantId = tenantId;
    }
}