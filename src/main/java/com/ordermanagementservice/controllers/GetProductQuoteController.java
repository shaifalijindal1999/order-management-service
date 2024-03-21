package com.ordermanagementservice.controllers;

import com.ordermanagementservice.models.response.quote.QuoteResponse;
import com.ordermanagementservice.services.ProductQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProductQuoteController {

    @Autowired
    private ProductQuoteService productQuoteService;

    @GetMapping("/product/{productId}/price")
    private ResponseEntity<QuoteResponse>
    getProductQuote(@PathVariable("productId") String productId, @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity) {
        QuoteResponse productQuoteResponse = productQuoteService.getProductQuote(productId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(productQuoteResponse);
    }
}
