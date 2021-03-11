package org.example.sandbox.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by vyacheslav.mischeryakov
 * Created 08.03.2021
 */
@TestConfiguration
public class TestRedisConfiguration {

    private RedisServer redisServer;

    public TestRedisConfiguration(RedisProperties redisProperties) {
        this.redisServer = new RedisServer(redisProperties.getRedisPort());
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}
