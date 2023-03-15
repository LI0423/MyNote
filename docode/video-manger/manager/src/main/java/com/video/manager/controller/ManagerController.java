package com.video.manager.controller;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.AppQueryDTO;
import com.video.manager.domain.dto.ManagerDTO;
import com.video.manager.domain.entity.ManagerDO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * @program: api
 * @description: manager
 * @author: laojiang
 * @create: 2020-08-19 14:15
 **/
@RestController
@RequestMapping("/api/v1/account")
@Slf4j
public class ManagerController {

    private ManagerService managerService;


    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/info")
    public ResponseResult get() {
        Subject subject = SecurityUtils.getSubject();
        String email = subject.getPrincipal().toString();
        ManagerDTO managerDTO = managerService.getByEmail(email);
        if (managerDTO == null) {
            return ResponseResult.failure(ErrorCodeEnum.USER_NOT_FOUND);
        }
        return ResponseResult.success(managerDTO);
    }


    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    @PutMapping("/{id}")
    public ResponseResult auth(@RequestBody ManagerDTO managerDTO) {
        managerService.update(managerDTO);
        return ResponseResult.success("ok");
    }

    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    @PostMapping
    public ResponseResult add(@RequestBody ManagerDTO managerDTO) {
        managerService.add(managerDTO);
        return ResponseResult.success("ok");
    }

    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        managerService.delete(id);
        return ResponseResult.success("删除成功!");
    }

    @GetMapping("listAuth")
    public ResponseResult listAuth() {
        return ResponseResult.success(managerService.listAuth());
    }

    @GetMapping("list")
    public ResponseResult list(@NotNull ManagerDTO query,
                               @NotNull Integer pageNum,
                               @NotNull Integer pageSize,
                               @Param("orderBy") String orderBy,
                               @Param("sequence") String sequence) {
        PageQuery<ManagerDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);
        pageQuery.setSequence(sequence);
        pageQuery.setOrderBy(orderBy);

        return ResponseResult.success(managerService.list(pageQuery));
    }
}
