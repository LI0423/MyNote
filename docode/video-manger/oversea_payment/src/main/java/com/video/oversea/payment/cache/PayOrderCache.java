package com.video.oversea.payment.cache;

import com.video.oversea.payment.domain.dto.payment.PayOrderDTO;

/**
 * 支付订单缓存
 */
public interface PayOrderCache {

    /**
     * 从缓存中获取PayOrderDTO信息
     * @param id pay order id
     * @return PayOrderDTO信息
     */
    PayOrderDTO getPayOrderDTOById(Long id);

    /**
     * 设置PayOrderDTO信息到缓存中, 带过期时间
     * @param id pay order id
     * @param payOrderDTO PayOrderDTO信息
     */
    void setPayOrderDTOByIdExpire(Long id, PayOrderDTO payOrderDTO);

    /**
     * 删除PayOrderDTO缓存
     * @param id pay order id
     */
    void delPayOrderDTOById(Long id);

    /**
     * 从缓存中获取PayOrderDTO信息
     * @param userId 用户id
     * @param appId app id
     * @param prepayId 预付订单id
     * @return PayOrderDTO信息
     */
    PayOrderDTO getPayOrderDTOByUserIdAndAppIdAndPrepayId(Long userId, Long appId, String prepayId);

    /**
     * 设置PayOrderDTO信息到缓存中, 带过期时间
     * @param userId 用户id
     * @param appId app id
     * @param prepayId 预付订单id
     * @param payOrderDTO PayOrderDTO信息
     */
    void setPayOrderDTOByUserIdAndAppIdAndPrepayIdExpire(Long userId, Long appId, String prepayId, PayOrderDTO payOrderDTO);

    /**
     * 从缓存中获取PayOrderDTO信息
     * @param userId 用户id
     * @param appId app id
     * @param prepayId 预付订单id
     */
    void delPayOrderDTOByUserIdAndAppIdAndPrepayId(Long userId, Long appId, String prepayId);
}
