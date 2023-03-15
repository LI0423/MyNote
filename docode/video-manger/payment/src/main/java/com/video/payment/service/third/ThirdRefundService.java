package com.video.payment.service.third;

import com.video.entity.RefundOrderDO;
import com.video.entity.RefundRecordDO;
import com.video.payment.domain.dto.payment.NotifyResultDTO;
import com.video.payment.domain.dto.refund.RefundOrderDTO;
import com.video.payment.domain.dto.refund.RefundRecordDTO;
import org.springframework.beans.BeanUtils;

/**
 * @program: mango
 * @description: 第三方退款(用户给企业付款)
 * @author: wangwei
 **/
public interface ThirdRefundService<T> {

    /**
     * 退款
     * @param payOrderId 支付订单id
     * @param amount 现金数
     * @param reason 退款原因
     * @param notifyUrl 通知url
     * @return 退款订单信息
     */
    RefundOrderDTO refund(String pkg, Long payOrderId, Integer amount, String reason, String notifyUrl);

    /**
     * 查询退款订单信息
     * @param payOrderId 支付id
     * @return 退款订单信息
     */
    RefundOrderDTO findByPayOrderId(Long payOrderId);

    /**
     * 查询退款订单信息
     * @param refundOrderId 退款订单号
     * @return 退款订单信息
     */
    RefundOrderDTO findByRefundOrderId(Long refundOrderId);

    /**
     * 退款通知处理
     * @param notifyMessage 通知消息对象
     * @return 处理结果
     */
    NotifyResultDTO refundNotifyProcess(T notifyMessage);

    static RefundOrderDTO convertTo(RefundOrderDO refundOrderDO) {
        if (refundOrderDO == null) {
            return null;
        }
        RefundOrderDTO refundOrderDTO = new RefundOrderDTO();
        BeanUtils.copyProperties(refundOrderDO, refundOrderDTO);
        return refundOrderDTO;
    }

    static RefundRecordDTO convertTo(RefundRecordDO refundRecordDO) {
        if (refundRecordDO == null) {
            return null;
        }
        RefundRecordDTO refundRecordDTO = new RefundRecordDTO();
        BeanUtils.copyProperties(refundRecordDO, refundRecordDTO);
        return refundRecordDTO;
    }
}
