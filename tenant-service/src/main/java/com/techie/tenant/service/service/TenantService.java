package com.techie.tenant.service.service;

import com.techie.tenant.service.dto.TenantDTO;
import com.techie.tenant.service.entity.TenantEntity;
import com.techie.tenant.service.exception.TenantNotFoundException;
import com.techie.tenant.service.mapper.TenantMapper;
import com.techie.tenant.service.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantService {

    private static final Logger log = LoggerFactory.getLogger(TenantService.class);
    private final TenantRepository tenantRepository;

    public TenantDTO getTenantById(String tenantId) {
        TenantEntity entity = tenantRepository.findById(Long.valueOf(tenantId))
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found: " + tenantId));
        return TenantMapper.toDTO(entity);
    }

    public List<TenantDTO> getAllTenants() {
        return tenantRepository.findAll().stream()
                .map(TenantMapper::toDTO)
                .toList();
    }

    public TenantDTO assignRole(String tenantId, String role) {
        TenantEntity entity = tenantRepository.findById(Long.valueOf(tenantId))
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found: " + tenantId));
        entity.setAssignedRole(role);
        tenantRepository.save(entity);
        return TenantMapper.toDTO(entity);
    }

    public void handleUserCreatedEvent(String userId, String email, String name) {
        log.info("📥 Handling UserCreatedEvent for userId: {}", userId);
        TenantEntity tenant = new TenantEntity();
        tenant.setUserId(userId);
        tenant.setEmail(email);
        tenant.setAssignedRole("USER");
        tenantRepository.save(tenant);
    }
}