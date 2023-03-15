package com.video.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.UserAuthDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author admin
 */
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuthDO> {

    @Select("select * from user_auth where user_id=#{userId} limit 1")
    UserAuthDO selectByUserId(Long userId);
}
