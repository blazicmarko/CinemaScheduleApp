package com.example.cinema.validator;


import com.example.cinema.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdMovieValidator implements ConstraintValidator<ValidIdMovie, Integer> {
    private String format;
    private MoviesService moviesService;

    @Autowired
    public IdMovieValidator(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

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
        return value <= moviesService.getLastId();
    }
}
