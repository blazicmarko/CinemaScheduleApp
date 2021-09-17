package com.example.cinema.exception;

public class NoMovieNameException extends RuntimeException{
    public NoMovieNameException(String message) {
        super(message);
    }

    public NoMovieNameException(String message, Throwable cause) {
        super(message, cause);
    }

}
