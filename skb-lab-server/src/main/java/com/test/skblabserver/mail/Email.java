package com.test.skblabserver.mail;

import lombok.Data;

@Data
public class Email {
    private final String receiver;
    private final String body;

    public Email(String receiver, String body) {
        this.receiver = receiver;
        this.body = body;
    }
}
