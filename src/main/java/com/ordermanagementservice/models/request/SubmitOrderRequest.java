package com.ordermanagementservice.models.request;

import com.ordermanagementservice.models.common.ProductModels.Product;
import com.ordermanagementservice.models.common.UserModels.User;

public class SubmitOrderRequest {

    int quantity;
    Product product;
    User user;


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
