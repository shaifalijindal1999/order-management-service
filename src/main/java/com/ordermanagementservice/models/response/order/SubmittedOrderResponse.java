package com.ordermanagementservice.models.response.order;

import com.ordermanagementservice.models.common.ProductModels.Product;
import com.ordermanagementservice.models.common.UserModels.User;

public class SubmittedOrderResponse implements OrderManagementResponse {

    String orderId;

    int quantity;
    Product productInfo;
    User userInfo;
    float totalCost;
    OrderStatus orderStatus;

    public SubmittedOrderResponse() {

    }

    public SubmittedOrderResponse(String id, int quantity, Product product, User user) {
        this.orderId = id;
        this.quantity = quantity;
        this.productInfo = product;
        this.userInfo = user;
        this.orderStatus = OrderStatus.SUBMITTED;
    }

    public void setTotalCost() {
        this.totalCost = calculatePayableAmount();
    }

    private float calculatePayableAmount() {
        return this.quantity * this.productInfo.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
