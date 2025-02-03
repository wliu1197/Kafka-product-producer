package com.kafka.ms.products.configuration;

import com.kafka.ms.products.model.constants.Constants;
import com.kafka.ms.products.model.event.ProductCreatedEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
/*
    // This part of code using java to configure producer acks and creates KafkaTemplate spring bean from ProducerFactory
    // for ProductCreatedEvent then we can inject this KafkaTemplate to the business logic

    @Value("${kafka.producer.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${kafka.producer.value-serializer}")
    private String valueSerializer;
    @Value("${spring.kafka.producer.acks}")
    private String acks;
    @Value("${spring.kafka.producer.retries}")
    private String retries;
    @Value("${kafka.producer.properties.retry.backoff.ms}")
    private String retryBackOff;
    @Value("${kafka.producer.properties.delivery.timeout.ms}")
    private String deliveryTimeout;
    @Value("${kafka.producer.properties.linger.ms}")
    private String linger;
    @Value("${kafka.producer.properties.request.timeout.ms}")
    private String requestTimeout;

    @Value("${kafka.producer.properties.enable.idempotence}")
    private boolean idempotence;
    @Value("${kafka.producer.properties.max.in.flight.request.per.connection}")
    private Integer inflightRequest;

    Map<String,Object> producerConfigs() {
        Map<String,Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,keySerializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,valueSerializer);
        config.put(ProducerConfig.ACKS_CONFIG,acks);
        config.put(ProducerConfig.RETRIES_CONFIG,retries);
        config.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG,retryBackOff);
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,deliveryTimeout);
        config.put(ProducerConfig.LINGER_MS_CONFIG,linger);
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,requestTimeout);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,idempotence);
        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,inflightRequest);
        return config;
    }

    @Bean
    ProducerFactory<String, ProductCreatedEvent> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }
    @Bean
    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate(){
        return new KafkaTemplate<String, ProductCreatedEvent>(producerFactory());
    }
*/
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
