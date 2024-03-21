package com.ordermanagementservice.models.response.quote;

import com.ordermanagementservice.models.common.ErrorResponse;

public class FailedQuoteResponse implements QuoteResponse {
    ErrorResponse errorResponse;

    String productId; // quote failed for a product id

    int requestQuantity;

    public FailedQuoteResponse(ErrorResponse errorResponse, String productId, int requestQuantity) {
        this.errorResponse = errorResponse;
        this.productId = productId;
        this.requestQuantity = requestQuantity;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(int requestQuantity) {
        this.requestQuantity = requestQuantity;
    }
}
