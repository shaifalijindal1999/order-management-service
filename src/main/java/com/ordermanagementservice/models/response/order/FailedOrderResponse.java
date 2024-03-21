package com.ordermanagementservice.models.response.order;

import com.ordermanagementservice.models.common.ErrorResponse;
import com.ordermanagementservice.models.common.ProductModels.Product;
import com.ordermanagementservice.models.common.UserModels.User;

public class FailedOrderResponse implements OrderManagementResponse {

    ErrorResponse errorResponse;

    Product productInfo;

    User userInfo;

    public FailedOrderResponse(ErrorResponse errorResponse, Product product, User user) {
        this.errorResponse = errorResponse;
        this.productInfo = product;
        this.userInfo = user;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public Product getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(Product productInfo) {
        this.productInfo = productInfo;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }
}
