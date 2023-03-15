package com.video.payment.cache;

import com.video.entity.AppPaymentConfigDO;

public interface AppPaymentConfigCache {

    /**
     * 从缓存中获取app payment config
     * @param id
     * @return
     */
    AppPaymentConfigDO getById(Long id);

    /**
     * 清除以id为key的app payment config缓存
     * @param id
     */
    void delById(Long id);

    /**
     * 设置以id为key的app payment config缓存
     * @param id
     * @param appPaymentConfigDO
     */
    void setByIdExpire(Long id, AppPaymentConfigDO appPaymentConfigDO);

    /**
     * 从缓存中获取app payment config
     * @param aliPayAppId
     * @return
     */
    AppPaymentConfigDO getByAliPayAppId(String aliPayAppId);

    /**
     * 清除以id为key的app payment config缓存
     * @param aliPayAppId
     */
    void delByAliPayAppId(String aliPayAppId);

    /**
     * 设置以id为key的app payment config缓存
     * @param aliPayAppId
     * @param appPaymentConfigDO
     */
    void setByAliPayAppIdExpire(String aliPayAppId, AppPaymentConfigDO appPaymentConfigDO);
}
