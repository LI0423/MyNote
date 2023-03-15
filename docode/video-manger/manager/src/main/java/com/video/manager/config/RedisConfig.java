package com.video.manager.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Slf4j
@Configuration
public class RedisConfig {

    /**
     * 创建redisStandaloneConfiguration
     * @param redisProperties
     * @return
     */
    @Bean("redisStandaloneConfiguration")
    RedisConfiguration redisStandaloneConfiguration(RedisProperties redisProperties) {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
        redisStandaloneConfiguration.setPassword(redisProperties.getPassword());

        log.info("Create redis standalone configuration success. host:[{}], port:[{}], password:[{}]",
                redisProperties.getHost(), redisProperties.getPort(), redisProperties.getPassword());
        return redisStandaloneConfiguration;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(@Qualifier("redisStandaloneConfiguration")
                                                                         RedisConfiguration redisConfiguration) {
        return new LettuceConnectionFactory(redisConfiguration);
    }
}
