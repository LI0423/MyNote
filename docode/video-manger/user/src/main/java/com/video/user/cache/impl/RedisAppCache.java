package com.video.user.cache.impl;

import com.video.entity.AppDO;
import com.video.user.cache.AppCache;
import org.springframework.stereotype.Component;

@Component
public class RedisAppCache extends BaseRedisCache implements AppCache {

    private static final String STRING_APP_BY_PKG_KEY_PREFIX = CACHE_KEY_PREFIX + "app:pkg:%s";

    private static final String STRING_APP_BY_ID_KEY_PREFIX = CACHE_KEY_PREFIX + "app:id:%s";

    private static final String STRING_APP_UPDATE_INFO_BY_APPID_SOURCE_KEY_TMPL = CACHE_KEY_PREFIX + "appupdateinfo:aid:%s:source:%s";

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
