package com.test.skblabserver.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.UUID;

public class Message<T extends Serializable> {
    private final static ObjectMapper mapper = new ObjectMapper();
    private final String message;
    private final MessageId messageId;

    @SneakyThrows
    public Message(T message) {
        this.message = mapper.writeValueAsString(message);
        messageId = new MessageId(UUID.randomUUID());
    }

    @SneakyThrows
    public T getMessage(Class<T> type) {
        return mapper.readValue(message, type);
    }

    public MessageId getMessageId() {
        return messageId;
    }

    public class MessageId {
        private final UUID id;

        public UUID getId() {
            return id;
        }

        public MessageId(UUID randomUUID) {
            id = randomUUID;
        }
    }
}
