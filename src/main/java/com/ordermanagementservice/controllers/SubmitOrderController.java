package com.ordermanagementservice.controllers;

import com.ordermanagementservice.models.common.ErrorResponse;
import com.ordermanagementservice.models.request.SubmitOrderRequest;
import com.ordermanagementservice.models.response.order.FailedOrderResponse;
import com.ordermanagementservice.models.response.order.OrderManagementResponse;
import com.ordermanagementservice.models.response.order.OrderStatus;
import com.ordermanagementservice.models.response.order.SubmittedOrderResponse;
import com.ordermanagementservice.services.OrderProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PathVariable;
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


    @PostMapping(path = "/product/{productId}/orders")
    public CompletableFuture<ResponseEntity<OrderManagementResponse>> submitOrder(@PathVariable("productId") String productId, @RequestBody SubmitOrderRequest submitOrderRequest) {

        // Generate random id for order
        String orderId = UUID.randomUUID().toString();

        // publish order event to kafka topic
        CompletableFuture<SendResult<String, String >> producerResponse = orderProducerService.submitOrder(submitOrderRequest, orderId);

        // whenever order is submitted, send response to user
        return producerResponse.thenApplyAsync(result -> {
            OrderManagementResponse orderDataResponse = new SubmittedOrderResponse(orderId, submitOrderRequest.getQuantity(), submitOrderRequest.getProduct(), submitOrderRequest.getUser());
            logger.info("method=submitOrder message=Order submitted");
            return ResponseEntity.status(HttpStatus.OK).body(orderDataResponse);
        }).exceptionally(ex -> {
            OrderManagementResponse failedOrderResponse = new FailedOrderResponse(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), "Failed to submit order"), submitOrderRequest.getProduct(), submitOrderRequest.getUser());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failedOrderResponse);
        });
    }
}
