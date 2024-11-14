package com.generoso.kafka.classic.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import static com.generoso.kafka.classic.config.Common.TOPIC_NAME;

@Configuration
public class KafkaConsumerConfig {

    @KafkaListener(topics = TOPIC_NAME)
    public void listen(ConsumerRecord<String, String> record) {
        String message = record.value();
        System.out.println("Received message: " + message);
    }
}
