package com.yaqa.exception;

public abstract class YaqaBaseException extends RuntimeException {
    public YaqaBaseException() {
    }

    public YaqaBaseException(String s) {
        super(s);
    }

    public YaqaBaseException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public YaqaBaseException(Throwable throwable) {
        super(throwable);
    }

    public YaqaBaseException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
