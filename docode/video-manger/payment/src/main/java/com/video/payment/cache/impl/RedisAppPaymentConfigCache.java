package com.video.payment.cache.impl;

import com.video.entity.AppPaymentConfigDO;
import com.video.payment.cache.AppPaymentConfigCache;
import org.springframework.stereotype.Component;

@Component
public class RedisAppPaymentConfigCache extends BaseSinglePointRedisCache implements AppPaymentConfigCache {

    private static final String APP_PAYMENT_CONFIG_BY_ID_KEY_TMPL = KEY_PREFIX + "app_payment_config:%s";

    private static final String APP_PAYMENT_CONFIG_BY_ALIPAY_ID_KEY_TMPL = KEY_PREFIX + "app_payment_config:allipay_appid:%s";

    @Override
    public AppPaymentConfigDO getById(Long id) {
        String cacheKey = buildKey(APP_PAYMENT_CONFIG_BY_ID_KEY_TMPL, id);
        return get(cacheKey, AppPaymentConfigDO.class);
    }

    @Override
    public void delById(Long id) {
        String cacheKey = buildKey(APP_PAYMENT_CONFIG_BY_ID_KEY_TMPL, id);
        del(cacheKey);
    }

    @Override
    public void setByIdExpire(Long id, AppPaymentConfigDO appPaymentConfigDO) {
        String cacheKey = buildKey(APP_PAYMENT_CONFIG_BY_ID_KEY_TMPL, id);
        setAndExpire(cacheKey, appPaymentConfigDO);
    }

    @Override
    public AppPaymentConfigDO getByAliPayAppId(String aliPayAppId) {
        String cacheKey = buildKey(APP_PAYMENT_CONFIG_BY_ALIPAY_ID_KEY_TMPL, aliPayAppId);
        return get(cacheKey, AppPaymentConfigDO.class);
    }

    @Override
    public void delByAliPayAppId(String aliPayAppId) {
        String cacheKey = buildKey(APP_PAYMENT_CONFIG_BY_ALIPAY_ID_KEY_TMPL, aliPayAppId);
        del(cacheKey);
    }

    @Override
    public void setByAliPayAppIdExpire(String aliPayAppId, AppPaymentConfigDO appPaymentConfigDO) {
        String cacheKey = buildKey(APP_PAYMENT_CONFIG_BY_ALIPAY_ID_KEY_TMPL, aliPayAppId);
        setAndExpire(cacheKey, appPaymentConfigDO);
    }
}
