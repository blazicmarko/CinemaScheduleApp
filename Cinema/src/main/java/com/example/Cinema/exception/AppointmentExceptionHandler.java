package com.example.Cinema.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.awt.geom.RectangularShape;
import java.time.ZonedDateTime;

@ControllerAdvice
public class AppointmentExceptionHandler {

    @ExceptionHandler(value = {AppointmentCheckException.class})
    public ResponseEntity<Object> handleAppointmentCheckException(AppointmentCheckException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        AppointmentException appointmentException = new AppointmentException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(appointmentException, badRequest);
    }
}
