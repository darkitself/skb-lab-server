package com.test.skblabserver.messaging;

import com.test.skblabserver.registration.UserData;
import com.test.skblabserver.verifier.VerificationResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Класс, отвечающий за отправку сообщений в шину
 */
@Service
@Slf4j
public class MessageSender {
    private final MessagingService messagingService;
    private final ExecutorService executor;
    private final ConcurrentLinkedQueue<Message> notSentMessages = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<Message, Message> messagesWithAnswers = new ConcurrentHashMap<>();

    public MessageSender(MessagingService messagingService) {
        this.messagingService = messagingService;
        this.executor = Executors.newWorkStealingPool();
    }

    /**
     * Получение пользовательских данных, которые прошли верификацию
     */
    //TODO: должен быть более абстрактным, отдавая данные пар сообщений переданных типов
    // (В данном случае, только пар <UserData, VerificationResult>)
    public Map<UserData, VerificationResult> getVerifiedData() {
        var res = messagesWithAnswers.entrySet().stream().collect(Collectors.toMap(
                e -> (UserData) e.getKey().getMessage(UserData.class),
                e -> (VerificationResult) e.getValue().getMessage(VerificationResult.class)
        ));
        messagesWithAnswers.clear();
        return res;
    }

    /**
     * Отправка сообщения на верификацию. В случае ошибки, сообщение уходит в очередь на повторную отправку
     */
    public boolean sendMessage(Message message) {
        try {
            messagesWithAnswers.put(message, messagingService.doRequest(message));
            return true;
        } catch (TimeoutException timeoutException) {
            timeoutException.printStackTrace();
            log.error(timeoutException.getMessage());
            notSentMessages.add(message);//Возможно, в случае проблем с отправкой на верификацию,
            return false;                // следует уведомлять пользователя о наличии у нас технических неполадок
        }
    }

    /**
     * Повторная отправка сообщений из очереди
     */
    @SneakyThrows
    @Scheduled(cron = "${scheduler.messaging}")
    private void resendMessages() {
        log.info("Start to resending {} messages", notSentMessages.size());
        List<Runnable> tasks = new LinkedList<>();
        while (!notSentMessages.isEmpty()) {
            var message = notSentMessages.remove();
            tasks.add(() -> sendMessage(message));
        }
        for (var task : tasks)
            executor.submit(task);
    }
}
