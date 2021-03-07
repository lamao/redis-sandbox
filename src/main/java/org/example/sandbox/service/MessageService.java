package org.example.sandbox.service;

import org.example.sandbox.service.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    void publish(String content);
    Optional<Message> getLast();
    List<Message> getByTime(long start, long end);
}
