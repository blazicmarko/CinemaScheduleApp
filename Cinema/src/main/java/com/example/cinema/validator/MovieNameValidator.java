package com.example.cinema.validator;

import com.example.cinema.service.InitService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MovieNameValidator implements ConstraintValidator<ValidMovieName, String> {
    private String format;
    public static Logger logger = LogManager.getLogger(MovieNameValidator.class.getName());

    private Logger getLogger() {
        return logger;
    }

    @Override
    public void initialize(ValidMovieName validIdMovie) {
        format = validIdMovie.format();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        List<String> list = InitService.getMovieNames();
        if (list.contains(value)) {
            getLogger().error("Movie with name " + value + " exists. ValidationException.");
        }
        return !list.contains(value);
    }
}
