package com.techie.dashboard.service.controller;

import com.techie.dashboard.service.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/me")
    public ResponseEntity<?> getDashboard(Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(dashboardService.getDashboard(username));
    }
}