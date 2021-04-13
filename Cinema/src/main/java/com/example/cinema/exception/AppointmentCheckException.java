package com.example.cinema.exception;

public class AppointmentCheckException extends RuntimeException{
    public AppointmentCheckException(String message) {
        super(message);
    }

    public AppointmentCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}
