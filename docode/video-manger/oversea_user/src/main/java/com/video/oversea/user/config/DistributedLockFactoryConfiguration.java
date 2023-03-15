package com.video.oversea.user.config;

import com.video.oversea.user.config.prop.DistributedLockProperties;
import com.video.oversea.user.util.concurrent.DistributedLockFactory;
import com.video.oversea.user.util.concurrent.RedisDistributedLockFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

@Slf4j
@Configuration
public class DistributedLockFactoryConfiguration {

    @Bean
    @Autowired
    public DistributedLockFactory buildRedisDistributedLockFactory(JedisPool jedisPool, DistributedLockProperties config) {
        log.info("Redis distributed lock factory expire:[{}s, {}ns]",
                config.getExpire().getSeconds(), config.getExpire().getNano());
        return new RedisDistributedLockFactory(jedisPool, config.getExpire());
    }
}
