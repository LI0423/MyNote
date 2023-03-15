package com.video.oversea.user.cache.impl;

import com.video.oversea.user.cache.UserCache;
import com.video.oversea.user.domain.dto.user.BaseUserDTO;
import org.springframework.stereotype.Component;

@Component
public class RedisUserCache extends BaseRedisCache implements UserCache {


    private static final String STRING_BASE_USER_BY_ID = "spredis:baseuser:id:%s";

    private String buildBaseUserByIdKey(Long id) {
        return String.format(STRING_BASE_USER_BY_ID, id);
    }

    @Override
    public BaseUserDTO getBaseUserDTOById(Long id) {
        String cacheKey = buildBaseUserByIdKey(id);
        return get(cacheKey, BaseUserDTO.class);
    }

    @Override
    public void setBaseUserDTOByIdExpire(Long id, BaseUserDTO baseUserDTO) {
        String cacheKey = buildBaseUserByIdKey(id);
        setAndExpire(cacheKey, baseUserDTO);
    }

    @Override
    public void delBaseUserDTOById(Long id) {
        String cacheKey = buildBaseUserByIdKey(id);
        del(cacheKey);
    }
}
