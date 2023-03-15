package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.SignInDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: video-manger
 * @description: 签到
 * @author: laojiang
 * @create: 2020-09-03 11:30
 **/
@Mapper
public interface SignInMapper extends BaseMapper<SignInDO> {
}
