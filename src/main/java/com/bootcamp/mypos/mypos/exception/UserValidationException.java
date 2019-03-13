package com.bootcamp.mypos.mypos.exception;

public class UserValidationException extends Exception {

    private final UserValidationError validationError;
    public UserValidationException(UserValidationError validationError) {
        this.validationError = validationError;
    }

    public UserValidationError getValidationError() {
        return validationError;
    }
}
