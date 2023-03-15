package com.video.manager.controller;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.TaskConfigDTO;
import com.video.manager.domain.dto.TaskDTO;
import com.video.manager.domain.dto.TaskQueryDTO;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.TaskConfigService;
import com.video.manager.service.TaskService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yifan
 */
@RestController
@RequestMapping("/api/v1/taskConfig")
public class TaskConfigController {
    @Autowired
    TaskConfigService taskConfigService;


    @GetMapping("{id}")
    public ResponseResult find(@PathVariable Long id) {
        return ResponseResult.success(taskConfigService.findByTaskId(id));
    }


    @PostMapping("{id}")
    public ResponseResult insert(@RequestBody List<TaskConfigDTO> taskConfigDTO,@PathVariable Long id) {
        if (taskConfigService.save(taskConfigDTO, id)) {
            return ResponseResult.success("新增成功!");
        }
        return ResponseResult.failure(ErrorCodeEnum.INSERT_FAILURE);
    }


    @GetMapping("/option")
    public ResponseResult listOption() {
        return ResponseResult.success(taskConfigService.listOption());
    }
}
