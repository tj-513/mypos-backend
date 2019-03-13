package com.bootcamp.mypos.mypos.exception;

public enum OrderValidationError {

    UNIDENTIFIED_ORDER_STATE("Provided state is not valid (should be either open or closed)"),
    NON_EXISTENT_ID("Provided Order Id doesn't exist");

    private final String message;

    OrderValidationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
