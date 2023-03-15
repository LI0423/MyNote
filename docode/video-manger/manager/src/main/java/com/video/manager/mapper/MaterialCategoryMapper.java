package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.manager.domain.entity.VideoCategoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: video-manger
 * @description: MaterialCategory
 * @author: laojiang
 * @create: 2020-09-04 11:24
 **/
@Mapper
public interface MaterialCategoryMapper extends BaseMapper<VideoCategoryDO> {
}
