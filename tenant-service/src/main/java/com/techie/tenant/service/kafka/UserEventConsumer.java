package com.techie.tenant.service.kafka;

import com.techie.event.model.events.UserCreatedEvent;
import com.techie.tenant.service.avro.AvroDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventConsumer {

    private final AvroDeserializer avroDeserializer;

    public UserEventConsumer(AvroDeserializer avroDeserializer) {
        this.avroDeserializer = avroDeserializer;
    }

    @KafkaListener(topics = "user-events", groupId = "tenant-consumer-group")
    public void consume(byte[] message) {
        UserCreatedEvent event = avroDeserializer.deserialize(message);
        System.out.println("✅ Received user: " + event.getName() + " (" + event.getEmail() + ")");

        // TODO: Trigger tenant onboarding logic here
    }
}