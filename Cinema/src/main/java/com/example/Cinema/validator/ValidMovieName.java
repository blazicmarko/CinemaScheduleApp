package com.example.Cinema.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = { MovieNameValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidMovieName {
    String format() default "";

    String message() default "{Movie name already exists}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    boolean value() default true;
}
