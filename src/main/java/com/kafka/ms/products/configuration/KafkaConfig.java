package com.kafka.ms.products.configuration;

import com.kafka.ms.products.model.constants.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    NewTopic createTopic(){
        // we can have 3 broker servers running so we can replicate messages to provide high availability
        // as have 3 broker servers so replicas set to 3
        // 3 partitions in each broker
        // minimum 2 replicas that acknowledge successful stored of message it will return success to producer otherwise it returns exception
        return TopicBuilder.name(Constants.PRODUCT_CREATED_EVENTS_TOPIC)
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas","2"))
                .build();
    }
}
