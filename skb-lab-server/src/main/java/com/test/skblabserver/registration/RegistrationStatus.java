package com.test.skblabserver.registration;

public enum RegistrationStatus {
    VALIDATED("Регистрация завершена. Подробности на электронной почте"),
    NOT_UNIQUE("Email, логин и номер телефона должны быть уникальными");

    private String description;

    RegistrationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
