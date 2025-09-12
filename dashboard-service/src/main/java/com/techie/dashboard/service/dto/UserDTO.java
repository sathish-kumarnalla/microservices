package com.techie.dashboard.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String tenantId;
    private List<String> roles;
}