package com.video.payment.cache;

import com.video.payment.domain.dto.payment.PayRecordDTO;

/**
 * 支付记录缓存
 */
public interface PayRecordCache {

    /**
     * 从缓存中获取PayRecordDTO信息
     * @param payOrderId 支付订单id
     * @return PayRecordDTO信息
     */
    PayRecordDTO getLastPayRecordDTOByPayOrderId(Long payOrderId);

    /**
     * 将payRecordDTO信息设置到缓存中
     * @param payOrderId
     */
    void setLastPayRecordDTOByPayOrderIdExpire(Long payOrderId, PayRecordDTO payRecordDTO);

    /**
     * 删除payRecordDTO信息缓存
     * @param payOrderId
     */
    void delLastPayRecordDTOByPayOrderId(Long payOrderId);
}

