package com.video.oversea.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.PayOrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrderDO> {

    /**
     * 获取PayOrder信息
     * @param userId 用户id
     * @param appId app id
     * @param prepayId 预支付订单 id
     * @return PayOrder信息
     */
    PayOrderDO selectByUserIdAndAppIdAndPrepayId(@Param("userId") Long userId,
                                                 @Param("appId") Long appId,
                                                 @Param("prepayId") String prepayId);

    /**
     * 获取PayOrder信息
     * @param thirdOutTradeNo 第三方支付订单id
     * @return PayOrder信息
     */
    PayOrderDO selectByThirdOutTradeNo(@Param("thirdOutTradeNo") String thirdOutTradeNo);

    void updatePayOrderById(@Param("id") Long id, @Param("thirdTransactionId") String thirdTransactionId, @Param("status") int status);
}
