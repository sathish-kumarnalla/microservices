package com.techie.user.profile.service;



import com.techie.user.profile.Repository.UserRepository;
import com.techie.user.profile.entity.UserEntity;
import com.techie.user.profile.exception.UserNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public UserService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with Id::" + id));
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }
    public List<UserEntity> getAllUsers(){
       return  userRepository.findAll();
    }

    @CircuitBreaker(name = "tenantService", fallbackMethod = "fallbackTenant")
    public String callTenantService() {
        return restTemplate.getForObject("http://tenant-service/api/tenants", String.class);
    }

    public String fallbackTenant(Throwable t) {
        return "Fallback: Tenant service is unavailable";
    }



}