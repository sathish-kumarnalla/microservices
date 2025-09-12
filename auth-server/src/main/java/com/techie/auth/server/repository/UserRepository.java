package com.techie.auth.server.repository;

import com.techie.auth.server.entity.UserConsent;
import com.techie.auth.server.entity.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    // Check if a user exists by username
    boolean existsByUsername(String username);

    // Find a user by username
    Optional<UserEntity> findByUsername(String username);

    // Find all users by tenant
    List<UserEntity> findByTenantId(String tenantId);


    // Check if a user exists in a specific tenant
    boolean existsByUsernameAndTenantId(String username, String tenantId);
    @NonNull
    Page<UserEntity> findAll(Pageable pageable);
    List<UserEntity> findByRolesAndTenantId(String role, String tenantId);


}
