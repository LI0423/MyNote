package com.video.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.PayRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PayRecordMapper extends BaseMapper<PayRecordDO> {

    /**
     * 通过支付订单id查询最后一条相关的支付记录
     * @param payOrderId 支付订单id
     * @return 最后一条支付记录
     */
    PayRecordDO selectLastByPayOrderId(@Param("payOrderId") Long payOrderId);
}
