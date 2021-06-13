package com.test.skblabserver.mocktests;

import com.test.skblabserver.messaging.Message;
import com.test.skblabserver.messaging.MessageSender;
import com.test.skblabserver.messaging.MessagingService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
public class MessageSenderTests {
    @Test
    public void whenTimeout_addToNotSentQueue() throws TimeoutException {
        MessagingService messagingService = Mockito.mock(MessagingService.class);
        MessageSender messageSender = new MessageSender(messagingService);
        Message message = new Message("test");
        Mockito.doThrow(new TimeoutException()).when(messagingService).doRequest(message);
        assertFalse(messageSender.sendMessage(message));
    }

    @Test
    public void whenNotTimeout_addToNotSentQueue() throws TimeoutException {
        MessagingService messagingService = Mockito.mock(MessagingService.class);
        MessageSender messageSender = new MessageSender(messagingService);
        Message message = new Message("test");
        Mockito.doReturn(message).when(messagingService).doRequest(message);
        assertTrue(messageSender.sendMessage(message));
    }
}
