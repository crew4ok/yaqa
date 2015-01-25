package com.yaqa.exception;

public class InvalidImageFormatException extends YaqaBaseException {
    public InvalidImageFormatException() {
        super("Image has invalid format");
    }
}
