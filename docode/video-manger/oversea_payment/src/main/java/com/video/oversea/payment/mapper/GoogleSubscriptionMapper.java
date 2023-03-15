package com.video.oversea.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.GoogleSubscriptionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface GoogleSubscriptionMapper extends BaseMapper<GoogleSubscriptionDO> {

    @Select("select * from google_subscription where purchase_token = #{purchaseToken}")
    GoogleSubscriptionDO findByPurchaseToken(@Param("purchaseToken") String purchaseToken);

    @Update("update google_subscription " +
            "set expiry_time_millis = #{expiryTimeMillis}," +
            "third_out_trade_no = #{thirdOutTradeNo}," +
            "extra = #{extra} " +
            "where id = #{id}")
    void updateExpiryTime(@Param("id") Long id,
                          @Param("expiryTimeMillis") Long expiryTimeMillis,
                          @Param("thirdOutTradeNo") String thirdOutTradeNo,
                          @Param("extra") String extra);
}
