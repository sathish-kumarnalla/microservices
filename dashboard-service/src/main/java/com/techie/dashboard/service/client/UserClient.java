package com.techie.dashboard.service.client;

import com.techie.dashboard.service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-profile-service", url = "http://localhost:8081")
public interface UserClient {
    @GetMapping("/users/{username}")
    UserDTO getUser(@PathVariable String username);
}
