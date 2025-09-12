package com.techie.gateway.controller;

import com.techie.gateway.service.RoutingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    private final RoutingService routingService;

    public GatewayController(RoutingService routingService) {
        this.routingService = routingService;
    }

    /**
     * Routes request to downstream service.
     * Example: /gateway/user-profile-service/api/v1/users?useCircuitBreaker=true
     */
    @GetMapping("/{service}/{path}")
    public ResponseEntity<String> route(@PathVariable String service,
                                        @PathVariable String path,
                                        @RequestParam(defaultValue = "false") boolean useCircuitBreaker) {
        return routingService.route(service, path, useCircuitBreaker);
    }
}