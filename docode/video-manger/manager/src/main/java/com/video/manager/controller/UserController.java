package com.video.manager.controller;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.*;
import com.video.manager.service.BlackUserService;
import com.video.manager.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yifan
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    BlackUserService blackUserService;

    @GetMapping
    public ResponseResult find(@NotNull UserQueryDTO query,
                               @NotNull Integer pageNum,
                               @NotNull Integer pageSize,
                               @Param("orderBy") String orderBy,
                               @Param("sequence") String sequence){

        return ResponseResult.success(userService.find(buildQuery(query, pageNum, pageSize, orderBy, sequence)));
    }

    private PageQuery<UserQueryDTO> buildQuery(UserQueryDTO query,
                                                Integer pageNum,
                                                Integer pageSize,
                                                String orderBy,
                                                String sequence
    ) {
        PageQuery<UserQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);
        pageQuery.setSequence(sequence);
        pageQuery.setOrderBy(orderBy);

        return pageQuery;
    }

    @GetMapping("/downloadExcel")
    public ResponseResult download(HttpServletResponse response, @NotNull UserQueryDTO query,
                                   @NotNull Integer pageNum,
                                   @NotNull Integer pageSize,
                                   @Param("orderBy") String orderBy,
                                   @Param("sequence") String sequence){
        userService.excel(response, buildQuery(query, pageNum, pageSize, orderBy, sequence));
        return ResponseResult.success("");
    }

    @PostMapping("/black")
    public ResponseResult addToBlackList(@RequestBody BlackUserDTO blackUserDTO) {
        blackUserService.add(blackUserDTO);
        return ResponseResult.success("");
    }

    @GetMapping("/black")
    public ResponseResult<List<BlackUserViewDTO>> allBlackUser() {
        return ResponseResult.success(blackUserService.list());
    }



    @DeleteMapping
    public ResponseResult del(String accessToken){
        userService.clearUser(accessToken);
        return ResponseResult.success("");
    }

    @PostMapping("/black/batch")
    public ResponseResult addToBlackListBatch(@RequestBody List<BlackUserDTO> blackUserDTOList) {
        blackUserService.addBatch(blackUserDTOList);
        return ResponseResult.success("");
    }
}
