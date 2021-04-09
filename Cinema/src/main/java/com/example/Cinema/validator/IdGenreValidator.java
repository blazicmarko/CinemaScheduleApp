package com.example.Cinema.validator;

import com.example.Cinema.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdGenreValidator implements ConstraintValidator<ValidIdGenre, Integer> {
private String format;
private GenreService genreService;

    @Autowired
    public IdGenreValidator(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public void initialize(ValidIdGenre  validIdHall ) {
        format = validIdHall.format(); }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null){
            return true;
        }
        if(value <= 0)
            return false;
        return value <= genreService.getLastId();
    }
}
