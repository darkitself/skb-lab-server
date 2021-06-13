package com.test.skblabserver.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {
    String message() default "Enter valid russian mobile phone number (11 digits)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
