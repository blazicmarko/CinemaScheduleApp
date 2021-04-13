package com.example.cinema.validator;

import com.example.cinema.service.InitService;
import com.example.cinema.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

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
        List<String> list = InitService.getMovieNames();
        return !list.contains(value);
    }
}
