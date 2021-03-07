package org.example.sandbox;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestMessageController {

    private MessageController controller;

    @Before
    public void setUp() {
        controller = new MessageController();
    }

    @Test
    public void testPublish() {
        controller.publish("any-message");
    }

    @Test
    public void testGetLast() {
        String actual = controller.getLast();

        String expected = "any-message";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetByTime() {
        List<String> actual = controller.getByTime(10, 20);

        List<String> expected = Arrays.asList(
                "any-message",
                "another-one"
        );
        assertEquals(expected, actual);
    }
}
