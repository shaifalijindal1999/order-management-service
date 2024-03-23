package com.ordermanagementservice.models.response.quote;

import com.ordermanagementservice.models.common.ProductModels.Product;
import lombok.Getter;

@Getter
public class ProductQuoteResponse implements QuoteResponse {

    private Product product;

    private int requestedQuantity;

    private float amountToBePaid;

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    // Constructors
    public ProductQuoteResponse(Product product, int requestedQuantity, float amountToBePaid) {
        this.product = product;
        this.requestedQuantity = requestedQuantity;
        this.amountToBePaid = amountToBePaid;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    public void setAmountToBePaid() {
        this.amountToBePaid = this.product.getPrice() * this.requestedQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public float getAmountToBePaid() {
        return amountToBePaid;
    }

    public void setAmountToBePaid(float amountToBePaid) {
        this.amountToBePaid = amountToBePaid;
    }
}

