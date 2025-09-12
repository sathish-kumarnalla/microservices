package com.techie.tenant.service.kafka;

import com.techie.event.model.events.UserCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;

public class Consumer {

    @KafkaListener(topics = "user-events", groupId = "tenant-group")
    public void handleUserCreated(UserCreatedEvent event) {
        // Use event payload to provision tenant
    }
}
