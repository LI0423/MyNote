package com.video.payment.service.impl;

import com.video.entity.PayOrderDO;
import com.video.payment.cache.PayOrderCache;
import com.video.payment.domain.dto.payment.PayOrderDTO;
import com.video.payment.mapper.PayOrderMapper;
import com.video.payment.service.PayOrderService;
import com.video.payment.service.third.ThirdPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayOrderServiceImpl implements PayOrderService {

    @Autowired
    protected PayOrderMapper payOrderMapper;

    @Autowired
    protected PayOrderCache payOrderCache;

    @Override
    public PayOrderDTO find(Long id) {
        // 尝试从缓存中获取PayOrderDTO信息
        PayOrderDTO payOrderDTO =
                payOrderCache.getPayOrderDTOById(id);

        if (payOrderDTO != null) {
            return payOrderDTO.getId() == null ? null : payOrderDTO;
        }

        PayOrderDO payOrderDO =
                payOrderMapper.selectById(id);

        payOrderDTO = payOrderDO == null ? null : ThirdPaymentService.convertTo(payOrderDO);

        // 将PayOrderDTO信息设置到缓存中
        payOrderCache.setPayOrderDTOByIdExpire(id, payOrderDTO);

        return payOrderDTO;
    }
}
