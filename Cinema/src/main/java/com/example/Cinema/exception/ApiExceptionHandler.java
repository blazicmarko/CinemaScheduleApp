package com.example.Cinema.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;
import java.net.ConnectException;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {AppointmentCheckException.class})
    public ResponseEntity<Object> handleAppointmentCheckException(AppointmentCheckException e){
        HttpStatus badRequest = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {NoMovieException.class})
    public ResponseEntity<Object> handleNoMovieException(NoMovieException e){
        HttpStatus badRequest = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {NoHallException.class})
    public ResponseEntity<Object> handleNoHallException(NoHallException e){
        HttpStatus badRequest = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {NullVariableException.class})
    public ResponseEntity<Object> handleNullVariableException(NullVariableException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {IdExistsException.class})
    public ResponseEntity<Object> handleIdExistsException(IdExistsException e){
        HttpStatus badRequest = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {NoIdException.class})
    public ResponseEntity<Object> handleNoIdException(NoIdException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {TableEmptyException.class})
    public ResponseEntity<Object> handleTableEmptyException(TableEmptyException e){
        HttpStatus badRequest = HttpStatus.NO_CONTENT;
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NO_CONTENT,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleNoArgumentValid(MethodArgumentNotValidException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleNotReadableArgumentValid(HttpMessageNotReadableException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleNotValid(ValidationException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value ={ConnectException.class})
    public ResponseEntity<Object> handleDatabaseException(ConnectException e){
        HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(
                "Database is temporary disconnected. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }


}