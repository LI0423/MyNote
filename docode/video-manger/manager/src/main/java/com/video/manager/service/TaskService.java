package com.video.manager.service;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.TaskDTO;
import com.video.manager.domain.dto.TaskQueryDTO;

import java.util.List;

/**
 * @program: video-manger
 * @description: task service
 * @author: laojiang
 * @create: 2020-09-03 14:24
 **/
public interface TaskService {
    /**
     * 添加
     * @param taskDTO
     * @return
     */
    Boolean insert(TaskDTO taskDTO);

    /**
     * 修改
     * @param taskDTO
     * @param id
     * @return
     */
    Boolean update(TaskDTO taskDTO);

    /**
     * 删除
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * 查询任务列表
     * @param pageQuery
     * @return
     */
    PageResult<List<TaskDTO>> find(PageQuery<TaskQueryDTO> pageQuery);

    void copyTask(Long sourceAppId,Long targetAppId);
}
