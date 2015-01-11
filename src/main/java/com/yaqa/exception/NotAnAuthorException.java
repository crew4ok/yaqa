package com.yaqa.exception;

public class NotAnAuthorException extends YaqaBaseException {
    public NotAnAuthorException() {
        super("Only author can edit this comment");
    }
}
