package com.bootcamp.mypos.mypos.exception;

public enum OrderValidationError {

    UNIDENTIFIED_ORDER_STATE("Provided state is not valid (should be either open or closed)"),
    NON_EXISTENT_ID("Provided Order Id doesn't exist"),
    NON_EXISTENT_ORDER_ID("Provided order ID does not exist"),
    NON_EXISTENT_ITEM_ID("Provided item ID does not exist");

    private final String message;

    OrderValidationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
