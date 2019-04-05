package com.bootcamp.mypos.mypos.exception;

import com.bootcamp.mypos.mypos.exception.validation_errors.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ValidationException extends RuntimeException {
    @Getter
    private final ValidationError validationError;

    @Override
    public String getMessage() {
        return validationError.getMessage();
    }
}
