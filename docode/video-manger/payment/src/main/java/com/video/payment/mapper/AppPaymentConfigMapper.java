package com.video.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.AppPaymentConfigDO;
import com.video.entity.AppPaymentWeChatConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppPaymentConfigMapper extends BaseMapper<AppPaymentConfigDO> {

    AppPaymentConfigDO selectByAliPayAppId(@Param("aliPayAppId") String aliPayAppId);

    List<AppPaymentConfigDO> selectByFrozenStatusAndDeletedAndSubSelect(
            @Param("frozen_status")  Integer frozenStatus, @Param("deleted") Integer deleted,
            @Param("merchant_type") Integer merchantType, @Param("mapping_type") Integer mappingType,
            @Param("status") Integer status, @Param("app_id") Long appId);
}
