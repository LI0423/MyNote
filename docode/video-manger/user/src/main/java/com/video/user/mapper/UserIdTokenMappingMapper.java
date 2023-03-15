package com.video.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.UserIdTokenMapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserIdTokenMappingMapper extends BaseMapper<UserIdTokenMapping> {

    @Select("select token from userid_token_mapping where pkg = #{pkg} and user_id = #{userId} order by create_time desc limit 1 ")
    String selelctLatestToken(String pkg, Long userId);
}
