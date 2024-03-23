package com.ordermanagementservice.services;

import com.ordermanagementservice.exceptions.SubmitOrderProductIdNullException;
import com.ordermanagementservice.exceptions.SubmitOrderProductListEmptyException;
import com.ordermanagementservice.exceptions.SubmitOrderProductListNullException;
import com.ordermanagementservice.models.common.ProductModels.Product;
import com.ordermanagementservice.models.request.SubmitOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubmitOrderRequestValidator implements RequestValidator {

    Logger logger = LoggerFactory.getLogger(SubmitOrderRequestValidator.class);

    @Autowired
    SubmitOrderRequest submitOrderRequest;

    SubmitOrderRequestValidator(SubmitOrderRequest submitOrderRequest) {
        this.submitOrderRequest = submitOrderRequest;
    }

    public void validateRequest(SubmitOrderRequest submitOrderRequest) throws SubmitOrderProductListNullException, SubmitOrderProductListEmptyException, SubmitOrderProductIdNullException {

        if (submitOrderRequest.getProductList() == null) {
            logger.error("Submit order failed - product list was null");
            throw new SubmitOrderProductListNullException("Product list cannot be null");
        }

        if (submitOrderRequest.getProductList().isEmpty()) {
            logger.error("Submit order failed - product list was empty");
            throw new SubmitOrderProductListEmptyException("Product list cannot be empty");
        }

        for (Product product : submitOrderRequest.getProductList()) {
            if (product.getId() == null) {
                logger.error("Submit order failed - product id was null");
                throw new SubmitOrderProductIdNullException("Product id cannot be null");
            }

            if (product.getId().isEmpty()) {
                logger.error("Submit order failed - product id was empty");
                throw new SubmitOrderProductIdNullException("Product id cannot be empty");
            }
        }

    }
}
