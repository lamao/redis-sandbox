package org.example.sandbox.service;

import org.example.sandbox.service.model.Message;
import org.example.sandbox.service.redis.RedisMessageService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestRedisMessageService {

    private MessageService service;

    @BeforeEach
    public void setUp() {
        service = new RedisMessageService();
    }

    @Test
    public void testPublish() {
        service.publish("any-message");
    }

    @Test
    public void testGetLast() {
        Message actual = service.getLast();

        Message expected = new Message("any-message");
        assertEquals(expected, actual);
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
