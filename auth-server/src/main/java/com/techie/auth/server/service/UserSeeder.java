package com.techie.auth.server.service;

import com.techie.auth.server.entity.UserEntity;
import com.techie.auth.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserSeeder {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.existsByUsername("admin")) {
                userRepository.save(new UserEntity("admin",
                        passwordEncoder.encode("adminpass"),
                        "ADMIN",
                        "tenantA"));
            }

            if (userRepository.existsByUsername("user1")) {
                userRepository.save(new UserEntity("user1",
                        passwordEncoder.encode("user1pass"),
                        "USER",
                        "tenantA"));
            }

            if (!userRepository.existsByUsernameAndTenantId("user1", "tenantA")) {
                userRepository.save(new UserEntity("user1", passwordEncoder.encode("user1pass"), "USER", "tenantA"));
            }

            if (userRepository.existsByUsername("manager")) {
                userRepository.save(new UserEntity("manager",
                        passwordEncoder.encode("managerpass"),
                        "MANAGER",
                        "tenantB"));
            }
        };
    }


}
