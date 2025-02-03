package com.kafka.ms.products.service;

import com.kafka.ms.products.model.CreateProductRequest;
import com.kafka.ms.products.model.constants.Constants;
import com.kafka.ms.products.model.event.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Qualifier("ProductServiceImpl")
public class ProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //key-value pair message <String,ProductCreateEvent>
    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductRequest product){
        String productId = UUID.randomUUID().toString();
        //todo: Persist product details into database table before publishing an Event
        ProductCreatedEvent productCreateEvent = new ProductCreatedEvent(productId,
                            product.getTitle(),
                            product.getPrice(),
                            product.getQuantity());
        /*
        // asynchronous communication style without worry about whether message is stored successfully
        kafkaTemplate.send(Constants.PRODUCT_CREATED_EVENTS_TOPIC, productId, productCreateEvent);
        */

        //java asynchronous callback function when asynchronous operation completed it will trigger the logic
        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
                kafkaTemplate.send(Constants.PRODUCT_CREATED_EVENTS_TOPIC, productId, productCreateEvent);

        future.whenComplete((result,exception) -> {
           if(exception != null){
               logger.error("Failed to send product message: " + exception.getMessage());
           } else {
                logger.info("Successfully sent product message: " + result.getRecordMetadata());
           }
        });
        /* future.join() makes it synchronous communication style it will wait until future object complete processing it's logic
            if you don't want to wait result just simple remove this future.join()
            above future.whenComplete will still run after it receive result from broker
            and application will continue without waiting for it. */
       // future.join();
        return productId;
    }


 /*
    // you can use above future.join() to make it synchronous or use below call
    @Override
    public String createProduct(CreateProductRequest product){
        String productId = UUID.randomUUID().toString();
        //todo: Persist product details into database table before publishing an Event
        ProductCreatedEvent productCreateEvent = new ProductCreatedEvent(productId,
                product.getTitle(),
                product.getPrice(),
                product.getQuantity());
        try {
            SendResult<String, ProductCreatedEvent> result =
                    kafkaTemplate.send(Constants.PRODUCT_CREATED_EVENTS_TOPIC, productId, productCreateEvent).get();
            logger.info("Successfully sent product message: " + result.getRecordMetadata());
            logger.info("Partition: " + result.getRecordMetadata().partition());
            logger.info("Topic: " + result.getRecordMetadata().topic());
            logger.info("Offset: " + result.getRecordMetadata().offset());
        }catch (Exception e){
            logger.error("Failed to send product message: " + e.getMessage());
        }
        return productId;
    }
 */
}
