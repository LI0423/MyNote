package com.video.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.RefundOrderDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefundOrderMapper extends BaseMapper<RefundOrderDO> {

    RefundOrderDO selectByPayOrderId(Long payOrderId);
}
