package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.manager.domain.entity.ManagerDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: api
 * @description: manger mapper
 * @author: laojiang
 * @create: 2020-08-18 18:54
 **/
@Mapper
public interface ManagerMapper extends BaseMapper<ManagerDO> {

}
