package com.video.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.BaseUserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseUserMapper extends BaseMapper<BaseUserDO> {
}
