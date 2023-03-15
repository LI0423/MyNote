package com.video.withdrawal.config;

import com.video.withdrawal.config.prod.CacheRedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
        JedisPool jedisPool =
                new JedisPool(jedisPoolConfig, cacheRedisProperties.getHost(), cacheRedisProperties.getPort());
        return jedisPool;
    }
}
