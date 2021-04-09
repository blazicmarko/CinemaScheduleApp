package com.example.Cinema.validator;

import com.example.Cinema.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MovieNameValidator implements ConstraintValidator<ValidMovieName, String> {
    private String format;
    private MoviesService moviesService;

    @Autowired
    public MovieNameValidator(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @Override
    public void initialize(ValidMovieName  validIdMovie ) {
        format = validIdMovie.format(); }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return moviesService.checkMovieName(value);
    }
}
