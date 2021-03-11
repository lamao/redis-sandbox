package org.example.sandbox.service.impl;

import lombok.AllArgsConstructor;
import org.example.sandbox.repository.MessageRepository;
import org.example.sandbox.repository.entity.MessageEntity;
import org.example.sandbox.service.MessageService;
import org.example.sandbox.service.model.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private static final int MILLINSECONDS_IN_ON_SECOND = 1000;

    private final MessageRepository repository;

    @Override
    public void publish(String content) {
        MessageEntity entity = MessageEntity.builder()
                .content(content)
                .publishTimestampMillis(System.currentTimeMillis())
                .build();

        repository.save(entity);
    }

    @Override
    public Optional<Message> getLast() {
        Iterable<MessageEntity> allEntries = repository.findAll();
        if (!allEntries.iterator().hasNext()) {
            return Optional.empty();
        }

        Iterator<MessageEntity> iterator = allEntries.iterator();
        MessageEntity lastEntity = iterator.next();
        while (iterator.hasNext()) {
            MessageEntity currentEntity = iterator.next();
            if (currentEntity.getPublishTimestampMillis() > lastEntity.getPublishTimestampMillis()) {
                lastEntity = currentEntity;
            }
        }

        return Optional.of(new Message(lastEntity.getContent()));

    }

    @Override
    public List<Message> getByTime(long startSeconds, long endSeconds) {
        long startMillis = startSeconds * MILLINSECONDS_IN_ON_SECOND;
        long endMillis = endSeconds * MILLINSECONDS_IN_ON_SECOND;

        List<Message> result = new ArrayList<>();

        for (MessageEntity entity : repository.findAll()) {
            if (isTimestampBetween(entity.getPublishTimestampMillis(), startMillis, endMillis)
            ) {
                result.add(new Message(entity.getContent()));
            }
        }

        return result;
    }

    private boolean isTimestampBetween(long timestamp, long start, long end) {
        return start <= timestamp && timestamp <= end;
    }
}
