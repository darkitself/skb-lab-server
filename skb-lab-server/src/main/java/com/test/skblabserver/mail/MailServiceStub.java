package com.test.skblabserver.mail;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Класс-заглушка, имитирующий почтовый сервис
 */
@Slf4j
@Service
public class MailServiceStub implements MailService {
    /**
     * Метод, имитирующий отправку письма
     */
    @Override
    public void sendMail(Email email) throws TimeoutException {

        if (shouldThrowTimeout()) {
            sleep();
            throw new TimeoutException("Timeout!");
        }

        if (shouldSleep()) {
            sleep();
        }

        log.info("Message sent to {}, body {}.", email.getReceiver(), email.getBody());
    }

    @SneakyThrows
    private static void sleep() {
        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
    }

    private static boolean shouldSleep() {
        return new Random().nextInt(10) == 1;
    }

    private static boolean shouldThrowTimeout() {
        return new Random().nextInt(10) == 1;
    }
}
