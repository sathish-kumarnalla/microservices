package com.techie.user.profile.controller;

import  com.techie.user.profile.util.JwtUtils;
import com.techie.user.profile.entity.UserEntity;
import com.techie.user.profile.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private  final RestTemplate restTemplate;

    @Autowired
    public UserController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public ResponseEntity<UserEntity> getMe(@AuthenticationPrincipal Jwt jwt) {
        String email = JwtUtils.extractEmailByJwt(jwt);
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserEntity user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping("/{username}")
    public  ResponseEntity<UserEntity> getUserByUsername(@PathVariable String username){
        return  ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping
    public List<UserEntity> getAllUsers(){
       return userService.getAllUsers();
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUserService")
    public ResponseEntity<String> getUserData() {
        return restTemplate.getForEntity("http://user-profile-service/users", String.class);
    }

    public ResponseEntity<String> fallbackUserService(Throwable t) {
        return ResponseEntity.ok("Fallback: User service is unavailable");
    }
}