package com.bootcamp.mypos.mypos.exception;

public enum OrderValidationError {

    UNIDENTIFIED_ORDER_STATE("Provided state is not valid (should be either open or closed)"),
    NON_EXISTENT_ID("Provided Order Id doesn't exist"),
    NON_EXISTENT_ORDER_ID("Provided order ID does not exist"),
    NON_EXISTENT_ITEM_ID("Provided item ID does not exist"),
    QUANTITY_LARGER_THAN_AVAILABLE("Ordered quantity is larger than available for item"),
    INVALID_QUANTITY("Invalid quantity requested"),
    ITEM_ALREADY_EXISTS_IN_ORDER("Item already exists in order"),
    UNAUTHORIZED_USER_DELETE("Only the creator of order can remove this order item"),
    INVALID_ORDER_STATUS("Invalid order status provided"),
    INVALID_USER_ID("Provided user ID does not exist");

    private final String message;

    OrderValidationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
