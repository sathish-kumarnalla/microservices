package com.techie.user.profile.Repository;


import com.techie.user.profile.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;



import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByUsername(String username);// ✅ Corrected
    Optional<UserEntity> findByEmail(String email); // ✅ Valid
}