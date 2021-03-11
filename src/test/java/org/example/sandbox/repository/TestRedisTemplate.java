package org.example.sandbox.repository;

import org.example.sandbox.configuration.TestRedisConfiguration;
import org.example.sandbox.repository.entity.MessageEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by vyacheslav.mischeryakov
 * Created 07.03.2021
 */
@Disabled
@SpringBootTest(classes = {TestRedisConfiguration.class})
public class TestRedisTemplate {

    @Autowired
    private MessageRepository repository;

    @Autowired
    private RedisTemplate<String, Object> template;

    private HashOperations<String, String, Object> hashOperations;

    private ZSetOperations<String, Object> zSetOperations;

    @BeforeEach
    public void setUp() {
        hashOperations = template.opsForHash();
        zSetOperations = template.opsForZSet();
    }

    @Test
    public void testGetByDate() {
        HashOperations<String, String, MessageEntity> hashOperations = template.opsForHash();

        hashOperations.put("Messages", "ten", MessageEntity.builder()
                .id("ten")
                .content("some value")
                .publishTimestampMillis(10)
                .build());
        hashOperations.put("Messages", "fifteen", MessageEntity.builder()
                .id("fifteen")
                .content("another value")
                .publishTimestampMillis(15)
                .build());
        hashOperations.put("Messages", "seventeen", MessageEntity.builder()
                .id("seventeen")
                .content("another value")
                .publishTimestampMillis(17)
                .build());
        hashOperations.put("Messages", "twenty", MessageEntity.builder()
                .id("twenty")
                .content("another value")
                .publishTimestampMillis(25)
                .build());

        for (MessageEntity entity : repository.findAll()) {
            System.out.println(entity);
        }

        Set<?> actualRaw = zSetOperations.rangeByScore("publishTimestampMillis", 10, 20);

        assertNotNull(actualRaw);
        assertEquals(2, actualRaw.size());

        List<MessageEntity> actual = ((Set<MessageEntity>) actualRaw)
                .stream()
                .sorted(Comparator
                        .comparingLong(MessageEntity::getPublishTimestampMillis)
                        .reversed())
                .collect(Collectors.toList());


        List<MessageEntity> expected = Arrays.asList(
                MessageEntity.builder()
                        .id("fifteen")
                        .content("another value")
                        .publishTimestampMillis(15)
                        .build(),
                MessageEntity.builder()
                        .id("seventeen")
                        .content("another value")
                        .publishTimestampMillis(17)
                        .build()
        );

        assertEquals(expected, actual);
    }


}
