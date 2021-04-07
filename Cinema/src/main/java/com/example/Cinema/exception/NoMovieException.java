package com.example.Cinema.exception;

public class NoMovieException extends RuntimeException {
    public NoMovieException(String message) {
        super(message);
    }

    public NoMovieException(String message, Throwable cause) {
        super(message, cause);
    }
}
