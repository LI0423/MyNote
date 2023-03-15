package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.video.manager.domain.entity.VideoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * @program: video-manger
 * @description: video
 * @author: laojiang
 * @create: 2020-09-05 10:37
 **/
@Mapper
public interface VideoMapper extends BaseMapper<VideoDO> {
    /**
     * 关联分类表查询
     * @param page
     * @param queryWrapper
     * @return
     */
    @Select("select * from video a left join video_category b on a.id=b.video_id")
    IPage<VideoDO> findPage(Page page, Wrapper<VideoDO> queryWrapper);
}
