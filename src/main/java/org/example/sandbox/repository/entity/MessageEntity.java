package org.example.sandbox.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

@RedisHash("message")
@Value
@Builder
public class MessageEntity {

    @Id
    private String id;
    private String content;
    @Indexed
    private long publishTimestampMillis;
}
