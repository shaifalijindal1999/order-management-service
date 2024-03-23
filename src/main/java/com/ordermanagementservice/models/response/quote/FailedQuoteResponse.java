package com.ordermanagementservice.models.response.quote;

import com.ordermanagementservice.models.common.ErrorResponse;
import lombok.Getter;

@Getter
public class FailedQuoteResponse implements QuoteResponse {
    ErrorResponse errorResponse;

    String productId; // quote failed for a product id

    int requestQuantity;

    public FailedQuoteResponse(ErrorResponse errorResponse, String productId, int requestQuantity) {
        this.errorResponse = errorResponse;
        this.productId = productId;
        this.requestQuantity = requestQuantity;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setRequestQuantity(int requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public String getProductId() {
        return productId;
    }

    public int getRequestQuantity() {
        return requestQuantity;
    }
}
