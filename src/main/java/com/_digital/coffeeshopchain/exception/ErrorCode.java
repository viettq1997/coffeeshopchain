package com._digital.coffeeshopchain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION("API9999", "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY("CF1000", "Uncategorized error", HttpStatus.BAD_REQUEST),
    SHOP_NOT_FOUND("CF1001", "Shop not found", HttpStatus.NOT_FOUND),
    CUSTOMER_NOT_FOUND("CF1002", "Customer not found", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND("CF1003", "Order not found", HttpStatus.NOT_FOUND),
    CANCEL_INVALID_ORDER("CF1004", "Only placed status can be cancel!", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(String code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final String code;
    private final String message;
    private final HttpStatusCode statusCode;
}
