package com.video.payment.service;

import com.video.payment.domain.dto.payment.PayOrderDTO;

public interface PayOrderService {


    /**
     * 获取支付订单
     * @param id
     * @return
     */
    PayOrderDTO find(Long id);
}
