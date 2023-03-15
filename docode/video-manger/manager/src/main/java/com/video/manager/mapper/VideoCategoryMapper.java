package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.manager.domain.entity.VideoCategoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: video-manger
 * @description: video category mapper
 * @author: laojiang
 * @create: 2020-09-05 10:38
 **/
@Mapper
public interface VideoCategoryMapper extends BaseMapper<VideoCategoryDO> {
}
