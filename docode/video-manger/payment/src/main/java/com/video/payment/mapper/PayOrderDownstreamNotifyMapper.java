package com.video.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.PayOrderDownstreamNotifyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PayOrderDownstreamNotifyMapper extends BaseMapper<PayOrderDownstreamNotifyDO> {

    PayOrderDownstreamNotifyDO selectByPayOrderId(@Param("payOrderId") Long payOrderId);
}
