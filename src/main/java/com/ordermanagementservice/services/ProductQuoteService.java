package com.ordermanagementservice.services;

import com.ordermanagementservice.models.common.ProductModels.Product;
import com.ordermanagementservice.models.common.ProductModels.ProductInfo;
import com.ordermanagementservice.models.response.quote.FailedQuoteResponse;
import com.ordermanagementservice.models.response.quote.ProductQuoteResponse;
import com.ordermanagementservice.models.response.quote.QuoteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductQuoteService {

    @Value("${inventory.service.base-url}")
    public String inventoryServiceUri;
    public QuoteResponse getProductQuote(String id, int requestedQuantity) {

        WebClient webClient = WebClient.create(inventoryServiceUri);

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
                return new FailedQuoteResponse(productResponse.getErrorResponse(),
                        productResponse.getId(),
                        requestedQuantity);
            }

        }).block();
    }
}
