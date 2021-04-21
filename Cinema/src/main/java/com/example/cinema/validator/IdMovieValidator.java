package com.example.cinema.validator;


import com.example.cinema.service.InitService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdMovieValidator implements ConstraintValidator<ValidIdMovie, Integer> {
    private String format;
    public static Logger logger = LogManager.getLogger(IdMovieValidator.class.getName());

    private Logger getLogger() {
        return logger;
    }

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
