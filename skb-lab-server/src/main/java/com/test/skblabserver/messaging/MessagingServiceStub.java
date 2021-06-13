package com.test.skblabserver.messaging;

import com.test.skblabserver.verifier.Status;
import com.test.skblabserver.verifier.VerificationResult;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Класс, имитирующий шину сообщений
 */
@Service
public class MessagingServiceStub implements MessagingService {
    @Override
    public Message.MessageId send(Message msg) {
        return msg.getMessageId();
    }

    @Override
    public Message receive(Message.MessageId messageId) throws TimeoutException {

        if (shouldThrowTimeout()) {
            sleep();

            throw new TimeoutException("Timeout!");
        }

        if (shouldSleep()) {
            sleep();
        }

        if (shouldReject()) {
            return new Message<>(VerificationResult.withStatus(Status.REJECTED));
        }
        return new Message<>(VerificationResult.withStatus(Status.CONFIRMED));
    }

    /**
     * Основной метод, имитирующий отправку сообщения и получение ответа на него
     */
    @Override
    public Message doRequest(Message request) throws TimeoutException {
        final Message.MessageId messageId = send(request);

        return receive(messageId);
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

    private static boolean shouldReject() {
        return new Random().nextInt(10) == 1;
    }
}
