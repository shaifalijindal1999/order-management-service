package com.ordermanagementservice.services;

import com.ordermanagementservice.models.common.ErrorResponse;
import com.ordermanagementservice.models.common.ProductModels.Product;
import com.ordermanagementservice.models.response.quote.FailedQuoteResponse;
import com.ordermanagementservice.models.response.quote.ProductQuoteResponse;
import com.ordermanagementservice.models.response.quote.QuoteResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductQuoteService {
    public QuoteResponse getProductQuote(String id, int quantity) {

        if (id == null || id.isEmpty() || quantity <= 0) {
            // product available response
            // product unavailable response
            // error response
            return new FailedQuoteResponse(new ErrorResponse("500","No product"), id, quantity);
        }
        Product productInfo = Product.
                builder().setId("1").setName("123").build();

        ProductQuoteResponse productQuoteResponse = new ProductQuoteResponse(productInfo);
        productQuoteResponse.calculatePayableAmount(quantity);
        return productQuoteResponse;
    }
}
