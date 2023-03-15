package com.video.manager.service;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.MaterialDTO;
import com.video.manager.domain.dto.MaterialQueryDTO;
import com.video.manager.domain.dto.VideoDTO;
import com.video.manager.domain.dto.VideoQueryDTO;

import java.util.List;

/**
 * @program: api
 * @description: 视频服务类
 * @author: laojiang
 * @create: 2020-08-18 15:02
 **/
public interface VideoService {

    /**
     * 查询视频信息
     * @param pageQuery
     * @return
     */
    PageResult<List<VideoDTO>> query(PageQuery<VideoQueryDTO> pageQuery);

    /**
     * 更新视频的分类
     * @param categoryId
     * @param videoId
     * @return
     */
    void addCategory(Integer categoryId,Long videoId);
    /**
     * 更新视频的分类
     * @param categoryId
     * @param videoId
     * @return
     */
    void deleteCategory(Integer categoryId,Long videoId);

    void deleteCategory(Integer categoryId);

    Boolean updateCategory(List<Integer> categoryIdList,Long videoId);
    /**
     * 更新视频级别
     * @param videoId
     * @param contentLevel
     * @return
     */
    Boolean updateContentLevel(Long videoId,Integer contentLevel);

    Boolean delete(Long videoId);

    Boolean refresh(Long videoId);

    void sync();

}
