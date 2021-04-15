package com.example.cinema.validator;


import com.example.cinema.service.InitService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.example.cinema.CinemaApplication.getLogger;

public class IdMovieValidator implements ConstraintValidator<ValidIdMovie, Integer> {
    private String format;


    @Override
    public void initialize(ValidIdMovie validIdMovie) {
        format = validIdMovie.format();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        if (value <= 0 || value > InitService.getMovieLastId()) {
            getLogger().error("idMovie not valid. Movie with id " + value + " doesn't exists. ValidationException.");
            return false;
        }
        return true;
    }
}
