package com.techie.auth.server.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuditEventListener {

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        System.out.println("✅ Login success: " + event.getAuthentication().getName());
        log.info("✅ Login success: {}", event.getAuthentication().getName());
    }
}
