package com.example.Cinema.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = { IdHallValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidIdHall {
    String format() default "";

    String message() default "{Invalid idHall}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    boolean value() default true;
}
