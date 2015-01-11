package com.yaqa.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ErrorResponse {
    private final String errorMessage;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
