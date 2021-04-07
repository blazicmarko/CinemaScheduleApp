package com.example.Cinema.exception;

public class NoHallException extends RuntimeException {
    public NoHallException(String message) {
        super(message);
    }

    public NoHallException(String message, Throwable cause) {
        super(message, cause);
    }
}
