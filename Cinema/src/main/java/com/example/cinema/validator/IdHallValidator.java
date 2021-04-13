package com.example.cinema.validator;

import com.example.cinema.service.InitService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdHallValidator implements ConstraintValidator<ValidIdHall, Integer> {
private String format;


    @Override
public void initialize(ValidIdHall  validIdHall ) {
        format = validIdHall.format(); }

@Override
public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null){
            return true;
        }
        if(value <= 0)
            return false;
    return value <= InitService.getHallLastId();
}
}
