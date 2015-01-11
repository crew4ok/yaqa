package com.yaqa.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

public class ValidationException extends YaqaBaseException {

    public ValidationException(BindingResult result) {
        super(result.getAllErrors().stream()
                .map(ObjectError::toString)
                .collect(Collectors.joining())
        );
    }

}
