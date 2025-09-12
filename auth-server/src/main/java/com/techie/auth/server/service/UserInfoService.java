package com.techie.auth.server.service;

import com.techie.auth.server.entity.UserEntity;
import com.techie.auth.server.exception.UserInfoException;
import com.techie.auth.server.repository.UserRepository;
import com.techie.auth.server.specification.UserSpecifications;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserInfoService {

    private final UserRepository userRepository;

    public UserInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, Object> getUserInfo(Authentication authentication) {
        return Map.of(
                "sub", authentication.getName(),
                "email", authentication.getName(),
                "roles", authentication.getAuthorities()
        );
    }

    public String getTenantAccessMessage(String tenantId) {
        return "Access granted for tenant: " + tenantId;
    }

    public List<UserEntity> searchUsers(String role, String tenantId, String usernameContains, int page, int size) {
        try {
            Specification<UserEntity> spec = Specification
                    .where(UserSpecifications.hasRole(role))
                    .and(UserSpecifications.inTenant(tenantId))
                    .and(UserSpecifications.usernameContains(usernameContains));

            Page<UserEntity> result = userRepository.findAll(spec, PageRequest.of(page, size));
            return result.getContent();
        } catch (DataAccessException e) {
            throw new UserInfoException("Database error while searching users", "USER_SEARCH_DB_ERROR");
        } catch (Exception e) {
            throw new UserInfoException("Unexpected error during user search", "USER_SEARCH_ERROR");
        }
    }
}