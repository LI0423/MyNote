package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.TaskLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: video-manger
 * @description: task log mapper
 * @author: laojiang
 * @create: 2020-09-03 11:32
 **/
@Mapper
public interface TaskLogMapper extends BaseMapper<TaskLogDO> {
}
