package com.bootcamp.mypos.mypos.exception.validation_errors;

public enum ItemValidationError implements ValidationError {

    NON_EXISTENT_ID("Provided Item Id doesn't exist"),
    ITEM_NAME_EXISTS("An item with the same name exists"),
    EMPTY_ITEM_NAME("Provided item name is empty"),
    INVALID_AMOUNT_AVAILABLE("Provided amount available is invalid");

    private final String message;

    ItemValidationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
