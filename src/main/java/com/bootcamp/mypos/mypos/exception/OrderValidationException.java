package com.bootcamp.mypos.mypos.exception;

public class OrderValidationException extends Exception {

    private final OrderValidationError validationError;
    public OrderValidationException(OrderValidationError validationError) {
        this.validationError = validationError;
    }

    public OrderValidationError getValidationError() {
        return validationError;
    }
}
