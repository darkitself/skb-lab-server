package com.test.skblabserver.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Too weak password. Please, use 0-9, A-Z, a-z and special symbol (6 is min length)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
