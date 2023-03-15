package com.video.manager.service;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.MaterialDTO;
import com.video.manager.domain.dto.MaterialQueryDTO;

import java.util.List;

/**
 * @program: api
 * @description: 视频服务类
 * @author: laojiang
 * @create: 2020-08-18 15:02
 **/
public interface MaterialService {

    /**
     * 查询视频信息
     * @param pageQuery
     * @return
     */
    PageResult<List<MaterialDTO>> query(PageQuery<MaterialQueryDTO> pageQuery);

    /**
     * 刷新视频的时间
     * @param materialId
     * @return
     */
    Boolean updateTime(Long materialId);


}
