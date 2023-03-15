package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.UserLoginDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserLoginMapper extends BaseMapper<UserLoginDO> {

}
