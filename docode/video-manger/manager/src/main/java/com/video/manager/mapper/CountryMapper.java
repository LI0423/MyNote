package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.manager.domain.entity.CountryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: mango
 * @description: country mapper
 * @author: laojiang
 * @create: 2020-10-21 15:46
 **/
@Mapper
public interface CountryMapper extends BaseMapper<CountryDO> {
}
