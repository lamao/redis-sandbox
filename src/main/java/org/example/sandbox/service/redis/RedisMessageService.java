package org.example.sandbox.service.redis;

import org.example.sandbox.service.MessageService;
import org.example.sandbox.service.model.Message;

public class RedisMessageService implements MessageService {

    @Override
    public void publish(String content) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Message getLast() {
        return null;
    }
}
