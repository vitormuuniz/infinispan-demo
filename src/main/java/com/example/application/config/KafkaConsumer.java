package com.example.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "example-topic", groupId = "example-group")
    public void consumeMessage(String message) {
        LOGGER.info("Received message through Kafka: {}", message);
    }
}
