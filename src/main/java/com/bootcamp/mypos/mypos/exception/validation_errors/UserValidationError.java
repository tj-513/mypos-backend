package com.bootcamp.mypos.mypos.exception.validation_errors;

public enum UserValidationError implements ValidationError{

    EMPTY_EMAIL("Provided email is empty"),
    INVALID_EMAIL("Invalid email provided by user"),
    DUPLICATE_EMAIL("Provided Email Already Exists"),
    INVALID_USERNAME("Provided Username is invalid"),
    NON_EXISTENT_ID("Provided Id doesn't exist"),
    DUPLICATE_USERNAME("Provided Username Already Exists");

    private final String message;

    UserValidationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
