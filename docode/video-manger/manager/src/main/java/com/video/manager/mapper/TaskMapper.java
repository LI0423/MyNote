package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.TaskDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: video-manger
 * @description: task mapper
 * @author: laojiang
 * @create: 2020-09-03 11:31
 **/
@Mapper
public interface TaskMapper extends BaseMapper<TaskDO> {
}
