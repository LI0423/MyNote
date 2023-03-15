package com.video.payment.cache.impl;

import com.video.entity.AppDO;
import com.video.payment.cache.AppCache;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RedisAppCache extends BaseSinglePointRedisCache implements AppCache {

    private static final String STRING_APP_BY_PKG_KEY_PREFIX = "payment:app:pkg:%s";

    private static final String STRING_APP_BY_ID_KEY_PREFIX = "payment:app:id:%s";

    private static final String STRING_APP_BY_THIRD_APP_ID_KEY_PREFIX = "payment:app:third_app_id:%s";

    private static final String STRING_ALL_APP_API_V3_KEY = "payment:api_v3_key:all";

    private static final String STRING_ALL_WE_CHAR_API_V3_KEY = "payment:api_v3_key:we_chat_all";

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
    public void setAppByThirdAppIdExpire(String thirdAppId, AppDO appDO) {
        String cacheKey = buildKey(STRING_APP_BY_THIRD_APP_ID_KEY_PREFIX, thirdAppId);
        setAndExpire(cacheKey, appDO);
    }

    @Override
    public AppDO getThirdAppById(String thirdAppId) {
        String cacheKey = buildKey(STRING_APP_BY_THIRD_APP_ID_KEY_PREFIX, thirdAppId);
        return get(cacheKey, AppDO.class);
    }

    @Override
    public void setAppByIdExpire(Long id, AppDO appDO) {
        String cacheKey = buildAppByIdKey(id);
        setAndExpire(cacheKey, appDO);
    }

    @Override
    public List<String> getAllApiV3Key() {
        String cacheKey = STRING_ALL_APP_API_V3_KEY;
        String[] apiV3Keys = get(cacheKey, String[].class);
        if (apiV3Keys == null) {
            return null;
        }
        return Arrays.asList(apiV3Keys);
    }

    @Override
    public void setAllApiV3Key(List<String> apiV3Keys) {
        String cacheKey = STRING_ALL_APP_API_V3_KEY;
        setAndExpire(cacheKey, apiV3Keys);
    }

    @Override
    public List<String> getAllWeChatApiV3Key() {
        String cacheKey = STRING_ALL_WE_CHAR_API_V3_KEY;
        String[] apiV3Keys = get(cacheKey, String[].class);
        if (apiV3Keys == null) {
            return null;
        }
        return Arrays.asList(apiV3Keys);
    }

    @Override
    public void setAllWeChatApiV3Key(List<String> apiV3Keys) {
        String cacheKey = STRING_ALL_WE_CHAR_API_V3_KEY;
        setAndExpire(cacheKey, apiV3Keys);
    }
}
