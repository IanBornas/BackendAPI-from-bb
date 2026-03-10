package com.gabriel.messms.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageEventConsumer.class);
    private final SimpMessagingTemplate messagingTemplate;

    public MessageEventConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = KafkaTopicConfig.MESSAGE_CREATED_TOPIC, groupId = "chat-message-group")
    public void consume(MessageCreatedEvent event) {
        logger.info("Received message event: {}", event.toString());
        String destination = "/topic/conversation/" + event.getConversationId();
        messagingTemplate.convertAndSend(destination, event);
    }
}
