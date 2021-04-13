package com.example.cinema.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = { IdMovieValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidIdMovie {
    String format() default "";

    String message() default "{Invalid idMovie}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    boolean value() default true;
}
