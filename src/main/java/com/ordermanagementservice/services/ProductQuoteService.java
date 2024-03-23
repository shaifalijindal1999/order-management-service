package com.ordermanagementservice.services;

import com.ordermanagementservice.database.schema.ProductData;
import com.ordermanagementservice.models.common.ErrorResponse;
import com.ordermanagementservice.models.common.ProductModels.Product;
import com.ordermanagementservice.models.common.ProductModels.ProductInfo;
import com.ordermanagementservice.models.response.quote.FailedQuoteResponse;
import com.ordermanagementservice.models.response.quote.ProductQuoteResponse;
import com.ordermanagementservice.models.response.quote.QuoteResponse;
import com.ordermanagementservice.repositories.ProductDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class ProductQuoteService {

    @Autowired
    ProductDataRepository productDataRepository;
    public QuoteResponse getProductQuote(String id, int requestedQuantity) {

        WebClient webClient = WebClient.create("http://localhost:8888");

        Mono<ProductInfo> productResponseMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/product/{id}")
                        .queryParam("quantity", requestedQuantity)
                        .build(id))
                .retrieve()
                .bodyToMono(ProductInfo.class);

        return productResponseMono.map(productResponse -> {
            if (productResponse.getErrorResponse() == null) {
                Product productInfo = Product.
                        builder()
                        .setId(id)
                        .setName(productResponse.getName())
                        .setPrice(productResponse.getPrice())
                        .setRequestedQuantity(requestedQuantity)
                        .build();


                ProductQuoteResponse productQuoteResponse = new ProductQuoteResponse(productInfo, requestedQuantity, productResponse.getPrice());
                productQuoteResponse.setAmountToBePaid();
                return productQuoteResponse;
            }
            else {
                FailedQuoteResponse failedQuoteResponse = new FailedQuoteResponse(productResponse.getErrorResponse(),
                        productResponse.getId(),
                        requestedQuantity);
                return failedQuoteResponse;
            }

        }).block();
    }
}
