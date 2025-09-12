package com.techie.user.profile;

import com.techie.event.model.events.UserCreatedEvent;
import com.techie.user.profile.entity.UserEntity;
import com.techie.user.profile.service.UserService;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "user-events" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserEventIntegrationTest {

    @Autowired
    private UserService userService;

    private KafkaConsumer<String, byte[]> consumer;
    @Autowired
    private KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    @BeforeAll
    void setupConsumer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("user-events"));
    }

    @Disabled
    @Test
    void testUserCreatedEventPublished() throws Exception {
        // Create a user with roles as a Set
        Set<String> roles = Set.of("USER");
        UserEntity user = new UserEntity(null, "testuser", "test@example.com", "tenant-001", roles);
        userService.createUser(user);

        // Poll Kafka for the event
        ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofSeconds(5));
        assertFalse(records.isEmpty(), "No Kafka records received");

        // Deserialize Avro payload
        ConsumerRecord<String, byte[]> record = records.iterator().next();
        DatumReader<UserCreatedEvent> reader = new SpecificDatumReader<>(UserCreatedEvent.class);
        Decoder decoder = DecoderFactory.get().binaryDecoder(record.value(), null);
        UserCreatedEvent event = reader.read(null, decoder);

        // Validate event content
        assertEquals("testuser", event.getName().toString());
        assertEquals("test@example.com", event.getEmail().toString());
        assertEquals("USER", event.getEventType().toString()); // Adjust if needed
    }
}