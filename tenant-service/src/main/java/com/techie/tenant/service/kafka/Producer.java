package com.techie.tenant.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class Producer {

    @Autowired
    KafkaTemplate kafkaTemplate;
}
