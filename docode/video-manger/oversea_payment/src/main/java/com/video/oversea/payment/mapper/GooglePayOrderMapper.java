package com.video.oversea.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.GooglePayOrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GooglePayOrderMapper extends BaseMapper<GooglePayOrderDO> {
    @Select("select * from google_pay_order where third_order_id = #{thirdOrderId}")
    GooglePayOrderDO findByThirdOrderId(@Param("thirdOrderId") String thirdOrderId);
}
