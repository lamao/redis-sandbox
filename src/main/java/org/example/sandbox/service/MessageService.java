package org.example.sandbox.service;

import org.example.sandbox.service.model.Message;

import java.util.List;

public interface MessageService {
    void publish(String content);
    Message getLast();
    List<Message> getByTime(long start, long end);
}
