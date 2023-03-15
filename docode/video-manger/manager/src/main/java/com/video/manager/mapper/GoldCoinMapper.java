package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.video.entity.GoldCoinDO;
import com.video.manager.domain.dto.GoldCoinDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: video-manger
 * @description: gold coin mapper
 * @author: laojiang
 * @create: 2020-09-03 11:28
 **/
@Mapper

public interface GoldCoinMapper extends BaseMapper<GoldCoinDO> {
    @Select("select a.id,a.create_time,a.user_id,b.name task_name,a.number " +
            "from gold_coin a " +
            "left join task b on a.task_code = b.code and a.app_id=b.app_id ${ew.customSqlSegment}")
    IPage<GoldCoinDTO> getGoldDto(@Param(Constants.WRAPPER) Wrapper wrapper, Page<GoldCoinDTO> page);
}
