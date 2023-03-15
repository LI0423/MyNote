package com.video.oversea.payment.cache.impl;

import com.video.oversea.payment.cache.PayOrderCache;
import com.video.oversea.payment.domain.dto.payment.PayOrderDTO;
import org.springframework.stereotype.Component;

@Component
public class RedisPayOrderCache extends BaseSinglePointRedisCache implements PayOrderCache {

    /**
     * pay order by id key模板
     */
    private static final String PAY_ORDER_BY_ID_KEY_TMPL =
            BaseSinglePointRedisCache.KEY_PREFIX + "pay_order_dto:id:%s";

    /**
     * pay order by user id and app id and prepay id key模板
     */
    private static final String PAY_ORDER_BY_USER_ID_APP_ID_PREPAY_ID_KEY_TMPL =
            BaseSinglePointRedisCache.KEY_PREFIX + "pay_order_dto:user_id:%s:app_id:%s:prepay_id:%s";

    @Override
    public PayOrderDTO getPayOrderDTOById(Long id) {
        String cacheKey = buildKey(PAY_ORDER_BY_ID_KEY_TMPL, id);
        return get(cacheKey, PayOrderDTO.class);
    }

    @Override
    public void setPayOrderDTOByIdExpire(Long id, PayOrderDTO payOrderDTO) {
        String cacheKey = buildKey(PAY_ORDER_BY_ID_KEY_TMPL, id);
        setAndExpire(cacheKey, payOrderDTO);
    }

    @Override
    public void delPayOrderDTOById(Long id) {
        String cacheKey = buildKey(PAY_ORDER_BY_ID_KEY_TMPL, id);
        del(cacheKey);
    }

    @Override
    public PayOrderDTO getPayOrderDTOByUserIdAndAppIdAndPrepayId(Long userId, Long appId, String prepayId) {
        String cacheKey = buildKey(PAY_ORDER_BY_USER_ID_APP_ID_PREPAY_ID_KEY_TMPL, userId, appId, prepayId);
        return get(cacheKey, PayOrderDTO.class);
    }

    @Override
    public void setPayOrderDTOByUserIdAndAppIdAndPrepayIdExpire(Long userId, Long appId,
                                                                String prepayId, PayOrderDTO payOrderDTO) {
        String cacheKey = buildKey(PAY_ORDER_BY_USER_ID_APP_ID_PREPAY_ID_KEY_TMPL, userId, appId, prepayId);
        setAndExpire(cacheKey, payOrderDTO);
    }

    @Override
    public void delPayOrderDTOByUserIdAndAppIdAndPrepayId(Long userId, Long appId, String prepayId) {
        String cacheKey = buildKey(PAY_ORDER_BY_USER_ID_APP_ID_PREPAY_ID_KEY_TMPL, userId, appId, prepayId);
        del(cacheKey);
    }
}
