package com.example.cinema.exception;

public class TableEmptyException extends RuntimeException {
    public TableEmptyException(String message) {
        super(message);
    }

    public TableEmptyException(String message, Throwable cause) {
        super(message, cause);
    }
}
