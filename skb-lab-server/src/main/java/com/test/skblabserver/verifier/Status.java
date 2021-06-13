package com.test.skblabserver.verifier;

public enum Status {
    CONFIRMED("Вы успешно зарегестрированы в системе"),
    REJECTED("Ваша заявка на регистрацию отклонена"),
    NOT_UNIQUE("Email, логин и номер телефона должны быть уникальными");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
