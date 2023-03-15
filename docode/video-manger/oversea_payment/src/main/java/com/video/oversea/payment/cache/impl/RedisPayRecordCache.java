package com.video.oversea.payment.cache.impl;

import com.video.oversea.payment.cache.PayRecordCache;
import com.video.oversea.payment.domain.dto.payment.PayRecordDTO;
import org.springframework.stereotype.Component;

@Component
public class RedisPayRecordCache extends BaseSinglePointRedisCache implements PayRecordCache {

    /**
     * pay record by pay order id key模板
     */
    private static final String PAY_RECORD_BY_PAY_ORDER_ID_KEY_TMPL =
            KEY_PREFIX + "pay_record_dto:pay_order_id:%s";

    @Override
    public PayRecordDTO getLastPayRecordDTOByPayOrderId(Long payOrderId) {
        String cacheKey = buildKey(PAY_RECORD_BY_PAY_ORDER_ID_KEY_TMPL, payOrderId);
        return get(cacheKey, PayRecordDTO.class);
    }

    @Override
    public void setLastPayRecordDTOByPayOrderIdExpire(Long payOrderId, PayRecordDTO payRecordDTO) {
        String cacheKey = buildKey(PAY_RECORD_BY_PAY_ORDER_ID_KEY_TMPL, payOrderId);
        setAndExpire(cacheKey, payRecordDTO);
    }

    @Override
    public void delLastPayRecordDTOByPayOrderId(Long payOrderId) {
        String cacheKey = buildKey(PAY_RECORD_BY_PAY_ORDER_ID_KEY_TMPL, payOrderId);
        del(cacheKey);
    }
}
