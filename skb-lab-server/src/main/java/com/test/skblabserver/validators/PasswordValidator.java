package com.test.skblabserver.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
/**
 * Валидатор пароля, чтобы пароль был не слишком простым
 */
public class PasswordValidator implements
        ConstraintValidator<Password, String> {

    private static final Pattern passwordValidator = Pattern.compile("(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}");

    @Override
    public void initialize(Password password) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext cxt) {
        return passwordValidator.matcher(password).find();
    }
}
