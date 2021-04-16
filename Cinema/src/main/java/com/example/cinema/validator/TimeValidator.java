package com.example.cinema.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TimeValidator implements ConstraintValidator<ValidTime, LocalTime> {
    private String format;
    public static Logger logger = LogManager.getLogger(TimeValidator.class.getName());

    private Logger getLogger() {
        return logger;
    }

    @Override
    public void initialize(ValidTime validDateTime) {
        format = validDateTime.format();
    }

    @Override
    public boolean isValid(LocalTime value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            getLogger().error("Wrong time in request sent. ValidationException.");
        }
        return matcher.matches();
    }
}
