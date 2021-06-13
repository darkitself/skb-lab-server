package com.test.skblabserver.registration;

import com.test.skblabserver.database.UserService;
import com.test.skblabserver.mail.Email;
import com.test.skblabserver.mail.MailSender;
import com.test.skblabserver.messaging.Message;
import com.test.skblabserver.messaging.MessageSender;
import com.test.skblabserver.verifier.Status;
import com.test.skblabserver.verifier.VerificationResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Основной сервис регистрации пользователей
 */
@Slf4j
@Service
public class RegistrationService {
    private final UserService userService;
    private final MessageSender messageSender;
    private final MailSender mailSender;
    private final BCryptPasswordEncoder encoder;
    private final ExecutorService executor;

    public RegistrationService(UserService userService, MessageSender messageSender, MailSender mailSender) {
        this.userService = userService;
        this.messageSender = messageSender;
        this.mailSender = mailSender;
        encoder = new BCryptPasswordEncoder();
        this.executor = Executors.newWorkStealingPool();
    }

    /**
     * Метод, вызываемый для регистрации пользователя в системе
     * Не приводит к непосредственному сохранению пользователя в базе
     */
    public RegistrationStatus saveUser(UserData userData) {
        var safeUserData = userData.withEncryptedPassword(encoder.encode(userData.getPassword()));
        //Такая проверка необходима, чтобы пользователь не ждал до момента непосредственно сохранения в базу
        if (userService.isUserDataUnique(safeUserData)) {       //Если данные пользователя ранее не встречались в базе,
            Runnable verify = () -> verifyUser(safeUserData);   //то начинаем процесс верификации данных
            executor.submit(verify);                            //Верификация идёт отдельным потоком, чтобы не задерживать пользователя
            return RegistrationStatus.VALIDATED;                //Пользователю говорим, что всё ОК, подробности на почте
        }
        return RegistrationStatus.NOT_UNIQUE;                   //Иначе говорим, что какие-то из введёных данных уже встречались
    }

    private boolean verifyUser(UserData userData) {
       return messageSender.sendMessage(new Message(userData));
    }

    private boolean notifyUser(UserData userData, VerificationResult result) {
        return mailSender.sendMail(new Email(userData.getEmail(), result.getStatus().getDescription()));
    }

    /**
     * Обработка данных, прошедших верификацию.
     * Здесь происходит сохранение в базе, если все условия соблюдены.
     * Здесь же происходит уведомление пользователей о результате регистрации
     */
    @SneakyThrows
    @Scheduled(cron = "${scheduler.saving}")
    private void saveVerifiedData() {
        log.info("Start processing verified data");
        for (var data : messageSender.getVerifiedData().entrySet()) {
            VerificationResult result = data.getValue();
            if (data.getValue().getStatus().equals(Status.CONFIRMED)) {
                try {
                    userService.saveUser(data.getKey());//Сохраняем по одному, хотя одной транзакцией оптимальнее, чтобы точно избежать проблем с неуникальными полями
                } catch (Exception ex) {        //Catch-блок, который обрабывает ситуацию с одновременной отправкой двумя пользователями
                    log.error(ex.getMessage()); //одинаковых данных. Пользователь, которому не повезло, получит на почту
                    result = VerificationResult.withStatus(Status.NOT_UNIQUE);//сообщение о том, что его данные уже есть в системе
                }
            }
            notifyUser(data.getKey(), result);
        }
    }
}
