package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.UserAuthDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: video-manger
 * @description: user auth mapper
 * @author: laojiang
 * @create: 2020-09-03 11:33
 **/
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuthDO> {
}
