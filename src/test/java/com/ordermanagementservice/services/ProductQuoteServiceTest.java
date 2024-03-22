package com.ordermanagementservice.services;

import com.ordermanagementservice.database.schema.ProductData;
import com.ordermanagementservice.models.response.quote.FailedQuoteResponse;
import com.ordermanagementservice.models.response.quote.ProductQuoteResponse;
import com.ordermanagementservice.models.response.quote.QuoteResponse;
import com.ordermanagementservice.repositories.ProductDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductQuoteServiceTest {
    @Mock
    ProductDataRepository mockProductDataRepository;
    ProductQuoteService productQuoteService;
    String mockProductId = "mockProductId";
    String mockProductName = "mockProductName";
    int mockRequestedQuantity = 1;

    @BeforeEach
    void setUp() {
        mockProductDataRepository = mock(ProductDataRepository.class);
        productQuoteService = new ProductQuoteService();
        productQuoteService.productDataRepository = mockProductDataRepository;
    }

    @Test
    void getProductQuote_validProductId_ProductQuoteResponse() {

        // Arrange
        Optional<ProductData> mockOptional = mock(Optional.class);
        ProductData mockProductData = createMockProductDataFromDatabase();
        when(mockProductDataRepository.findById(mockProductId)).thenReturn(mockOptional);
        when(mockOptional.orElse(null)).thenReturn(mockProductData);

        // Act
        QuoteResponse response = productQuoteService.getProductQuote(mockProductId, mockRequestedQuantity);

        // Assert
        assertInstanceOf(ProductQuoteResponse.class, response);
        ProductQuoteResponse productQuoteResponse = (ProductQuoteResponse) response;
        assertEquals(productQuoteResponse.getProduct().getPrice(), mockProductData.getPrice());
        assertEquals(productQuoteResponse.getProduct().getId(), mockProductData.getId());
        assertEquals(productQuoteResponse.getProduct().getName(), mockProductData.getName());
        assertEquals(productQuoteResponse.getProduct().getRequestedQuantity(), mockRequestedQuantity);
    }

    @Test
    void getProductQuote_noProductIdFound_FailureNoProductFoundResponse() {

        // Arrange
        Optional<ProductData> mockOptional = mock(Optional.class);
        when(mockProductDataRepository.findById(mockProductId)).thenReturn(mockOptional);
        when(mockOptional.orElse(null)).thenReturn(null);

        // Act
        QuoteResponse response = productQuoteService.getProductQuote(mockProductId, mockRequestedQuantity);

        // Assert
        assertInstanceOf(FailedQuoteResponse.class, response);
        FailedQuoteResponse failedResponse = (FailedQuoteResponse) response;
        assertEquals(failedResponse.getErrorResponse().getFailReason(), "No product found with given id");
        assertEquals(failedResponse.getErrorResponse().getCode(), "500");
    }

    @Test
    void getProductQuote_lessQuantity_FailureNoProductAvailableResponse() {

        // Arrange
        Optional<ProductData> mockOptional = mock(Optional.class);
        ProductData mockProductData = createMockProductDataFromDatabase();
        when(mockProductDataRepository.findById(mockProductId)).thenReturn(mockOptional);
        when(mockOptional.orElse(null)).thenReturn(mockProductData);

        // Act
        QuoteResponse response = productQuoteService.getProductQuote(mockProductId, 5);

        // Assert
        assertInstanceOf(FailedQuoteResponse.class, response);
        FailedQuoteResponse failedResponse = (FailedQuoteResponse) response;
        assertEquals(failedResponse.getErrorResponse().getFailReason(), "Product unavailable");
        assertEquals(failedResponse.getErrorResponse().getCode(), "500");
    }

    @Test
    void getProductQuote_invalidProductId_FailureInvalidRequestResponse() {

        // Arrange
        Optional<ProductData> mockOptional = mock(Optional.class);
        when(mockProductDataRepository.findById(mockProductId)).thenReturn(mockOptional);
        when(mockOptional.orElse(null)).thenReturn(null);

        // Act
        QuoteResponse response = productQuoteService.getProductQuote("", 1);

        // Assert
        assertInstanceOf(FailedQuoteResponse.class, response);
        FailedQuoteResponse failedResponse = (FailedQuoteResponse) response;
        assertEquals(failedResponse.getErrorResponse().getFailReason(), "Invalid request");
        assertEquals(failedResponse.getErrorResponse().getCode(), "500");
    }

    private ProductData createMockProductDataFromDatabase() {
        ProductData mockProductData = new ProductData();
        mockProductData.setId(mockProductId);
        mockProductData.setName(mockProductName);
        mockProductData.setPrice(10);
        mockProductData.setQuantity(1);
        return mockProductData;
    }
}