package com.ordermanagementservice.services;

import com.ordermanagementservice.constants.Constants;
import com.ordermanagementservice.models.request.SubmitOrderRequest;
import org.apache.kafka.common.protocol.types.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OrderProducerServiceTest {

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;
    @InjectMocks
    OrderProducerService orderProducerService;

    String mockOrderId = "mockOrderId";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        orderProducerService.kafkaTemplate = kafkaTemplate;
    }

    @Test
    void submitOrder_SubmitOrderSuccessful() throws ExecutionException, InterruptedException {

        // Arrange
        SubmitOrderRequest request = new SubmitOrderRequest();

        CompletableFuture<Constants.StatusMessages> expectedResult =
                CompletableFuture.completedFuture(Constants.StatusMessages.SUBMITTED);

        CompletableFuture<SendResult<String, String>> kafkaFuture = createMockedKafkaFuture();

        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(kafkaFuture);

        // Act
        CompletableFuture<Constants.StatusMessages> result =
                orderProducerService.submitOrder(request, mockOrderId);

        // Assert
        assertEquals(expectedResult.get(), result.get());
        verify(kafkaTemplate).send(eq(Constants.KAFKA_TOPIC), eq(mockOrderId));
    }

    @Test
    void submitOrder_SubmitOrderFailure() throws ExecutionException, InterruptedException {

        // Arrange
        SubmitOrderRequest request = new SubmitOrderRequest();

        CompletableFuture<Constants.StatusMessages> expectedResult =
                CompletableFuture.completedFuture(Constants.StatusMessages.NOT_SUBMITTED);

        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(createMockedFailedFuture());

        // Act
        CompletableFuture<Constants.StatusMessages> result =
                orderProducerService.submitOrder(request, mockOrderId);

        // Assert
        assertEquals(expectedResult.get(), result.get());
        verify(kafkaTemplate).send(eq(Constants.KAFKA_TOPIC), eq(mockOrderId));
    }

    private CompletableFuture<SendResult<String, String>> createMockedKafkaFuture() {
        CompletableFuture<SendResult<String,String>> future = new CompletableFuture<>();
        future.complete(null);
        return future;
    }

    // Helper method to create a failed CompletableFuture
    private CompletableFuture<SendResult<String, String>> createMockedFailedFuture() {
        CompletableFuture<SendResult<String,String>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Kafka send failed"));
        return future;
    }
}