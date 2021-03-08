package org.example.sandbox.service.impl;

import lombok.AllArgsConstructor;
import org.example.sandbox.repository.MessageRepository;
import org.example.sandbox.repository.entity.MessageEntity;
import org.example.sandbox.service.MessageService;
import org.example.sandbox.service.model.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    public static final int MILLINSECONS_IN_ON_SECOND = 1000;
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
        return repository.findFirst1ByOrderByPublishDateDesc()
                .map(it -> new Message(it.getContent()));

    }

    @Override
    public List<Message> getByTime(long startSeconds, long endSeconds) {
        Date startDate = new Date(startSeconds * MILLINSECONS_IN_ON_SECOND);
        Date endDate = new Date(endSeconds * MILLINSECONS_IN_ON_SECOND);

        List<Message> result = new ArrayList<>();

        for (MessageEntity entity : repository.findAll()) {
            if (isDateBetween(entity.getPublishDate(), startDate, endDate)
            ) {
                result.add(new Message(entity.getContent()));
            }
        }

        return result;
    }

    private boolean isDateBetween(Date date, Date startDate, Date endDate) {
        return date.after(startDate) && date.before(endDate);
    }
}
