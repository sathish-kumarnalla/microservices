package com.techie.auth.server.controller;

import com.techie.auth.server.entity.UserEntity;
import com.techie.auth.server.exception.UserInfoException;
import com.techie.auth.server.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userinfo")
@Tag(name = "User Info", description = "Authenticated user details")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Operation(summary = "Get authenticated user info from JWT")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserInfo(Authentication authentication) {
        return ResponseEntity.ok(userInfoService.getUserInfo(authentication));
    }

    @Operation(summary = "Tenant check")
    @PreAuthorize("@tenantSecurity.check(authentication, #tenantId)")
    @GetMapping("/tenant/{tenantId}/data")
    public ResponseEntity<String> getTenantData(@PathVariable String tenantId) {
        return ResponseEntity.ok(userInfoService.getTenantAccessMessage(tenantId));
    }

    @Operation(summary = "Search users based on role, tenant, and username filter")
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> searchUsers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String tenantId,
            @RequestParam(required = false) String usernameContains,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<UserEntity> users = userInfoService.searchUsers(role, tenantId, usernameContains, page, size);
        return ResponseEntity.ok(users);
    }
}