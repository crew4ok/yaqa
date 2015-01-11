package com.yaqa.exception;

public class NotFoundException extends YaqaBaseException {
    public <T> NotFoundException(Class<T> entityClass, Object identifier) {
        super("Entity of class = " + entityClass + " was not found by identifier = " + identifier);
    }
}
