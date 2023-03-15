package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.video.entity.GoldCoinDO;
import com.video.manager.domain.common.Constants;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.GoldCoinDTO;
import com.video.manager.domain.dto.GoldCoinQueryDTO;
import com.video.manager.excel.ExcelUtil;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.GoldCoinMapper;
import com.video.manager.service.AppService;
import com.video.manager.service.GoldCoinService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: video-manger
 * @description: GoldCoinService
 * @author: laojiang
 * @create: 2020-09-04 14:07
 **/
@Service
public class GoldCoinServiceImpl extends ServiceImpl<GoldCoinMapper, GoldCoinDO> implements GoldCoinService {
    @Autowired
    GoldCoinMapper goldCoinMapper;
    @Autowired
    AppService appService;

    @Override
    public PageResult<List<GoldCoinDTO>> find(PageQuery<GoldCoinQueryDTO> pageQuery) {
        return get(pageQuery, false);
    }

    public PageResult<List<GoldCoinDTO>> get(PageQuery<GoldCoinQueryDTO> pageQuery, boolean excel) {
        if(StringUtils.isEmpty(pageQuery.getQuery().getStartDate())){
            throw new BusinessException(ErrorCodeEnum.DATE_EMPTY);
        }
        GoldCoinDO query = new GoldCoinDO();
        BeanUtils.copyProperties(pageQuery.getQuery(), query);

        QueryWrapper queryWrapper = new QueryWrapper(query);

        List<Long> appIds = appService.listAppIds();
        if (query.getAppId() == null) {
            queryWrapper.in("a.app_id", appIds);
        } else {
            if (!appIds.contains(query.getAppId())) {
                throw new BusinessException(ErrorCodeEnum.APP_FORBIDDEN);
            }
            queryWrapper.eq("a.app_id", query.getAppId());
        }


        if (pageQuery.getQuery().getStartDate() != null && !"".equals(pageQuery.getQuery().getStartDate())) {
            queryWrapper.between("a.create_time",
                    pageQuery.getQuery().getStartDate() + " 00:00:00", pageQuery.getQuery().getEndDate() + " 23:59:59");
        }

        if (pageQuery.getQuery().getUserId() != null) {
            queryWrapper.eq("a.user_id",
                    pageQuery.getQuery().getUserId());
        }

        String orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageQuery.getOrderBy());

        if (Constants.ASCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByAsc(orderBy);
        }

        if (Constants.DESCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByDesc(orderBy);
        }

        Page page = new Page(pageQuery.getPageNo(), pageQuery.getPageSize());
        if (excel) {
            page = new Page(1, 100000000);
        }

        IPage<GoldCoinDTO> goldDOIPage = goldCoinMapper.getGoldDto(queryWrapper, page);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(goldDOIPage.getTotal());

        pageResult.setLists(goldDOIPage.getRecords());

        return pageResult;
    }


    @Override
    public void excel(HttpServletResponse response, PageQuery<GoldCoinQueryDTO> pageQuery) {
        PageResult<List<GoldCoinDTO>> pageResult = get(pageQuery, true);
        List<GoldCoinDTO> result = pageResult.getLists();
        String fileName = "gold.xls";
        String[] headers = {"ID", "Datadate", "SignId", "TaskName", "Reward"};
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        try (ServletOutputStream out = response.getOutputStream()) {
            ExcelUtil.exportExcel(headers, result, out);
        } catch (IOException e) {
            log.error("export excel failed!", e);
        }
    }


}
