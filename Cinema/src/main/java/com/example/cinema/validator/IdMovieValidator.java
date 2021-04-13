package com.example.cinema.validator;


import com.example.cinema.service.InitService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdMovieValidator implements ConstraintValidator<ValidIdMovie, Integer> {
    private String format;


    @Override
    public void initialize(ValidIdMovie  validIdMovie ) {
        format = validIdMovie.format(); }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null){
            return true;
        }
        if(value <= 0)
            return false;
        return value <= InitService.getMovieLastId();
    }
}
