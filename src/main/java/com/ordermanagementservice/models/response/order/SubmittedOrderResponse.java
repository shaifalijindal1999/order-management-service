package com.ordermanagementservice.models.response.order;

import com.ordermanagementservice.models.common.ErrorResponse;
import com.ordermanagementservice.models.common.ProductModels.Product;
import com.ordermanagementservice.models.request.SubmitOrderRequest;
import lombok.Getter;

@Getter
public class SubmittedOrderResponse implements OrderManagementResponse {

    ErrorResponse errorResponse;

    String orderId;

    OrderStatus orderStatus;

    public SubmittedOrderResponse(String id) {
        this.orderId = id;
        this.orderStatus = OrderStatus.SUBMITTED;
    }

    public SubmittedOrderResponse(String id, ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
        this.orderId = id;
        this.orderStatus = OrderStatus.NOT_SUBMITTED;

    }
    public void setFailedOrderResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
