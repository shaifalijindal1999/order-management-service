package com.ordermanagementservice.services;

import com.ordermanagementservice.exceptions.SubmitOrderProductIdNullException;
import com.ordermanagementservice.exceptions.SubmitOrderProductListEmptyException;
import com.ordermanagementservice.exceptions.SubmitOrderProductListNullException;
import com.ordermanagementservice.models.common.ProductModels.Product;
import com.ordermanagementservice.models.request.SubmitOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class SubmitOrderRequestValidatorTest {

    @Mock
    private Product product;

    @InjectMocks
    private SubmitOrderRequestValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void validateRequest_NullProductList_ShouldThrowException() {
        SubmitOrderRequest request = new SubmitOrderRequest();
        request.setProductList(null);

        // Act and assert
        assertThrows(SubmitOrderProductListNullException.class, () -> validator.validateRequest(request));
    }

    @Test
    void validateRequest_EmptyProductListShouldThrowException() {
        SubmitOrderRequest request = new SubmitOrderRequest();
        request.setProductList(Collections.emptyList());

        assertThrows(SubmitOrderProductListEmptyException.class, () -> validator.validateRequest(request));
    }

    @Test
    void validateRequest_NullProductId_ShouldThrowException() {
        when(product.getId()).thenReturn(null);

        SubmitOrderRequest request = new SubmitOrderRequest();
        request.setProductList(Collections.singletonList(product));

        assertThrows(SubmitOrderProductIdNullException.class, () -> validator.validateRequest(request));
    }

    @Test
    void validateRequest_EmptyProductId_ShouldThrowException() {
        when(product.getId()).thenReturn("");

        SubmitOrderRequest request = new SubmitOrderRequest();
        request.setProductList(Collections.singletonList(product));

        assertThrows(SubmitOrderProductIdNullException.class, () -> validator.validateRequest(request));
    }

    @Test
    void validateRequest_ValidRequest_ShouldNotThrowException() throws Exception {
        when(product.getId()).thenReturn("123");

        SubmitOrderRequest request = new SubmitOrderRequest();
        request.setProductList(Collections.singletonList(product));

        validator.validateRequest(request); // No exception should be thrown
    }
}
