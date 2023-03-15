package com.video.user.cache.impl;

import org.springframework.stereotype.Component;
import redis.clients.jedis.params.SetParams;

import java.io.Serializable;

@Component
public class SimpleRedisUtil extends BaseRedisCache {

    @Override
    public String get(String key) {
        return super.get(key);
    }

    @Override
    public String set(String key, String value) {
        return super.set(key, value);
    }

    @Override
    public String set(String key, String value, SetParams setParams) {
        return super.set(key, value, setParams);
    }

    @Override
    public Long del(String keys) {
        return super.del(keys);
    }

    @Override
    public Long del(String ... keys) {
        return super.del(keys);
    }

    @Override
    public <T extends Serializable> Long publish(String channel, T messageObject) {
        return super.publish(channel, messageObject);
    }
}
