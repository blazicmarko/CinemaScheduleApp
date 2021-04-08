package com.example.Cinema.validator;

import com.example.Cinema.service.HallsService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdHallValidator implements ConstraintValidator<ValidIdHall, Integer> {
private String format;
private HallsService hallsService;

@Autowired
public IdHallValidator(HallsService hallsService) {
    this.hallsService = hallsService;
}

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
        return value <= hallsService.getLastId();
}
}
