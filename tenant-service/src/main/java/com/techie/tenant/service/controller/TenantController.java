package com.techie.tenant.service.controller;

import com.techie.tenant.service.dto.TenantDTO;
import com.techie.tenant.service.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {

    private static final Logger log = LoggerFactory.getLogger(TenantController.class);
    private final TenantService tenantService;

    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantDTO> getTenant(@PathVariable String tenantId) {
        log.info("🔍 Fetching tenant with ID: {}", tenantId);
        return ResponseEntity.ok(tenantService.getTenantById(tenantId));
    }

    @GetMapping
    public ResponseEntity<List<TenantDTO>> getAllTenants() {
        log.info("📋 Fetching all tenants");
        return ResponseEntity.ok(tenantService.getAllTenants());
    }

    @PostMapping("/{tenantId}/assign-role")
    public ResponseEntity<TenantDTO> assignRole(@PathVariable String tenantId, @RequestParam String role) {
        log.info("🔧 Assigning role '{}' to tenant '{}'", role, tenantId);
        return ResponseEntity.ok(tenantService.assignRole(tenantId, role));
    }
}