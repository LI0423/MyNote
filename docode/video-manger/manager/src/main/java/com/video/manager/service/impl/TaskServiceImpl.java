package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.video.entity.TaskConfigDO;
import com.video.entity.TaskDO;
import com.video.manager.domain.common.Constants;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.TaskDTO;
import com.video.manager.domain.dto.TaskQueryDTO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.TaskMapper;
import com.video.manager.service.AppService;
import com.video.manager.service.TaskConfigService;
import com.video.manager.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: video-manger
 * @description: task service impl
 * @author: laojiang
 * @create: 2020-09-04 10:25
 **/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, TaskDO> implements TaskService {

    @Autowired
    AppService appService;

    @Autowired
    TaskConfigService taskConfigService;

    @Override
    public Boolean insert(TaskDTO taskDTO) {

        TaskDO taskDO = new TaskDO();
        BeanUtils.copyProperties(taskDTO, taskDO);

        return save(taskDO);
    }

    @Override
    public Boolean update(TaskDTO taskDTO) {

        TaskDO taskDO = new TaskDO();
        BeanUtils.copyProperties(taskDTO, taskDO);
        taskDO.setId(taskDTO.getId());

        return updateById(taskDO);
    }

    @Override
    public Boolean delete(Long id) {

        return removeById(id);
    }

    @Override
    public PageResult<List<TaskDTO>> find(PageQuery<TaskQueryDTO> pageQuery) {
        TaskDO query = new TaskDO();
        BeanUtils.copyProperties(pageQuery.getQuery(), query);
        QueryWrapper queryWrapper = new QueryWrapper(query);

        List<Long> appIds = appService.listAppIds();
        if (query.getAppId() == null) {
            queryWrapper.in("app_id", appIds);
        } else {
            if (!appIds.contains(query.getAppId())) {
                throw new BusinessException(ErrorCodeEnum.APP_FORBIDDEN);
            }
        }

        if (pageQuery.getQuery().getTaskGroup() != null) {
            queryWrapper.eq("task_group",
                    pageQuery.getQuery().getTaskGroup());
        }

        String orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageQuery.getOrderBy());
        if (Constants.ASCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByAsc(orderBy);
        }

        if (Constants.DESCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByDesc(orderBy);
        }

        Page page = new Page(pageQuery.getPageNo(), pageQuery.getPageSize());

        IPage<TaskDO> taskDOIPage = page(page, queryWrapper);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(taskDOIPage.getTotal());

        List<TaskDTO> taskDTOList = Optional
                .ofNullable(taskDOIPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(
                        taskDO -> {
                            TaskDTO taskDTO = new TaskDTO();
                            BeanUtils.copyProperties(taskDO, taskDTO);
                            return taskDTO;
                        }
                )
                .collect(Collectors.toList());

        pageResult.setLists(taskDTOList);

        return pageResult;
    }

    @Override
    public void copyTask(Long sourceAppId,Long targetAppId){
        QueryWrapper<TaskDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_id",sourceAppId);
        List<TaskDO> sourceList  = getBaseMapper().selectList(queryWrapper);
        for (TaskDO t:sourceList) {
            Long sourceConfigId = t.getId();
            t.setId(null);
            t.setAppId(targetAppId);
            if (getBaseMapper().selectCount(new QueryWrapper<TaskDO>().eq("app_id",targetAppId)
                    .eq("code",t.getCode()).eq("name",t.getName())) == 0){
                getBaseMapper().insert(t);
            }
            QueryWrapper<TaskDO> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("app_id",targetAppId).eq("code",t.getCode()).eq("name",t.getName());
            TaskDO newTask = getBaseMapper().selectOne(queryWrapper1);
            taskConfigService.copyTaskConfig(sourceConfigId,newTask.getId());
        }
    }

}
