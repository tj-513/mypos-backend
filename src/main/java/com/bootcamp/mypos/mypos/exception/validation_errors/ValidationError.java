package com.bootcamp.mypos.mypos.exception.validation_errors;

import java.io.Serializable;

public interface ValidationError extends Serializable {
    String getMessage();
}
