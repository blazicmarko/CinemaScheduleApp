package com.example.Cinema.exception;

public class NullVariableException extends RuntimeException {
    public NullVariableException(String message) {
        super(message);
    }

    public NullVariableException(String message, Throwable cause) {
        super(message, cause);
    }
}
