package com.video.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.RefundOrderDownstreamNotifyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RefundOrderDownstreamNotifyMapper extends BaseMapper<RefundOrderDownstreamNotifyDO> {

    RefundOrderDownstreamNotifyDO selectByRefundOrderId(@Param("refundOrderId") Long refundOrderId);
}
