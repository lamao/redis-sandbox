package org.example.sandbox.service;

import org.example.sandbox.repository.MessageRepository;
import org.example.sandbox.repository.entity.MessageEntity;
import org.example.sandbox.service.impl.MessageServiceImpl;
import org.example.sandbox.service.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestMessageServiceImpl {

    private MessageService service;

    @Mock
    private MessageRepository repository;

    @BeforeEach
    public void setUp() {
        service = new MessageServiceImpl(repository);
    }

    @Test
    public void testPublish() {
        service.publish("any-message");

        verify(repository).save(any());
    }

    @Test
    public void testGetLastReturnsData() {
        Optional<MessageEntity> fakeEntity = Optional.of(
                MessageEntity.builder()
                        .id("key")
                        .content("any-message")
                        .publishDate(new Date(10))
                        .build()
        );
        when(repository.findFirstByPublishDateOrderByPublishDateDesc()).thenReturn(fakeEntity);

        Optional<Message> actual = service.getLast();

        assertTrue(actual.isPresent());
        Message expected = new Message("any-message");
        assertEquals(expected, actual.get());
    }

    @Test
    public void testGetLastReturnsNoData() {
        when(repository.findFirstByPublishDateOrderByPublishDateDesc()).thenReturn(Optional.empty());

        Optional<Message> actual = service.getLast();

        assertFalse(actual.isPresent());
    }

    @Test
    public void testGetByTime() {
        List<MessageEntity> fakeEntities = Arrays.asList(
                MessageEntity.builder()
                        .id("key1")
                        .content("one")
                        .publishDate(new Date(15))
                        .build(),
                MessageEntity.builder()
                        .id("key2")
                        .content("two")
                        .publishDate(new Date(17))
                        .build()
        );
        Date fakeStart = new Date(10);
        Date fakeEnd = new Date(20);
        when(repository.findAllByPublishDateBetween(fakeStart, fakeEnd)).thenReturn(fakeEntities);

        List<Message> actual = service.getByTime(10, 20);

        List<Message> expected = Arrays.asList(
                new Message("one"),
                new Message("two")
        );
        assertEquals(expected, actual);
    }
}
