package com.techie.auth.server.specification;


import com.techie.auth.server.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<UserEntity> hasRole(String role) {
        return (root, query, cb) -> role == null ? null : cb.equal(root.get("roles"), role);
    }

    public static Specification<UserEntity> inTenant(String tenantId) {
        return (root, query, cb) -> tenantId == null ? null : cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<UserEntity> usernameContains(String keyword) {
        return (root, query, cb) -> keyword == null ? null : cb.like(root.get("username"), "%" + keyword + "%");
    }
}