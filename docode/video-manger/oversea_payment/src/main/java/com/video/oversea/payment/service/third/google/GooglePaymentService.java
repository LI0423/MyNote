package com.video.oversea.payment.service.third.google;

import com.video.entity.*;

public interface GooglePaymentService {

    /**
     * 谷歌支付订单校验
     *
     * @param pkg
     * @param originalJson
     * @param signature
     * @param userId
     * @param thirdOutTradeNo
     */
    Boolean subscribe(String pkg, String originalJson, String signature, Long userId, String thirdOutTradeNo);


    /**
     * 支付
     * @param
     * @return
     */
    GooglePayOrderDO pay(Long userId, String pkg, Long amount, PayOrderPayTypeEnum payType,
                         Integer businessType, String orderDesc, String notifyUrl,
                         String originalJson, String signature) throws Exception;

    void googleSubscribeNotify(String eventJson);
}
