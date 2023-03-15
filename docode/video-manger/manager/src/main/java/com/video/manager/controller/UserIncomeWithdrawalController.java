package com.video.manager.controller;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.UserIncomeWithdrawalQueryDTO;
import com.video.manager.service.UserIncomeWithdrawalService;
import com.video.manager.util.DateSeperateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/withdrawal/user")
public class UserIncomeWithdrawalController {

    @Autowired
    UserIncomeWithdrawalService userIncomeWithdrawalService;

    @GetMapping("/list")
    public ResponseResult getAllInfo(@NotNull UserIncomeWithdrawalQueryDTO query,
                                     @RequestParam("pageNum")Integer pageNum,
                                     @RequestParam("pageSize")Integer pageSize,
                                     @RequestParam(value = "orderBy",required = false)String orderBy,
                                     @RequestParam(value = "sequence",required = false)String sequence){

        return ResponseResult.success(userIncomeWithdrawalService.getAllInfo(buildQuery(query, pageNum, pageSize, orderBy, sequence)));

    }

    private PageQuery<UserIncomeWithdrawalQueryDTO> buildQuery(UserIncomeWithdrawalQueryDTO query,
                                                               Integer pageNum,
                                                               Integer pageSize,
                                                               String orderBy,
                                                               String sequence
    ) {
        PageQuery<UserIncomeWithdrawalQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);
        pageQuery.setSequence(sequence);
        pageQuery.setOrderBy(orderBy);

        if (pageQuery.getQuery().getCreateTime()!=null){
            return DateSeperateUtil.pageQueryInit(pageQuery);
        }else {
            return pageQuery;
        }
    }

}
