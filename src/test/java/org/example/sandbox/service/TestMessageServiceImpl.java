package org.example.sandbox.service;

import org.example.sandbox.service.impl.MessageServiceImpl;
import org.example.sandbox.service.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMessageServiceImpl {

    private MessageService service;

    @BeforeEach
    public void setUp() {
        service = new MessageServiceImpl(null);
    }

    @Test
    public void testPublish() {
        service.publish("any-message");
    }

    @Test
    public void testGetLastReturnsData() {
        Optional<Message> actual = service.getLast();

        assertTrue(actual.isPresent());
        Message expected = new Message("any-message");
        assertEquals(expected, actual.get());
    }

    @Test
    public void testGetLastReturnsNoData() {
        Optional<Message> actual = service.getLast();

        assertFalse(actual.isPresent());
    }

    @Test
    public void testGetByTime() {
        List<Message> actual = service.getByTime(10, 20);

        List<Message> expected = Arrays.asList(
                new Message("one"),
                new Message("two")
        );
        assertEquals(expected, actual);
    }
}
