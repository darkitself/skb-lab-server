package com.test.skblabserver.validation;

import com.test.skblabserver.registration.UserData;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserDataValidationTests {
    @Autowired
    private LocalValidatorFactoryBean validator;

    @Test
    public void checkLoginValidation(){
        UserData test = new UserData("te", "1123Qw#", "test@test.com", "89998888881");
        Set<ConstraintViolation<UserData>> violations = validator.validate(test);
        assertFalse(violations.isEmpty());
    }
    @Test
    public void checkPasswordValidation(){
        UserData test = new UserData("test", "1123q", "test@test.com", "89998888881");
        Set<ConstraintViolation<UserData>> violations = validator.validate(test);
        assertFalse(violations.isEmpty());
    }
    @Test
    public void checkEmailValidation(){
        UserData test = new UserData("test", "1123Qw#", "test", "89998888881");
        Set<ConstraintViolation<UserData>> violations = validator.validate(test);
        assertFalse(violations.isEmpty());
    }
    @Test
    public void checkPhoneNumberValidation(){
        UserData test = new UserData("test", "1123Qw#", "test@test.com", "899988qwe881");
        Set<ConstraintViolation<UserData>> violations = validator.validate(test);
        assertFalse(violations.isEmpty());
    }
    @Test
    public void checkCorrectDataValidation(){
        UserData test = new UserData("test", "1123Qw#", "test@test.com", "89998888881");
        Set<ConstraintViolation<UserData>> violations = validator.validate(test);
        assertTrue(violations.isEmpty());
    }
}
