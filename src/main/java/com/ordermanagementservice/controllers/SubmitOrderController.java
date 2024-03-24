package com.ordermanagementservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ordermanagementservice.constants.Constants;
import com.ordermanagementservice.models.request.SubmitOrderRequest;
import com.ordermanagementservice.models.response.order.OrderManagementResponse;
import com.ordermanagementservice.services.OrderProducerService;
import com.ordermanagementservice.utilities.builders.SubmitOrderResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class SubmitOrderController {

    private final Logger logger = LoggerFactory.getLogger(SubmitOrderController.class);

    @Autowired
    private OrderProducerService orderProducerService;

    @Autowired
    private SubmitOrderResponseBuilder submitOrderResponseBuilder;


    @PostMapping(path = "/submit")
    public CompletableFuture<ResponseEntity<OrderManagementResponse>> submitOrder(@RequestBody SubmitOrderRequest submitOrderRequest) throws JsonProcessingException {

        // Generate random id for order
        String orderId = UUID.randomUUID().toString();

        try {
            // publish order event to kafka topic
            CompletableFuture<Constants.StatusMessages> producerResponse =
                    orderProducerService.submitOrder(submitOrderRequest, orderId);

            // whenever order is submitted, send response to user
            return producerResponse.thenApplyAsync(result -> {
                if (Constants.StatusMessages.SUBMITTED == result) {
                    logger.info("method=submitOrder message=Order successfully submitted to kafka");
                    return submitOrderResponseBuilder.
                            buildSubmitSuccessResponse(submitOrderRequest, orderId);
                }
                else {
                    return submitOrderResponseBuilder.
                            buildSubmitInternalServerErrorResponse(submitOrderRequest, "Failed to submit order!", orderId);
                }
            }).exceptionally(ex -> submitOrderResponseBuilder.buildSubmitInternalServerErrorResponse(submitOrderRequest, ex.getMessage(), orderId));
        } catch (Exception e) {
            return submitOrderResponseBuilder.
                    buildSubmitBadRequestErrorResponse(submitOrderRequest, e.getMessage(), orderId);
        }
    }
}
