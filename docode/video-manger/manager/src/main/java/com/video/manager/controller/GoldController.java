package com.video.manager.controller;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.GoldCoinDTO;
import com.video.manager.domain.dto.GoldCoinQueryDTO;
import com.video.manager.service.GoldCoinService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yifan
 */
@RestController
@RequestMapping("/api/v1/goldCoin")
public class GoldController {
    @Autowired
    GoldCoinService goldCoinService;

    @GetMapping
    public ResponseResult find(@NotNull GoldCoinQueryDTO query,
                               @NotNull Integer pageNum,
                               @NotNull Integer pageSize,
                               @Param("orderBy") String orderBy,
                               @Param("sequence") String sequence){

        return ResponseResult.success(goldCoinService.find(buildQuery(query, pageNum, pageSize, orderBy, sequence)));
    }


    private PageQuery<GoldCoinQueryDTO> buildQuery(GoldCoinQueryDTO query,
                                                    Integer pageNum,
                                                    Integer pageSize,
                                                    String orderBy,
                                                    String sequence
    ) {
        PageQuery<GoldCoinQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);
        pageQuery.setSequence(sequence);
        pageQuery.setOrderBy(orderBy);

        return pageQuery;
    }


    @GetMapping("/downloadExcel")
    public ResponseResult download(HttpServletResponse response, @NotNull GoldCoinQueryDTO query,
                                   @NotNull Integer pageNum,
                                   @NotNull Integer pageSize,
                                   @Param("orderBy") String orderBy,
                                   @Param("sequence") String sequence){
        goldCoinService.excel(response, buildQuery(query, pageNum, pageSize, orderBy, sequence));
        return ResponseResult.success("");
    }

}
