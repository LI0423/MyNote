package com.video.withdrawal.cache.impl;

import com.video.entity.AppDO;
import com.video.withdrawal.cache.AppCache;
import org.springframework.stereotype.Component;

@Component
public class RedisAppCache extends BaseSinglePointRedisCache implements AppCache {

    private static final String STRING_APP_BY_PKG_KEY_PREFIX = "spredis:app:pkg:%s";

    private static final String STRING_APP_BY_ID_KEY_PREFIX = "spredis:app:id:%s";

    private String buildAppByPkgKey(String pkg) {
        return String.format(STRING_APP_BY_PKG_KEY_PREFIX, pkg);
    }

    private String buildAppByIdKey(Long id) {
        return String.format(STRING_APP_BY_ID_KEY_PREFIX, id);
    }

    /**
     * 不对参数做任何校验，由上层业务模块进行校验
     * @param pkg
     * @return
     */
    @Override
    public AppDO getAppByPkg(String pkg) {
        String cacheKey = buildAppByPkgKey(pkg);
        return get(cacheKey, AppDO.class);
    }

    @Override
    public void setAppByPkgExpire(String pkg, AppDO appDO) {
        String cacheKey = buildAppByPkgKey(pkg);
        setAndExpire(cacheKey, appDO);
    }

    @Override
    public AppDO getAppById(Long id) {
        String cacheKey = buildAppByIdKey(id);
        return get(cacheKey, AppDO.class);
    }

    @Override
    public void setAppByIdExpire(Long id, AppDO appDO) {
        String cacheKey = buildAppByIdKey(id);
        setAndExpire(cacheKey, appDO);
    }
}
