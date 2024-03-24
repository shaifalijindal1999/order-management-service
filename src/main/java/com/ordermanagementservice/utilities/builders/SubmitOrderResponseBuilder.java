package com.ordermanagementservice.utilities.builders;

import com.ordermanagementservice.models.common.ErrorResponse;
import com.ordermanagementservice.models.request.SubmitOrderRequest;
import com.ordermanagementservice.models.response.order.OrderManagementResponse;
import com.ordermanagementservice.models.response.order.SubmittedOrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


@Component
public class SubmitOrderResponseBuilder {
    public CompletableFuture<ResponseEntity<OrderManagementResponse>> buildSubmitBadRequestErrorResponse(SubmitOrderRequest request, String message, String orderId) {
        CompletableFuture<ResponseEntity<OrderManagementResponse>> errorSubmitOrderResponse = new CompletableFuture<>();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                message);
        OrderManagementResponse failedOrderResponse = new SubmittedOrderResponse(null, errorResponse);
        errorSubmitOrderResponse.complete(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failedOrderResponse));
        return errorSubmitOrderResponse;

    }

    public ResponseEntity<OrderManagementResponse> buildSubmitInternalServerErrorResponse(SubmitOrderRequest request, String message, String orderId) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(),
                message);
        OrderManagementResponse failedOrderResponse = new SubmittedOrderResponse(null, errorResponse);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failedOrderResponse);

    }

    public ResponseEntity<OrderManagementResponse> buildSubmitSuccessResponse(SubmitOrderRequest request, String orderId) {
        SubmittedOrderResponse orderDataResponse =
                new SubmittedOrderResponse(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDataResponse);

    }
}
