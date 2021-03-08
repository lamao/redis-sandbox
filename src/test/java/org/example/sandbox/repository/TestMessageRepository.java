package org.example.sandbox.repository;

import org.example.sandbox.configuration.TestRedisConfiguration;
import org.example.sandbox.repository.entity.MessageEntity;
import org.example.sandbox.service.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
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
// TODO: Requires external redis server to be run.
//@Disabled
@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = TestRedisConfiguration.class)
@DataRedisTest
public class TestMessageRepository {

    @Autowired
    private MessageRepository repository;

    @Test
    public void testGetLast() {

        repository.save(MessageEntity.builder()
                .id("ten")
                .content("some value")
                .publishDate(new Date(10))
                .build());
        repository.save(MessageEntity.builder()
                .id("fifteen")
                .content("another value")
                .publishDate(new Date(15))
                .build());

        Optional<MessageEntity> actual = repository.findFirst1ByOrderByPublishDateDesc();

        MessageEntity expected = MessageEntity.builder()
                .id("fifteen")
                .content("another value")
                .publishDate(new Date(15))
                .build();

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    // BETWEEN (2): [IsBetween, Between] is not supported for Redis query derivation!
    @Disabled
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
