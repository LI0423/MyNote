package com.video.manager.controller;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.AppQueryDTO;
import com.video.manager.domain.dto.TaskDTO;
import com.video.manager.domain.dto.TaskQueryDTO;
import com.video.manager.exception.ErrorCodeEnum;
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
@RequestMapping("/api/v1/task")
public class TaskController {
    @Autowired
    TaskService taskService;


    @GetMapping
    public ResponseResult find(@NotNull TaskQueryDTO query,
                               @NotNull Integer pageNum,
                               @NotNull Integer pageSize,
                               @Param("orderBy") String orderBy,
                               @Param("sequence") String sequence) {

        return ResponseResult.success(getResult(query, pageNum, pageSize, orderBy, sequence));
    }

    private PageResult<List<TaskDTO>> getResult(TaskQueryDTO query,
                                                Integer pageNum,
                                                Integer pageSize,
                                                String orderBy,
                                                String sequence
    ) {
        PageQuery<TaskQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);
        pageQuery.setSequence(sequence);
        pageQuery.setOrderBy(orderBy);

        return taskService.find(pageQuery);
    }

    @PostMapping
    public ResponseResult insert(@RequestBody TaskDTO taskDTO) {
        if (taskService.insert(taskDTO)) {
            return ResponseResult.success("新增成功!");
        }
        return ResponseResult.failure(ErrorCodeEnum.INSERT_FAILURE);
    }

    @PutMapping("/{id}")
    public ResponseResult update(@RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        taskDTO.setId(id);
        if (taskService.update(taskDTO)) {
            return ResponseResult.success("修改成功!");
        }
        return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        if (taskService.delete(id)) {
            return ResponseResult.success("删除成功!");
        }
        return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
    }

    @PostMapping("/copy")
    public ResponseResult copyTask(Long sourceAppId,Long targetAppId) {
        taskService.copyTask(sourceAppId,targetAppId);
        return ResponseResult.success("");
    }


}
