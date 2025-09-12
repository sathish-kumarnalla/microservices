package com.techie.auth.server.controller;

import com.techie.auth.server.dto.ClientDto;
import com.techie.auth.server.entity.UserEntity;
import com.techie.auth.server.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin API", description = "Manage OAuth2 clients, tokens, and user info")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Register a new OAuth2 client")
    @PostMapping("/register-client")
    public ResponseEntity<String> registerClient(@RequestBody ClientDto dto) {
        adminService.registerClient(dto);
        return ResponseEntity.ok("Client registered successfully");
    }

    @Operation(summary = "Revoke an access token")
    @PostMapping("/revoke")
    public ResponseEntity<String> revokeToken(@RequestParam String token) {
        boolean revoked = adminService.revokeToken(token);
        if (revoked) {
            return ResponseEntity.ok("Token revoked successfully");
        }
        return ResponseEntity.status(404).body("Token not found");
    }

    @Operation(summary = "Token introspection")
    @PreAuthorize("hasAuthority('SCOPE_introspect')")
    @PostMapping("/introspect")
    public ResponseEntity<Map<String, Object>> introspect(@RequestParam String token) {
        Map<String, Object> result = adminService.introspect(token);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get authenticated user info from JWT")
    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    @GetMapping("/userinfo")
    public ResponseEntity<Map<String, Object>> userInfo(Authentication authentication) {
        Map<String, Object> userInfo = adminService.userInfo(authentication);
        return ResponseEntity.ok(userInfo);
    }

    @Operation(summary = "All users based on roles, tenants and username filter")
    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> searchUsers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String tenantId,
            @RequestParam(required = false) String usernameContains,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<UserEntity> users = adminService.searchUsers(role, tenantId, usernameContains, page, size);
        return ResponseEntity.ok(users);
    }
}