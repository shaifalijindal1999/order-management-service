package com.ordermanagementservice.services;

import com.ordermanagementservice.models.common.ProductModels.ProductInfo;
import com.ordermanagementservice.models.response.quote.FailedQuoteResponse;
import com.ordermanagementservice.models.response.quote.ProductQuoteResponse;
import com.ordermanagementservice.models.response.quote.QuoteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
class ProductQuoteServiceTest {

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.Builder webClientBuilder;


    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private WebClient webClient;

    @Value("${inventory.service.base-url}")
    private String inventoryServiceUri;
    @InjectMocks
    ProductQuoteService productQuoteService;

    @BeforeEach
    void setUp() {
        productQuoteService = new ProductQuoteService();
        MockitoAnnotations.openMocks(this);
        productQuoteService.inventoryServiceUri = "http://localhost:8888";
    }

    @Disabled
    void testGetProductQuote_Success() {
        // Arrange
        String id = "productId1";
        int requestedQuantity = 5;
        ProductInfo productInfo = ProductInfo.builder()
                .setId("id1")
                .setName("Product-1")
                .setRequestedQuantity(10)
                .build();

        // Mock successful response from WebClient
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ProductInfo.class)).thenReturn(Mono.just(productInfo));

        // Act
        QuoteResponse quoteResponse = productQuoteService.getProductQuote(id, requestedQuantity);

        // Assert
        assertInstanceOf(ProductQuoteResponse.class, quoteResponse);
    }
}