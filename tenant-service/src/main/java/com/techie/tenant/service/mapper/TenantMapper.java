package com.techie.tenant.service.mapper;

import com.techie.tenant.service.dto.TenantDTO;
import com.techie.tenant.service.entity.TenantEntity;

public class TenantMapper {
    public static TenantDTO toDTO(TenantEntity entity) {
        return new TenantDTO(entity.getUserId(), entity.getEmail(), entity.getAssignedRole());
    }
}
