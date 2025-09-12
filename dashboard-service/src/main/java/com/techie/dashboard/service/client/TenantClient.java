package com.techie.dashboard.service.client;

import com.techie.dashboard.service.dto.TenantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tenant-service", url = "http://localhost:8082")
public interface TenantClient {
    @GetMapping("/tenants/{tenantId}")
    TenantDTO getTenant(@PathVariable String tenantId);
}