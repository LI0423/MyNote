package com.video.manager.service;

import com.video.manager.domain.dto.TaskConfigDTO;
import com.video.manager.domain.dto.TaskConfigOptionDTO;

import java.util.List;

/**
 * @program: video-manger
 * @description: task config service
 * @author: laojiang
 * @create: 2020-09-03 14:32
 **/
public interface TaskConfigService {

    /**
     * 查询任务的所有配置
     * @param taskId
     * @return
     */
    List<TaskConfigDTO> findByTaskId(Long taskId);

    /**
     * 保存任务的配置
     * @param taskConfigDTOList
     * @param taskId
     * @return
     */
    Boolean save(List<TaskConfigDTO> taskConfigDTOList, Long taskId);

    TaskConfigOptionDTO listOption();

    void copyTaskConfig(Long sourceTaskId,Long targetTaskId);


}
