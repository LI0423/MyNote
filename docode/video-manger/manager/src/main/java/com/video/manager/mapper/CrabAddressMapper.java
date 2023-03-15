package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.manager.domain.entity.CrabAddressDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: video-manger
 * @description: 抓取链接
 * @author: laojiang
 * @create: 2020-09-03 11:36
 **/
@Mapper
public interface CrabAddressMapper extends BaseMapper<CrabAddressDO> {
}
