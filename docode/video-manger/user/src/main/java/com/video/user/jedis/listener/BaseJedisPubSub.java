package com.video.user.jedis.listener;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPubSub;

@Slf4j
public abstract class BaseJedisPubSub<T> extends JedisPubSub {

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

//    private final TypeReference<T> typeReference = new TypeReference<T>(){};

    /**
     *
     */
     private final Class<T> objectClass =
            (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 0);

    @Override
    public void onMessage(String channel, String message) {
        try {
            T object = OBJECT_MAPPER.readValue(message, objectClass);
            onMessage(channel, object);
        } catch (JsonProcessingException e) {
            log.error("OnMessage error. channel: [" + channel + "], message: [" + message + "].", e);
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        try {
            T object = OBJECT_MAPPER.readValue(message, objectClass);
            onPMessage(pattern, channel, object);
        } catch (JsonProcessingException e) {
            log.error("OnMessage error. channel: [" + channel + "], message: [" + message + "].", e);
        }
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        log.info("OnSubscribe, channel: [" + channel + "], subscribedChannels: [" + subscribedChannels + "].");
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        log.info("OnUnsubscribe, channel: [" + channel + "], subscribedChannels: [" + subscribedChannels + "].");
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        log.info("OnPUnsubscribe, pattern: [" + pattern + "], subscribedChannels: [" + subscribedChannels + "].");
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        log.info("OnPSubscribe, pattern: [" + pattern + "], subscribedChannels: [" + subscribedChannels + "].");
    }

    /**
     * 通过subscribe监听channel时, 当监听的channel有消息时会调用的方法(message 对象版)
     *
     * 需要子类去实现具体逻辑
     * @param channel
     * @param object
     */
    protected void onMessage(String channel, T object) {
    }

    /**
     * 通过psubscribe监听channel时, 当监听的channel有消息时会调用的方法(message 对象版)
     *
     * 需要子类去实现具体逻辑
     * @param channel
     * @param object
     */
    protected void onPMessage(String pattern, String channel, T object) {
    }
}
