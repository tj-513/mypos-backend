package com.bootcamp.mypos.mypos.exception;

import com.bootcamp.mypos.mypos.exception.validation_errors.ValidationError;

public class ValidationException extends RuntimeException {

    private final ValidationError validationError;
    public ValidationException(ValidationError validationError) {
        this.validationError = validationError;
    }

    public ValidationError getValidationError() {
        return validationError;
    }

    @Override
    public String getMessage() {
        return validationError.getMessage();
    }
}
