package com.video.withdrawal.service.third;

import com.video.withdrawal.domain.dto.third.PaymentDTO;
import com.video.withdrawal.domain.dto.third.PaymentResultDTO;

/**
 * @program: mango
 * @description: 第三方支付的直接
 * @author: laojiang
 * @create: 2020-09-11 11:52
 **/
public interface ThirdPaymentService {

    /**
     * 生成签名
     * @param paymentDTO
     * @param appPkg
     * @return
     */
    String generateSign(PaymentDTO paymentDTO, String appPkg);

    /**
     * 支付
     * @param paymentDTO
     * @return
     */
    PaymentResultDTO pay(PaymentDTO paymentDTO, String appPkg);
}
