package org.example.sandbox.service.impl;

import lombok.AllArgsConstructor;
import org.example.sandbox.repository.MessageRepository;
import org.example.sandbox.repository.entity.MessageEntity;
import org.example.sandbox.service.MessageService;
import org.example.sandbox.service.model.Message;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private MessageRepository repository;

    @Override
    public void publish(String content) {
        MessageEntity entity = MessageEntity.builder()
                .content(content)
                .publishDate(new Date())
                .build();

        repository.save(entity);
    }

    @Override
    public Optional<Message> getLast() {
        return repository.findFirstByPublishDateOrderByPublishDateDesc()
                .map(it -> new Message(it.getContent()));

    }

    @Override
    public List<Message> getByTime(long start, long end) {
        return repository.findAllByPublishDateBetween(new Date(start), new Date(end))
                .stream()
                .map(entity -> new Message(entity.getContent()))
                .collect(Collectors.toList());
    }
}
