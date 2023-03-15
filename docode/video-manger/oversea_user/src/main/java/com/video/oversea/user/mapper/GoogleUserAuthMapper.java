package com.video.oversea.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.GoogleUserAuthDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoogleUserAuthMapper extends BaseMapper<GoogleUserAuthDO> {
    @Select("select * from google_user_auth where google_user_id = #{googleUserId}")
    List<GoogleUserAuthDO> selectListByGoogleUserId(@Param("googleUserId") String googleUserId);

    @Select("select * from google_user_auth where access_token = #{accessToken}")
    List<GoogleUserAuthDO> selectListByAccessToken(@Param("accessToken") String accessToken);
}
