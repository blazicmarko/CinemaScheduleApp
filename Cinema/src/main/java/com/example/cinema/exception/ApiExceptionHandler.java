package com.example.cinema.exception;

import com.example.cinema.model.responseModel.BasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.net.ConnectException;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {AppointmentCheckException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleAppointmentCheckException(AppointmentCheckException e){
        HttpStatus badRequest = HttpStatus.CONFLICT;
        BasicResponse basicResponse = new BasicResponse(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, badRequest);
    }

    @ExceptionHandler(value = {NoIdException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleNoIdException(NoIdException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        BasicResponse basicResponse = new BasicResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, badRequest);
    }

    @ExceptionHandler(value = {TableEmptyException.class})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> handleTableEmptyException(TableEmptyException e){
        HttpStatus badRequest = HttpStatus.NO_CONTENT;
        BasicResponse basicResponse = new BasicResponse(
                e.getMessage(),
                HttpStatus.NO_CONTENT,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, badRequest);
    }

    @ExceptionHandler(value = {MultipleMovieNameException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleMultipleMovieNameException(MultipleMovieNameException e){
        HttpStatus badRequest = HttpStatus.CONFLICT;
        BasicResponse basicResponse = new BasicResponse(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, badRequest);
    }

    @ExceptionHandler(value = {NoMovieNameException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleNoMovieNameException(NoMovieNameException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        BasicResponse basicResponse = new BasicResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, badRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleNoArgumentValid(MethodArgumentNotValidException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        List<String> details = new LinkedList<>();
        for(ObjectError error : e.getBindingResult().getAllErrors()) {
            String fieldErrors = ((FieldError) error).getField();
            details.add(fieldErrors + " : " +error.getDefaultMessage());
        }
        String message = "";
        for(String d : details){
            message = message.concat( d+ ", ");
        }
        BasicResponse basicResponse = new BasicResponse(
                message,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, badRequest);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleNotReadableArgumentValid(HttpMessageNotReadableException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        BasicResponse basicResponse = new BasicResponse(
                "" + e.getRootCause(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, badRequest);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleNotValid(ValidationException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        BasicResponse basicResponse = new BasicResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, badRequest);
    }

    @ExceptionHandler(value = {ConnectException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleDatabaseException(ConnectException e){
        HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;
        BasicResponse basicResponse = new BasicResponse(
                "Database is temporary disconnected. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, badRequest);
    }

    @ExceptionHandler(value = {WrongGenreNameException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleWrongGenreException(WrongGenreNameException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        BasicResponse basicResponse = new BasicResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(basicResponse, badRequest);
    }

}