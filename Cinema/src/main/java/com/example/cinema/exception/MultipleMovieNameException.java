package com.example.cinema.exception;

public class MultipleMovieNameException extends RuntimeException{
    public MultipleMovieNameException(String message) {
        super(message);
    }

    public MultipleMovieNameException(String message, Throwable cause) {
        super(message, cause);
    }

}
