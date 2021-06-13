package com.test.skblabserver.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
/**
 * Валидатор телефонного номера, работает для российских номеров
 */
public class PhoneValidator implements
        ConstraintValidator<Phone, String> {

    private static final Pattern passwordValidator = Pattern.compile("(8|\\+7)?(\\d{3})[\\d]{7,10}$");

    @Override
    public void initialize(Phone phone) {
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext cxt) {
        return passwordValidator.matcher(phone).find();
    }
}
