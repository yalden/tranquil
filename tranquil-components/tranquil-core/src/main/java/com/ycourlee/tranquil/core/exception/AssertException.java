package com.ycourlee.tranquil.core.exception;

public class AssertException extends RuntimeException {

    private static final long serialVersionUID = 3308646509525666500L;

    public AssertException() {
        super();
    }

    public AssertException(String message) {
        super(message);
    }

    public AssertException(String message, Throwable cause) {
        super(message, cause);
    }
}