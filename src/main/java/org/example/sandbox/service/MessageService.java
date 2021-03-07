package org.example.sandbox.service;

import org.example.sandbox.service.model.Message;

public interface MessageService {
    void publish(String content);
    Message getLast();
}
