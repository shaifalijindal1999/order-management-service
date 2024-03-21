package com.ordermanagementservice.controllers;

import com.ordermanagementservice.models.request.SubmitOrderRequest;
import com.ordermanagementservice.models.response.order.OrderStatus;
import com.ordermanagementservice.models.response.order.SubmittedOrderResponse;
import com.ordermanagementservice.services.OrderProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class SubmitOrderController {

    private final Logger logger = LoggerFactory.getLogger(SubmitOrderController.class);

    @Autowired
    private OrderProducerService orderProducerService;

    @PostMapping(path = "/orders")
    public CompletableFuture<ResponseEntity<SubmittedOrderResponse>> submitOrder(@RequestBody SubmitOrderRequest submitOrderRequest) {
        CompletableFuture<SendResult<String, String >> producerResponse = orderProducerService.submitOrder(submitOrderRequest);

        return producerResponse.thenApplyAsync(result -> {
            SubmittedOrderResponse orderDataResponse = new SubmittedOrderResponse();
            orderDataResponse.setOrderStatus(OrderStatus.SUBMITTED);
            orderDataResponse.setProductInfo(submitOrderRequest.getProduct());
            orderDataResponse.setUserInfo(submitOrderRequest.getUser());
            orderDataResponse.setTotalCost(submitOrderRequest.getQuantity() * submitOrderRequest.getProduct().getPrice());
            logger.info("method=submitOrder message=Order submitted");
            return ResponseEntity.status(HttpStatus.OK).body(orderDataResponse);
        }).exceptionally(ex -> {
            SubmittedOrderResponse orderDataResponse = new SubmittedOrderResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(orderDataResponse);
        });
    }
}
