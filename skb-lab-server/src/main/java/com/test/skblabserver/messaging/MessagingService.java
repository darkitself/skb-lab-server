package com.test.skblabserver.messaging;
import java.util.concurrent.TimeoutException;

public interface MessagingService {
    /**
     * Отправка сообщения в шину.
     *
     * @param msg сообщение для отправки.
     *
     * @return идентификатор отправленного сообщения (correlationId)
     */
    Message.MessageId send(Message msg);

    /**
     * Встает на ожидание ответа по сообщению с messageId.
     *
     * Редко, но может кинуть исключение по таймауту.
     *
     * @param messageId идентификатор сообщения, на которое ждем ответ.
     * @return Тело ответа.
     */
    Message receive(Message.MessageId messageId) throws TimeoutException;

    /**
     * Отправляем сообщение и ждем на него ответ.
     *
     * @param request тело запроса.
     * @return тело ответа.
     */
    Message doRequest(Message request) throws TimeoutException;
}
