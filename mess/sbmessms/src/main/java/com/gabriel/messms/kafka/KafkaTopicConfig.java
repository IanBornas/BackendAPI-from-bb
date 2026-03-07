package com.gabriel.messms.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String MESSAGE_CREATED_TOPIC = "chat.message.created";

    @Bean
    public NewTopic messageCreatedTopic() {
        return TopicBuilder.name(MESSAGE_CREATED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
