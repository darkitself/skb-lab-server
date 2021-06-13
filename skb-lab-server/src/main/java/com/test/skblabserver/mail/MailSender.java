package com.test.skblabserver.mail;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Класс, отвечающий за отправление писем
 */
@Service
@Slf4j
public class MailSender {
    private final MailService mailService;
    private final ExecutorService executor;
    private final ConcurrentLinkedQueue<Email> notSentMail = new ConcurrentLinkedQueue<>();

    public MailSender(MailService mailService) {
        this.mailService = mailService;
        this.executor = Executors.newWorkStealingPool();
    }

    /**
     * Отправка письма пользователю с результатом верификации.
     * В случае ошибки, письмо уходит в очередь на повторную отправку
     */
    public boolean sendMail(Email mail) {
        try {
            mailService.sendMail(mail);
            return true;
        } catch (TimeoutException timeoutException) {
            timeoutException.printStackTrace();
            log.error(timeoutException.getMessage());
            notSentMail.add(mail);
            return false;
        }
    }

    /**
     * Повторная отправка писем из очереди неотправленных
     */
    @SneakyThrows
    @Scheduled(cron = "*/30 * * * * *")
    private void resendMails() {
        log.info("Start to resending {} emails", notSentMail.size());
        List<Runnable> tasks = new LinkedList<>();
        while (!notSentMail.isEmpty()) {
            var mail = notSentMail.remove();
            tasks.add(() -> sendMail(mail));
        }
        for (var task : tasks)
            executor.submit(task);
    }
}
