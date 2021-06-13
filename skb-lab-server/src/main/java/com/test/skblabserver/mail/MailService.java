package com.test.skblabserver.mail;

import java.util.concurrent.TimeoutException;

public interface MailService {
    void sendMail (Email email) throws TimeoutException;
}
