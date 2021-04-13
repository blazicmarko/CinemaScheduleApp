package com.example.cinema.validator;

import com.example.cinema.service.InitService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MovieNameValidator implements ConstraintValidator<ValidMovieName, String> {
    private String format;


    @Override
    public void initialize(ValidMovieName  validIdMovie ) {
        format = validIdMovie.format(); }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        List<String> list = InitService.getMovieNames();
        return !list.contains(value);
    }
}
