package com.techie.dashboard.service.service;

import com.techie.dashboard.service.client.TenantClient;
import com.techie.dashboard.service.client.UserClient;
import com.techie.dashboard.service.dto.TenantDTO;
import com.techie.dashboard.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {
    @Autowired
    private UserClient userClient;

    @Autowired
    private TenantClient tenantClient;

    public Map<String, Object> getDashboard(String username) {
        UserDTO user = userClient.getUser(username);
        TenantDTO tenant = tenantClient.getTenant(user.getTenantId());

        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("user", user);
        dashboard.put("tenant", tenant);
        dashboard.put("message", "Welcome to your dashboard!");

        return dashboard;
    }
}