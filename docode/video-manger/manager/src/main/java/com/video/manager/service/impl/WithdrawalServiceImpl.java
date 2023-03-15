package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.video.entity.UserDO;
import com.video.entity.WithdrawalDO;
import com.video.entity.WithdrawalStatusEnum;
import com.video.entity.WithdrawalTypeEnum;
import com.video.manager.domain.common.Constants;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.ProWithdrawalDTO;
import com.video.manager.domain.dto.WithdrawalDTO;
import com.video.manager.domain.dto.WithdrawalQueryDTO;
import com.video.manager.excel.ExcelUtil;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.UserMapper;
import com.video.manager.mapper.WithdrawalMapper;
import com.video.manager.service.AppService;
import com.video.manager.service.WithdrawalService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: video-manger
 * @description: WithdrawalService
 * @author: laojiang
 * @create: 2020-09-04 14:08
 **/
@Service
public class WithdrawalServiceImpl extends ServiceImpl<WithdrawalMapper, WithdrawalDO> implements WithdrawalService {
    @Autowired
    WithdrawalMapper withdrawalMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AppService appService;

    @Override
    public PageResult<ProWithdrawalDTO> find(PageQuery<WithdrawalQueryDTO> pageQuery) {
        return get(pageQuery, false);
    }

    public PageResult<ProWithdrawalDTO> get(PageQuery<WithdrawalQueryDTO> pageQuery, boolean excel) {
        WithdrawalDO query = new WithdrawalDO();
        BeanUtils.copyProperties(pageQuery.getQuery(), query);

        if (pageQuery.getQuery().getStatus() != null) {
            query.setStatus(WithdrawalStatusEnum.values()[pageQuery.getQuery().getStatus()]);
        }

        QueryWrapper queryWrapper = new QueryWrapper(query);

        List<Long> appIds = appService.listAppIds();
        if (query.getAppId() == null) {
            queryWrapper.in("app_id", appIds);
        } else {
            if (!appIds.contains(query.getAppId())) {
                throw new BusinessException(ErrorCodeEnum.APP_FORBIDDEN);
            }
        }

        if (pageQuery.getQuery().getCashType() != null) {
            if (pageQuery.getQuery().getCashType() == 1) {
                queryWrapper.in("type", Arrays.asList(WithdrawalTypeEnum.COMMON_GOLD_BEAN_DAILY_MANY, WithdrawalTypeEnum.COMMON_GOLD_BEAN_MANY));
            } else {
                queryWrapper.notIn("type", Arrays.asList(WithdrawalTypeEnum.COMMON_GOLD_BEAN_DAILY_MANY, WithdrawalTypeEnum.COMMON_GOLD_BEAN_MANY));
            }
        }


        String orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageQuery.getOrderBy());
        if (Constants.ASCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByAsc(orderBy);
        }

        if (Constants.DESCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByDesc(orderBy);
        }

        if (pageQuery.getQuery().getStartDate() != null && !"".equals(pageQuery.getQuery().getStartDate())) {
            queryWrapper.between("create_time",
                    pageQuery.getQuery().getStartDate() + " 00:00:00", pageQuery.getQuery().getEndDate() + " 23:59:59");
        }

        Page page = new Page(pageQuery.getPageNo(), pageQuery.getPageSize());
        if (excel) {
            page = new Page(1, 100000000);
        }
        IPage<WithdrawalDO> withdrawalDOIPage = page(page, queryWrapper);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(withdrawalDOIPage.getTotal());

        List<WithdrawalDTO> withdrawalDTOList = Optional
                .ofNullable(withdrawalDOIPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(
                        withdrawalDO -> {
                            WithdrawalDTO withdrawalDTO = new WithdrawalDTO();
                            BeanUtils.copyProperties(withdrawalDO, withdrawalDTO);
                            log.debug(String.valueOf(withdrawalDO.getType()));
                            if (WithdrawalTypeEnum.COMMON_GOLD_BEAN_MANY == withdrawalDO.getType() ||WithdrawalTypeEnum.COMMON_GOLD_BEAN_DAILY_MANY ==withdrawalDO.getType()) {
                                withdrawalDTO.setCashType(1);
                            } else {
                                withdrawalDTO.setCashType(0);
                            }
                            withdrawalDTO.setUserId(withdrawalDO.getUserId() + "");
                            withdrawalDTO.setAppId(withdrawalDO.getAppId() + "");
                            withdrawalDTO.setId(withdrawalDO.getId() + "");
                            if (WithdrawalTypeEnum.COMMON_GOLD_BEAN_MANY == withdrawalDO.getType()
                                    || WithdrawalTypeEnum.COMMON_GOLD_BEAN_DAILY_MANY == withdrawalDO.getType()) {
                                withdrawalDTO.setAmount(withdrawalDO.getGoldBeanAmount());
                                withdrawalDTO.setNumber(withdrawalDO.getGoldBean());
                            }

                            UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("id", withdrawalDO.getUserId()));
                            if (userDO != null && userDO.getCreateTime() != null) {
                                withdrawalDTO.setUserCreateTime(userDO.getCreateTime());
                                Double diffHour = (withdrawalDTO.getCreateTime().getTime() - withdrawalDTO.getUserCreateTime().getTime()) / (1000 * 60 * 60 * 1.0);
                                diffHour = Double.parseDouble(String.format("%.2f", diffHour));
                                withdrawalDTO.setDiffHour(diffHour);
                            }

                            withdrawalDTO.setArpu(withdrawalDO.getArpu());
                            return withdrawalDTO;
                        }
                )
                .collect(Collectors.toList());

        ProWithdrawalDTO proWithdrawalDTO = new ProWithdrawalDTO();
        proWithdrawalDTO.setList(withdrawalDTOList);
        proWithdrawalDTO.setTotalAmount(withdrawalMapper.getTotalAmount(queryWrapper));

        pageResult.setLists(proWithdrawalDTO);

        return pageResult;
    }

    @Override
    public List<Map<String, Object>> withdrawTimes() {
        QueryWrapper<WithdrawalDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id,count(*) times").eq("status", 0).groupBy("user_id");
        return withdrawalMapper.selectMaps(queryWrapper);

    }

    @Override
    public void excel(HttpServletResponse response, PageQuery<WithdrawalQueryDTO> pageQuery) {
        PageResult<ProWithdrawalDTO> pageResult = get(pageQuery, true);
        ProWithdrawalDTO results = pageResult.getLists();
        if (Objects.isNull(results)) {
            return;
        }

        List<WithdrawalDTO> result = results.getList();
        String fileName = "withdraw.xls";
        String[] headers = {"id", "pkg", "userId", "withdrawDate", "withdrawMethod", "number", "status", "userCreateTime", "diffHour", "token", "arpu","红包0，金豆1"};
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
