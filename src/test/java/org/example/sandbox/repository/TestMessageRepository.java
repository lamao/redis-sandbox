package org.example.sandbox.repository;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.example.sandbox.repository.entity.MessageEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by vyacheslav.mischeryakov
 * Created 07.03.2021
 */
@ExtendWith(SpringExtension.class)
@DataRedisTest
public class TestMessageRepository {

    @Autowired
    private MessageRepository repository;

    @Test
    public void testGetLast() {
        Optional<MessageEntity> actual = repository.findFirst1ByOrderByPublishDateDesc();

        MessageEntity expected = MessageEntity.builder()
                        .id("key1")
                        .content("one")
                        .publishDate(new Date(15))
                        .build();

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Ignore
    @Test
    public void testGetByDate() {
        List<MessageEntity> actual = repository.findAllByPublishDateBetween(new Date(10), new Date(20));

        List<MessageEntity> expected = Arrays.asList(
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

        assertEquals(expected, actual);
    }


}
