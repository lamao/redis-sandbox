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
import java.util.Collections;
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
        List<MessageEntity> fakeEntities = Arrays.asList(
                MessageEntity.builder()
                        .id("five")
                        .content("five")
                        .publishTimestampMillis(5000)
                        .build(),
                MessageEntity.builder()
                        .id("fifteen")
                        .content("fifteen")
                        .publishTimestampMillis(15000)
                        .build()
        );
        when(repository.findAll()).thenReturn(fakeEntities);

        Optional<Message> actual = service.getLast();

        assertTrue(actual.isPresent());
        Message expected = new Message("fifteen");
        assertEquals(expected, actual.get());
    }

    @Test
    public void testGetLastReturnsNoData() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        Optional<Message> actual = service.getLast();

        assertFalse(actual.isPresent());
    }

    @Test
    public void testGetByTime() {
        List<MessageEntity> fakeEntities = Arrays.asList(
                MessageEntity.builder()
                        .id("five")
                        .content("five")
                        .publishTimestampMillis(5000)
                        .build(),
                MessageEntity.builder()
                        .id("fifteen")
                        .content("fifteen")
                        .publishTimestampMillis(15000)
                        .build(),
                MessageEntity.builder()
                        .id("seventeen")
                        .content("seventeen")
                        .publishTimestampMillis(17000)
                        .build(),
                MessageEntity.builder()
                        .id("thirty")
                        .content("thirty")
                        .publishTimestampMillis(30)
                        .build()
        );
        when(repository.findAll()).thenReturn(fakeEntities);

        List<Message> actual = service.getByTime(10, 20);

        List<Message> expected = Arrays.asList(
                new Message("fifteen"),
                new Message("seventeen")
        );
        assertEquals(expected, actual);
    }
}
