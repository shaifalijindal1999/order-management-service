package com.ordermanagementservice.models.response.order;

import com.ordermanagementservice.models.common.ProductModels.Product;
import com.ordermanagementservice.models.common.UserModels.User;
import lombok.Getter;

@Getter
public class SubmittedOrderResponse implements OrderManagementResponse {

    String orderId;

    int quantity;

    String productName;

    String productId;

    User user;

    float totalCost;

    OrderStatus orderStatus;

    public SubmittedOrderResponse(String id, int quantity, Product product, User user, String productId) {
        this.orderId = id;
        this.quantity = quantity;
        this.productName = product.getName();
        this.productId = productId;
        this.user = user;
        this.orderStatus = OrderStatus.SUBMITTED;
        this.totalCost = quantity * product.getPrice();
    }

    public void setTotalCost(Product product) {
        this.totalCost = quantity * product.getPrice();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
