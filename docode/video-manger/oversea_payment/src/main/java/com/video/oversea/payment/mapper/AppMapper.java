package com.video.oversea.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.AppDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppMapper extends BaseMapper<AppDO> {

    List<String> selectAllApiV3Key();

    List<AppDO> selectAllApp();
}
