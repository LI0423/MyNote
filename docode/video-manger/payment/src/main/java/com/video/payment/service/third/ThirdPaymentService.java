package com.video.payment.service.third;

import com.video.entity.PayOrderDO;
import com.video.entity.PayOrderPayTypeEnum;
import com.video.entity.PayRecordDO;
import com.video.payment.domain.dto.payment.NotifyResultDTO;
import com.video.payment.domain.dto.payment.PayOrderDTO;
import com.video.payment.domain.dto.payment.PayRecordDTO;
import com.video.payment.domain.dto.payment.PaymentQuerySignDTO;
import com.video.payment.domain.dto.third.AliPaymentResultDTO;
import com.video.payment.domain.dto.third.PaymentDTO;
import com.video.payment.domain.dto.third.PaymentResultDTO;
import com.video.payment.domain.dto.withdrawal.WithdrawalQueryDTO;
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
     * 查询支付订单信息
     * @param id 支付订单id
     * @return 支付订单信息
     */
    PayOrderDTO find(Long id);

    /**
     * 通过支付订单id查询最后一条支付记录
     * @param payOrderId 支付订单id
     * @return 支付记录
     */
    PayRecordDTO findLastByPayOrderId(Long payOrderId);

    /**
     * 支付结果回调处理
     * @param notifyMessage 通知消息
     * @return 支付订单信息
     */
    NotifyResultDTO payResultCallback(T notifyMessage);

    /**
     * 尝试主动获取第三方订单信息更新订单状态
     * @param payOrderId 支付订单id
     * @return 支付订单信息
     */
    PayOrderDTO tryUpdateFromThirdInfo(Long payOrderId);

    /**
     * 获取给客户掉起app支付时提供的请求参数
     * @param payOrderId 交易单号
     * @return 给客户掉起app支付时提供的请求参数
     */
    PaymentQuerySignDTO sign(Long payOrderId);



    /**
     * 生成签名
     * @param paymentDTO
     * @param thirdAppId
     * @return
     */
    String generateSign(PaymentDTO paymentDTO, String thirdAppId, String thirdMchid);

    /**
     * 支付
     * @param paymentDTO
     * @return
     */
    PaymentResultDTO pay(PaymentDTO paymentDTO, String thirdAppId, String thirdMchid);

    AliPaymentResultDTO alPay(WithdrawalQueryDTO withdrawalQueryDTO, String thirdAppId);



    /**
     * 签名校验
     * @param checkObj 签名校验用对象
     * @return
     */
    boolean signCheck(U checkObj);

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
