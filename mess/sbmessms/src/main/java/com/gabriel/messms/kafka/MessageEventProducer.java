package com.gabriel.messms.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageEventProducer {

    Logger logger = LoggerFactory.getLogger(MessageEventProducer.class);

    @Autowired
    private KafkaTemplate<String, MessageCreatedEvent> kafkaTemplate;

    public void sendMessageCreatedEvent(MessageCreatedEvent event) {
        String key = String.valueOf(event.getConversationId());
        kafkaTemplate.send(KafkaTopicConfig.MESSAGE_CREATED_TOPIC, key, event)
                .addCallback(
                        result -> logger.info("Message event sent | topic={} partition={} offset={}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset()),
                        ex -> logger.error("Failed to send message event: {}", ex.getMessage(), ex)
                );
    }
}
