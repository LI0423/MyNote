package com.video.oversea.user.cache.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.oversea.user.config.prop.CacheExpireUnitEnum;
import com.video.oversea.user.config.prop.CacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.params.SetParams;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

@Slf4j
public abstract class BaseRedisCache {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    protected static final String ILLEGAL_VALUE = "NULL";

    protected static final String CACHE_KEY_PREFIX = "uspredis:";

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private CacheProperties cacheProperties;

    protected static String buildKey(String keyTmpl, Object ... args) {
        return String.format(keyTmpl, args);
    }

    protected String serializationObj(Serializable obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String get(String key) {
        if (jedisPool == null) {
            throw new RedisComponentsMissingException("jedis pool not found");
        }
        // 三次错误重试
        for (int i = 0; i < 3; i++) {
            try(Jedis jedis = jedisPool.getResource()) {
                return jedis.get(key);
            } catch (Exception e) {
                log.error(String.format("redis get error, key:%s", key), e);
                if (i == 2) {
                    throw e;
                }
            }
        }
        return null;
    }

    protected String set(String key, String value) {
        if (jedisPool == null) {
            throw new RedisComponentsMissingException("jedis pool not found");
        }
        // 三次错误重试
        for (int i = 0; i < 3; i++) {
            try(Jedis jedis = jedisPool.getResource()) {
                // set操作错误也会返回null
                return jedis.set(key, value);
            } catch (Exception e) {
                log.error(String.format("redis set error, key:%s, value:%s", key, value), e);
                if (i == 2) {
                    throw e;
                }
            }
        }
        return null;
    }

    protected String set(String key, String value, SetParams setParams) {
        if (jedisPool == null) {
            throw new RedisComponentsMissingException("jedis pool not found");
        }
        // 三次错误重试
        for (int i = 0; i < 3; i++) {
            try(Jedis jedis = jedisPool.getResource()) {
                // set操作错误也会返回null
                return jedis.set(key, value, setParams);
            } catch (Exception e) {
                log.error(String.format("redis set error, key:%s, value:%s", key, value), e);
                if (i == 2) {
                    throw e;
                }
            }
        }
        return null;
    }

    protected Long del(String key) {
        if (jedisPool == null) {
            throw new RedisComponentsMissingException("jedis pool not found");
        }
        // 三次错误重试
        for (int i = 0; i < 3; i++) {
            try(Jedis jedis = jedisPool.getResource()) {
                // set操作错误也会返回null
                return jedis.del(key);
            } catch (Exception e) {
                log.error(String.format("redis del error, key:%s", key), e);
                if (i == 2) {
                    throw e;
                }
            }
        }
        return null;
    }

    protected Long del(String ... keys) {
        if (jedisPool == null) {
            throw new RedisComponentsMissingException("jedis pool not found");
        }
        // 三次错误重试
        for (int i = 0; i < 3; i++) {
            try(Jedis jedis = jedisPool.getResource()) {
                // set操作错误也会返回null
                return jedis.del(keys);
            } catch (Exception e) {
                log.error(String.format("redis del error, keys:%s",
                        keys == null ? null : Arrays.asList(keys)), e);
                if (i == 2) {
                    throw e;
                }
            }
        }
        return null;
    }

    protected String setAndExpire(String key, String value) {
        if (jedisPool == null) {
            throw new RedisComponentsMissingException("jedis pool not found");
        }
        // 三次错误重试
        for (int i = 0; i < 3; i++) {
            try(Jedis jedis = jedisPool.getResource()) {
                SetParams setParams = new SetParams();
                if (CacheExpireUnitEnum.SECONDS.equals(cacheProperties.getUnit())) {
                    setParams.ex(RandomUtils.nextInt(cacheProperties.getMinExpire(), cacheProperties.getMaxExpire()));
                } else {
                    setParams.px(RandomUtils.nextInt(cacheProperties.getMinExpire(), cacheProperties.getMaxExpire()));
                }
                // set操作错误也会返回null
                return jedis.set(key, value, setParams);
            } catch (Exception e) {
                log.error(String.format("redis set error, key:%s, value:%s", key, value), e);
                if (i == 2) {
                    throw e;
                }
            }
        }
        return null;
    }

    protected String setAndDoubleExpire(String key, String value) {
        if (jedisPool == null) {
            throw new RedisComponentsMissingException("jedis pool not found");
        }
        // 三次错误重试
        for (int i = 0; i < 3; i++) {
            try(Jedis jedis = jedisPool.getResource()) {
                SetParams setParams = new SetParams();
                if (CacheExpireUnitEnum.SECONDS.equals(cacheProperties.getUnit())) {
                    setParams.ex(RandomUtils.nextInt(cacheProperties.getMinExpire(), cacheProperties.getMaxExpire()) * 2);
                } else {
                    setParams.px(RandomUtils.nextInt(cacheProperties.getMinExpire(), cacheProperties.getMaxExpire()) * 2L);
                }
                // set操作错误也会返回null
                return jedis.set(key, value, setParams);
            } catch (Exception e) {
                log.error(String.format("redis set error, key:%s, value:%s", key, value), e);
                if (i == 2) {
                    throw e;
                }
            }
        }
        return null;
    }

    protected <T> T get(String key, Class<T> clazz) {
        if (clazz == null) {
            throw new JedisDataException("clazz param cannot be null");
        }
        String result = get(key);
        if (result == null) {
            return null;
        }
        // 若为非法值则返回全字段值都为空的实例
        if (ILLEGAL_VALUE.equals(result)) {
            try {
                // 数组类型需要特殊处理
                if (clazz.isArray()) {
                    return (T) Array.newInstance(clazz.getComponentType(), 0);
                } else {
                    return clazz.newInstance();
                }
            } catch (InstantiationException e) {
                log.error("create illegal object error.", e);
            } catch (IllegalAccessException e) {
                log.error("create illegal object error.", e);
            }
        }
        try {
            return OBJECT_MAPPER.readValue(result, clazz);
        } catch (JsonProcessingException e) {
            log.error(String.format("covert error, value:%s", result), e);
        }
        return null;
    }

    /**
     * obj允许是null, 设置null值是为了防止缓存穿透攻击
     * @param key
     * @param obj
     */
    protected String set(String key, Object obj) {
        String value = ILLEGAL_VALUE;
        try {
            if (obj != null) {
                value = OBJECT_MAPPER.writeValueAsString(obj);
            }
            return set(key, value);
        } catch (JsonProcessingException e) {
            log.error(String.format("covert error, value:%s", obj), e);
        }
        return null;
    }

    protected String setAndExpire(String key, Object obj) {
        String value = ILLEGAL_VALUE;
        try {
            if (obj != null) {
                value = OBJECT_MAPPER.writeValueAsString(obj);
            }
            return setAndExpire(key, value);
        } catch (JsonProcessingException e) {
            log.error(String.format("covert error, value:%s", obj), e);
        }
        return null;
    }

    protected String setAndDoubleExpire(String key, Object obj) {
        String value = ILLEGAL_VALUE;
        try {
            if (obj != null) {
                value = OBJECT_MAPPER.writeValueAsString(obj);
            }
            return setAndDoubleExpire(key, value);
        } catch (JsonProcessingException e) {
            log.error(String.format("covert error, value:%s", obj), e);
        }
        return null;
    }

    protected Long incr(String key) {
        if (jedisPool == null) {
            throw new RedisComponentsMissingException("jedis pool not found");
        }
        // 三次错误重试
        for (int i = 0; i < 3; i++) {
            try(Jedis jedis = jedisPool.getResource()) {
                return jedis.incr(key);
            } catch (Exception e) {
                log.error(String.format("redis incr error, key:%s", key), e);
                if (i == 2) {
                    throw e;
                }
            }
        }
        return null;
    }

    protected Long decr(String key) {
        if (jedisPool == null) {
            throw new RedisComponentsMissingException("jedis pool not found");
        }
        // 三次错误重试
        for (int i = 0; i < 3; i++) {
            try(Jedis jedis = jedisPool.getResource()) {
                return jedis.decr(key);
            } catch (Exception e) {
                log.error(String.format("redis decr error, key:%s", key), e);
                if (i == 2) {
                    throw e;
                }
            }
        }
        return null;
    }

    protected Long expire(String key,int time) {
        if (jedisPool == null) {
            throw new RedisComponentsMissingException("jedis pool not found");
        }
        // 三次错误重试
        for (int i = 0; i < 3; i++) {
            try(Jedis jedis = jedisPool.getResource()) {
                return jedis.expire(key,time);
            } catch (Exception e) {
                log.error(String.format("redis expire error, key:%s", key), e);
                if (i == 2) {
                    throw e;
                }
            }
        }
        return null;
    }

    protected Object eval(String script, int keyCount, String... params) {
        if (jedisPool == null) {
            throw new RedisComponentsMissingException("jedis pool not found");
        }
        // 三次错误重试
        for (int i = 0; i < 3; i++) {
            try(Jedis jedis = jedisPool.getResource()) {
                return jedis.eval(script, keyCount, params);
            } catch (Exception e) {
                log.error(String.format("redis eval error, script:%s, keyCount:%s, params:%s",
                        script, keyCount, params == null ? null : Arrays.asList(params)), e);
                if (i == 2) {
                    throw e;
                }
            }
        }
        return null;
    }

    public <T extends Serializable> Long publish(String channel, T messageObject) {
        if (jedisPool == null) {
            throw new RedisComponentsMissingException("jedis pool not found");
        }
        // 三次错误重试
        for (int i = 0; i < 3; i++) {
            try(Jedis jedis = jedisPool.getResource()) {
                return jedis.publish(channel, serializationObj(messageObject));
            } catch (Exception e) {
                log.error(String.format("redis publish error, channel:%s, messageObject:%s.",
                        channel, messageObject), e);
                if (i == 2) {
                    throw e;
                }
            }
        }
        return null;
    }

    private class RedisComponentsMissingException extends RuntimeException {
        RedisComponentsMissingException(String message) {
            super(message);
        }
    }
}
