package com.techie.dashboard.service.dto;

import lombok.Data;

@Data
public class TenantDTO {
    private String tenantId;
    private String name;
    private String contactEmail;
    private boolean active;
}