package com.ordermanagementservice.models.response.quote;

import com.ordermanagementservice.models.common.ProductModels.Product;

public class ProductQuoteResponse implements QuoteResponse {

    private Product product;

    private int requestedQuantity;

    private float amountToBePaid;

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    // Constructors
    public ProductQuoteResponse(Product product, int requestedQuantity) {
        this.product = product;
        this.requestedQuantity = requestedQuantity;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getAmountToBePaid() {
        return amountToBePaid;
    }

    public void setAmountToBePaid() {
        this.amountToBePaid = this.product.getPrice() * this.requestedQuantity;
    }
}

