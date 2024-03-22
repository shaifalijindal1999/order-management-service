package com.ordermanagementservice.models.response.order;

import com.ordermanagementservice.models.common.ErrorResponse;
import com.ordermanagementservice.models.common.ProductModels.Product;
import com.ordermanagementservice.models.common.UserModels.User;
import lombok.Getter;

public class FailedOrderResponse implements OrderManagementResponse {

    ErrorResponse errorResponse;
    String productName;
    String productId;

    User user;

    OrderStatus orderStatus;

    public FailedOrderResponse(ErrorResponse errorResponse, Product product, User user, String productId) {
        this.errorResponse = errorResponse;
        this.productName = product.getName();
        this.productId = productId;
        this.user = user;
        this.orderStatus = OrderStatus.NOT_SUBMITTED;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
