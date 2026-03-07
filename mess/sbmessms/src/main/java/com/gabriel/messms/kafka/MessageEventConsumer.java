package com.gabriel.messms.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageEventConsumer {

    Logger logger = LoggerFactory.getLogger(MessageEventConsumer.class);

    @KafkaListener(topics = KafkaTopicConfig.MESSAGE_CREATED_TOPIC, groupId = "chat-message-group")
    public void consume(MessageCreatedEvent event) {
        logger.info("Received message event: {}", event.toString());
        // Extend here: push notification, broadcast via WebSocket, update delivery status, etc.
    }
}
