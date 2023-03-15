package com.video.user.cache.impl;

import com.video.user.cache.VerificationCodeCache;
import org.springframework.stereotype.Component;
import redis.clients.jedis.params.SetParams;

@Component
public class RedisVerificationCodeCache extends BaseRedisCache implements VerificationCodeCache {

    private static final String VERIFICATION_CODE_BY_PHONE_NUMBER = "VER_CODE_%s";

    @Override
    public void saveVerificationCode(String phoneNumber, String verificationCode, long expire) {
        String key = buildKey(VERIFICATION_CODE_BY_PHONE_NUMBER, phoneNumber);
        set(key, verificationCode, SetParams.setParams().px(expire));
    }

    @Override
    public String getVerificationCode(String phoneNumber) {
        String key = buildKey(VERIFICATION_CODE_BY_PHONE_NUMBER, phoneNumber);
        return get(key);
    }

    @Override
    public Long delVerificationCode(String phoneNumber) {
        String key = buildKey(VERIFICATION_CODE_BY_PHONE_NUMBER, phoneNumber);
        return del(key);
    }
}
