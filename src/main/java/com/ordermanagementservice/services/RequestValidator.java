package com.ordermanagementservice.services;

import com.ordermanagementservice.exceptions.SubmitOrderProductIdNullException;
import com.ordermanagementservice.exceptions.SubmitOrderProductListEmptyException;
import com.ordermanagementservice.exceptions.SubmitOrderProductListNullException;
import com.ordermanagementservice.models.request.SubmitOrderRequest;
import org.springframework.stereotype.Component;

@Component
public interface RequestValidator {
    void validateRequest(SubmitOrderRequest request) throws Exception;
}
