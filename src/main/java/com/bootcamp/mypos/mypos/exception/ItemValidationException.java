package com.bootcamp.mypos.mypos.exception;

public class ItemValidationException extends Exception {

    private final ItemValidationError validationError;
    public ItemValidationException(ItemValidationError validationError) {
        this.validationError = validationError;
    }

    public ItemValidationError getValidationError() {
        return validationError;
    }
}
