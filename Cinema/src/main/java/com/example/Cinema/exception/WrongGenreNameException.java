package com.example.Cinema.exception;

public class WrongGenreNameException extends RuntimeException {
    public WrongGenreNameException(String message) {
        super(message);
    }

    public WrongGenreNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
