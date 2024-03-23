package com.ordermanagementservice.models.response.order;

import com.ordermanagementservice.models.common.ErrorResponse;
import com.ordermanagementservice.models.common.ProductModels.Product;
import com.ordermanagementservice.models.request.SubmitOrderRequest;
import lombok.Getter;

@Getter
public class SubmittedOrderResponse implements OrderManagementResponse {

    ErrorResponse errorResponse;

    String orderId;

    float totalCost;

    OrderStatus orderStatus;

    public SubmittedOrderResponse(String id) {
        this.orderId = id;
        this.orderStatus = OrderStatus.SUBMITTED;
        this.totalCost = 0;
    }

    public SubmittedOrderResponse(String id, ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
        this.orderId = id;
        this.orderStatus = OrderStatus.NOT_SUBMITTED;
        this.totalCost = 0;

    }
    public void setFailedOrderResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public void setTotalCost(SubmitOrderRequest request) {
        for (Product product : request.getProductList()) {
            if (product.getRequestedQuantity() == 0) {
                this.totalCost += product.getPrice();
            }
            this.totalCost += product.getPrice() * product.getRequestedQuantity();
        }

    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
