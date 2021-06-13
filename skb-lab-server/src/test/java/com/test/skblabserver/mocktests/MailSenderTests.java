package com.test.skblabserver.mocktests;

import com.test.skblabserver.mail.Email;
import com.test.skblabserver.mail.MailSender;
import com.test.skblabserver.mail.MailService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
public class MailSenderTests {

    @Test
    public void whenTimeout_addToNotSentQueue() throws TimeoutException {
        MailService mailService = Mockito.mock(MailService.class);
        MailSender mailSender = new MailSender(mailService);
        Email mail = new Email("test", "test");
        Mockito.doThrow(new TimeoutException()).when(mailService).sendMail(mail);
        assertFalse(mailSender.sendMail(mail));
    }

    @Test
    public void whenNotTimeout_addToNotSentQueue() throws TimeoutException {
        MailService mailService = Mockito.mock(MailService.class);
        MailSender mailSender = new MailSender(mailService);
        Email mail = new Email("test", "test");
        Mockito.doNothing().when(mailService).sendMail(mail);
        assertTrue(mailSender.sendMail(mail));
    }
}
