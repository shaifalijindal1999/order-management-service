package com.ordermanagementservice.services;

import com.ordermanagementservice.models.events.Event;
import com.ordermanagementservice.models.events.SubmitOrderEvent;
import com.ordermanagementservice.models.request.SubmitOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderProducerService {

    private final Logger logger = LoggerFactory.getLogger(OrderProducerService.class);

    private static final String TOPIC = "order-topic";


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public CompletableFuture<SendResult<String, String>> submitOrder(SubmitOrderRequest request, String orderId) {

        // generate uuid here


        SubmitOrderEvent orderEvent = new SubmitOrderEvent(orderId);

//        CompletableFuture<SendResult<String, Event>> future =
//                this.template.send(TOPIC, orderEvent);

        var future =  this.kafkaTemplate.send(TOPIC, orderId);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("method=submitOrder message=order successfully submitted");
                future.complete(result);
            }
            else {
                logger.error("method=submitOrder message=failed to submit order error={}", ex.getMessage());
                future.completeExceptionally(ex);
            }
        });
        return future;
    }
}
