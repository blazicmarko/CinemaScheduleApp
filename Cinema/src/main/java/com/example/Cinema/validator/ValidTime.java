package com.example.Cinema.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = { TimeValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidTime {
    String format() default "([01]?[0-9]|20):[0-5][0-9]";

    String message() default "{Invalid timestamp}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    boolean value() default true;
}
