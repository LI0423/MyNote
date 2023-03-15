package com.video.oversea.user.config;

import com.video.oversea.user.config.prop.CacheRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
public class RedisConfig {

    /**
     * cache redis pool配置
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig(CacheRedisProperties cacheRedisProperties) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(cacheRedisProperties.getMaxActive());
        jedisPoolConfig.setMinIdle(cacheRedisProperties.getMinIdle());
        jedisPoolConfig.setMaxIdle(cacheRedisProperties.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(cacheRedisProperties.getMaxWait());
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool(CacheRedisProperties cacheRedisProperties, JedisPoolConfig jedisPoolConfig) {
        log.info("初始化jedis pool, max-total:[{}], min-idle:[{}], max-idle:[{}], max-wait-millis:[{}], host:[{}], port:[{}]",
                jedisPoolConfig.getMaxTotal(), jedisPoolConfig.getMinIdle(), jedisPoolConfig.getMaxIdle(),
                jedisPoolConfig.getMaxWaitMillis(), cacheRedisProperties.getHost(), cacheRedisProperties.getPort());
        JedisPool jedisPool =
                new JedisPool(jedisPoolConfig, cacheRedisProperties.getHost(), cacheRedisProperties.getPort());
        return jedisPool;
    }
}
