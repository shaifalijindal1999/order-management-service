package com.ordermanagementservice.services;

import com.ordermanagementservice.constants.Constants;
import com.ordermanagementservice.models.request.SubmitOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.ordermanagementservice.constants.Constants.KAFKA_TOPIC;

@Service
public class OrderProducerService {

    private final Logger logger = LoggerFactory.getLogger(OrderProducerService.class);

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public CompletableFuture<Constants.StatusMessages> submitOrder(SubmitOrderRequest request, String orderId) {

        // publish order to kafka to topic asynchronously
        var future =  this.kafkaTemplate.send(KAFKA_TOPIC, orderId);
        CompletableFuture<Constants.StatusMessages> submitResult = new CompletableFuture<>();

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("method=submitOrder message=order successfully submitted");
                future.complete(result);
                submitResult.complete(Constants.StatusMessages.SUBMITTED);
            }
            else {
                logger.error("method=submitOrder message=failed to submit order error={}", ex.getMessage());
                future.completeExceptionally(ex);
                submitResult.complete(Constants.StatusMessages.NOT_SUBMITTED);
            }
        });

        // return result callback with submit status
        return submitResult;
    }
}
