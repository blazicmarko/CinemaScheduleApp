package com.example.cinema.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TimeValidator implements ConstraintValidator<ValidTime, LocalTime> {
    private String format;

    @Override
    public void initialize(ValidTime  validDateTime ) {
        format = validDateTime.format(); }

    @Override
    public boolean isValid(LocalTime value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null){
            return true;
        }
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(value.toString());
        return matcher.matches();
    }
}
