package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.video.entity.UserIncomeWithdrawalDO;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.UserIncomeWithdrawalDTO;
import com.video.manager.domain.dto.UserIncomeWithdrawalQueryDTO;
import com.video.manager.mapper.UserIncomeWithdrawalMapper;
import com.video.manager.service.UserIncomeWithdrawalService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserIncomeWithdrawalServiceImpl implements UserIncomeWithdrawalService {

    @Autowired
    UserIncomeWithdrawalMapper userIncomeWithdrawalMapper;

    @Override
    public PageResult<List<UserIncomeWithdrawalDTO>> getAllInfo(PageQuery<UserIncomeWithdrawalQueryDTO> query) {


        QueryWrapper<UserIncomeWithdrawalDO> queryWrapper = new QueryWrapper<>();

        if (query.getQuery().getAppId()!=null){
            queryWrapper.eq("app_id",query.getQuery().getAppId());
        }

        if (query.getQuery().getToken()!=null){
            queryWrapper.eq("token",query.getQuery().getToken());
        }

        if (query.getQuery().getCreateTime()!=null){
            queryWrapper.between("data_date",
                    query.getQuery().getBeginTime()+" 00:00:00",query.getQuery().getEndTime()+" 23:59:59");
        }

        if (query.getOrderBy()!=null){
            String orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, query.getOrderBy());
            if ("descending".equals(query.getSequence())){
                queryWrapper.orderByDesc(orderBy);
            }else {
                queryWrapper.orderByAsc(orderBy);
            }
        }else {
            queryWrapper.orderByDesc("withdraw_amount");
        }



        Page page = new Page(query.getPageNo(), query.getPageSize());
        IPage<UserIncomeWithdrawalDO> userIncomeWithdrawalDOIPage  = userIncomeWithdrawalMapper.selectPage(page, queryWrapper);

        List<UserIncomeWithdrawalDTO> resultList = Optional.ofNullable(userIncomeWithdrawalDOIPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(
                        userIncomeWithdrawalDO -> {
                            UserIncomeWithdrawalDTO userIncomeWithdrawalDTO = new UserIncomeWithdrawalDTO();
                            BeanUtils.copyProperties(userIncomeWithdrawalDO,userIncomeWithdrawalDTO);
                            userIncomeWithdrawalDTO.setWithdrawalAmount(userIncomeWithdrawalDO.getWithdrawAmount());
                            userIncomeWithdrawalDTO.setTotalWithdrawalAmount(userIncomeWithdrawalDO.getTotalWithdrawAmount());
                            userIncomeWithdrawalDTO.setUserId(userIncomeWithdrawalDO.getUserId().toString());
                            userIncomeWithdrawalDTO.setAppId(userIncomeWithdrawalDO.getAppId().toString());

                            Long profit = userIncomeWithdrawalDO.getShowIncomeAmount() - userIncomeWithdrawalDO.getWithdrawAmount();
                            Long cumulativeProfit = userIncomeWithdrawalDO.getTotalShowIncomeAmount() - userIncomeWithdrawalDO.getTotalWithdrawAmount();

                            userIncomeWithdrawalDTO.setProfit(profit);
                            userIncomeWithdrawalDTO.setCumulativeProfit(cumulativeProfit);

                            return userIncomeWithdrawalDTO;
                        }
                ).collect(Collectors.toList());

        PageResult pageResult = new PageResult();
        pageResult.setTotal(userIncomeWithdrawalDOIPage.getTotal());
        pageResult.setLists(resultList);

        return pageResult;
    }
}
