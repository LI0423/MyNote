package com.video.oversea.payment.service.third;

import com.video.entity.PayOrderDO;
import com.video.entity.PayOrderPayTypeEnum;
import com.video.entity.PayRecordDO;
import com.video.oversea.payment.domain.dto.payment.NotifyResultDTO;
import com.video.oversea.payment.domain.dto.payment.PayOrderDTO;
import com.video.oversea.payment.domain.dto.payment.PayRecordDTO;
//import com.video.payment.domain.dto.payment.PaymentQuerySignDTO;
//import com.video.payment.domain.dto.third.AliPaymentResultDTO;
//import com.video.payment.domain.dto.third.PaymentDTO;
//import com.video.payment.domain.dto.third.PaymentResultDTO;
//import com.video.payment.domain.dto.withdrawal.AliWithdrawalReq;
//import com.video.payment.domain.dto.withdrawal.WithdrawalQueryDTO;
import org.springframework.beans.BeanUtils;

/**
 * @program: mango
 * @description: 第三方支付(用户给企业付款)
 * @author: wangwei
 **/
public interface ThirdPaymentService<T, U> {

    /**
     * 生成预付订单
     * @param userId 用户id
     * @param pkg 本平台的app的pkg
     * @param amount 支付金额
     * @param payType 支付方式(JSAPI、APP、小程序等)
     * @param businessType 业务类型, 可以为null
     * @param orderDesc 订单描述
     * @return 支付订单信息
     */
    PayOrderDTO prepay(Long userId, String pkg, Long amount, PayOrderPayTypeEnum payType,
                       Integer businessType, String orderDesc, String notifyUrl);


    /**
     * 支付结果回调处理
     * @param notifyMessage 通知消息
     * @return 支付订单信息
     */
    NotifyResultDTO payResultCallback(T notifyMessage);

    /**
     * 将PayOrderDO对象转换成PayOrderDTO对象
     * @param payOrderDO
     * @return
     */
    static PayOrderDTO convertTo(PayOrderDO payOrderDO) {
        if (payOrderDO == null) {
            return null;
        }
        PayOrderDTO payOrderDTO = new PayOrderDTO();
        BeanUtils.copyProperties(payOrderDO, payOrderDTO);
        return payOrderDTO;
    }

    /**
     * 将PayOrderDO对象转换成PayOrderDTO对象
     * @param payRecordDO
     * @return
     */
    static PayRecordDTO convertTo(PayRecordDO payRecordDO) {
        if (payRecordDO == null) {
            return null;
        }
        PayRecordDTO payRecordDTO = new PayRecordDTO();
        BeanUtils.copyProperties(payRecordDO, payRecordDTO);
        return payRecordDTO;
    }
}
