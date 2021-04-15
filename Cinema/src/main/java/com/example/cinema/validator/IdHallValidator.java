package com.example.cinema.validator;

import com.example.cinema.service.InitService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.example.cinema.CinemaApplication.getLogger;

public class IdHallValidator implements ConstraintValidator<ValidIdHall, Integer> {
    private String format;


    @Override
    public void initialize(ValidIdHall validIdHall) {
        format = validIdHall.format();
    }

    @Override
public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        if (value <= 0 || value > InitService.getHallLastId()) {
            getLogger().error("idHall not valid. Hall with id " + value + " doesn't exists. ValidationException.");
            return false;
        }

        return true;
    }
}
