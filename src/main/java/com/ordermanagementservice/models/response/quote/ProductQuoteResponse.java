package com.ordermanagementservice.models.response.quote;

import com.ordermanagementservice.models.common.ProductModels.Product;

public class ProductQuoteResponse implements QuoteResponse {

    private Product product;

    private float amountToBePaid;

    // Constructors
    public ProductQuoteResponse(Product product) {
        this.product = product;
    }

    public void calculatePayableAmount(int requestedQuantity) {
        this.setAmountToBePaid(this.product.getPrice() * requestedQuantity);
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

    public void setAmountToBePaid(float amountToBePaid) {
        this.amountToBePaid = amountToBePaid;
    }
}

