package com.example.Cinema.exception;

import com.example.Cinema.model.responseModel.ApiResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;
import java.net.ConnectException;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {AppointmentCheckException.class})
    public ResponseEntity<Object> handleAppointmentCheckException(AppointmentCheckException e){
        HttpStatus badRequest = HttpStatus.CONFLICT;
        ApiResponseModel apiResponseModel = new ApiResponseModel(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiResponseModel, badRequest);
    }

    @ExceptionHandler(value = {IdExistsException.class})
    public ResponseEntity<Object> handleIdExistsException(IdExistsException e){
        HttpStatus badRequest = HttpStatus.CONFLICT;
        ApiResponseModel apiResponseModel = new ApiResponseModel(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiResponseModel, badRequest);
    }

    @ExceptionHandler(value = {NoIdException.class})
    public ResponseEntity<Object> handleNoIdException(NoIdException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiResponseModel apiResponseModel = new ApiResponseModel(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiResponseModel, badRequest);
    }

    @ExceptionHandler(value = {TableEmptyException.class})
    public ResponseEntity<Object> handleTableEmptyException(TableEmptyException e){
        HttpStatus badRequest = HttpStatus.NO_CONTENT;
        ApiResponseModel apiResponseModel = new ApiResponseModel(
                e.getMessage(),
                HttpStatus.NO_CONTENT,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiResponseModel, badRequest);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
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
        ApiResponseModel apiResponseModel = new ApiResponseModel(
                message,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiResponseModel, badRequest);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleNotReadableArgumentValid(HttpMessageNotReadableException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiResponseModel apiResponseModel = new ApiResponseModel(
                "" + e.getRootCause(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiResponseModel, badRequest);
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleNotValid(ValidationException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiResponseModel apiResponseModel = new ApiResponseModel(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiResponseModel, badRequest);
    }

    @ExceptionHandler(value ={ConnectException.class})
    public ResponseEntity<Object> handleDatabaseException(ConnectException e){
        HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponseModel apiResponseModel = new ApiResponseModel(
                "Database is temporary disconnected. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiResponseModel, badRequest);
    }
    @ExceptionHandler(value ={WrongGenreNameException.class})
    public ResponseEntity<Object> handleWrongGenreException(WrongGenreNameException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiResponseModel apiResponseModel = new ApiResponseModel(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiResponseModel, badRequest);
    }

}