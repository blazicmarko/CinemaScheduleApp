package com.example.cinema.exception;

public class IdExistsException extends RuntimeException {
    public IdExistsException(String message) {
        super(message);
    }

    public IdExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
