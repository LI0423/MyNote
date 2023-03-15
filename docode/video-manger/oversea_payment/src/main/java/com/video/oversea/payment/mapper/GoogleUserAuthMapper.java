package com.video.oversea.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.GoogleUserAuthDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GoogleUserAuthMapper extends BaseMapper<GoogleUserAuthDO> {
    @Select("select * from google_user_auth where user_id=#{userId} limit 1")
    GoogleUserAuthDO selectByUserId(@Param("userId") Long userId);
}
