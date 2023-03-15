package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.video.entity.*;
import com.video.manager.domain.dto.TaskConfigDTO;
import com.video.manager.domain.dto.TaskConfigOptionDTO;
import com.video.manager.mapper.TaskConfigMapper;
import com.video.manager.mapper.TaskMapper;
import com.video.manager.service.TaskConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: video-manger
 * @description: task config service
 * @author: laojiang
 * @create: 2020-09-04 10:40
 **/

@Service
public class TaskConfigServiceImpl extends ServiceImpl<TaskConfigMapper, TaskConfigDO> implements TaskConfigService {

    private TaskConfigMapper taskConfigMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    TaskMapper taskMapper;

    private static final String STRING_TASK_CONFIG_BY_TASK_ID_AND_KEY_KEY_PREFIX = "spredis:taskconfig:tid:%s:cKey:%s";

    private static final String STRING_USER_WITHDRAWAL_STATUS_TMPL_LIST_KEY_TMPL = "spredis:withdrawalstatustmpllist:ai:%s";


    @Autowired
    public TaskConfigServiceImpl(TaskConfigMapper taskConfigMapper) {
        this.taskConfigMapper = taskConfigMapper;
    }

    @Override
    public List<TaskConfigDTO> findByTaskId(Long taskId) {

        List<TaskConfigDO> taskConfigDOList = list(new QueryWrapper<TaskConfigDO>().eq("task_id", taskId));

        List<TaskConfigDTO> taskConfigDTOList = Optional
                .ofNullable(taskConfigDOList)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(
                        taskConfigDO -> {
                            TaskConfigDTO taskConfigDTO = new TaskConfigDTO();
                            BeanUtils.copyProperties(taskConfigDO, taskConfigDTO);
                            return taskConfigDTO;
                        })
                .collect(Collectors.toList());

        return taskConfigDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(List<TaskConfigDTO> taskConfigDTOList, Long taskId) {

        taskConfigMapper.delete(new QueryWrapper<TaskConfigDO>().eq("task_id", taskId));

        List<TaskConfigDO> taskConfigDOList = new ArrayList<>();
        List<String> taskKeys = new ArrayList<>();
        for (TaskConfigDTO taskConfigDTO : taskConfigDTOList) {
            TaskConfigDO taskConfigDO = new TaskConfigDO();
            BeanUtils.copyProperties(taskConfigDTO, taskConfigDO);
            taskConfigDO.setTaskId(taskId);
            taskConfigDOList.add(taskConfigDO);
            taskKeys.add(String.format(STRING_TASK_CONFIG_BY_TASK_ID_AND_KEY_KEY_PREFIX, taskId, taskConfigDO.getCKey()));
        }

        TaskDO taskDO = taskMapper.selectById(taskId);
        redisTemplate.delete(String.format(STRING_USER_WITHDRAWAL_STATUS_TMPL_LIST_KEY_TMPL, taskDO.getAppId()));
        redisTemplate.delete(taskKeys);

        return saveBatch(taskConfigDOList);
    }

    @Override
    public TaskConfigOptionDTO listOption() {
        HashMap<String, String> groupMap = new HashMap<>();
        for (TaskGroupEnum group : TaskGroupEnum.values()) {
            groupMap.put(group.getCode() + "", group.getDescription());
        }

        HashMap<String, String> codeMap = new HashMap<>();
        for (TaskCodeEnum code : TaskCodeEnum.values()) {
            codeMap.put(code.getCode() + "", code.getDescription());
        }

        HashMap<String, String> typeMap = new HashMap<>();
        for (TaskTypeEnum typeEnum : TaskTypeEnum.values()) {
            typeMap.put(typeEnum.getCode() + "", typeEnum.getDescription());
        }

        return TaskConfigOptionDTO.builder().group(groupMap).type(typeMap).code(codeMap).build();
    }

    @Override
    public void copyTaskConfig(Long sourceTaskId,Long targetTaskId){
        QueryWrapper<TaskConfigDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_id",sourceTaskId);
        List<TaskConfigDO> list = getBaseMapper().selectList(queryWrapper);
        for (TaskConfigDO t:list) {
            Integer count = getBaseMapper().selectCount(new QueryWrapper<TaskConfigDO>().eq("task_id",targetTaskId).eq("c_key",t.getCKey()));
            if (count>0){
                UpdateWrapper<TaskConfigDO> updateWrapper = new UpdateWrapper<>();
                getBaseMapper().update(null,updateWrapper.eq("task_id",targetTaskId).eq("c_key",t.getCKey()).set("value",t.getValue()));
            }else {
                t.setId(null);
                t.setTaskId(targetTaskId);
                save(t);
            }
        }
    }
}
