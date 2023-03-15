package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.ScoreDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: video-manger
 * @description: score mapper
 * @author: laojiang
 * @create: 2020-09-03 11:29
 **/
@Mapper
public interface ScoreMapper extends BaseMapper<ScoreDO> {
}
