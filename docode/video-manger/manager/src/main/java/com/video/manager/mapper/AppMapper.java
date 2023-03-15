package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.AppDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: video-manger
 * @description: app mapper
 * @author: laojiang
 * @create: 2020-09-03 11:27
 **/
@Mapper
public interface AppMapper extends BaseMapper<AppDO> {
}
