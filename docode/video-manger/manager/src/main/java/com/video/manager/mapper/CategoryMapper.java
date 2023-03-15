package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.manager.domain.entity.CategoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: api
 * @description: 分类
 * @author: laojiang
 * @create: 2020-08-18 14:41
 **/
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryDO> {

}
