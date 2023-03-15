package com.video.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.video.entity.*;
import com.video.manager.domain.common.Constants;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.UserDTO;
import com.video.manager.domain.dto.UserQueryDTO;
import com.video.manager.excel.ExcelUtil;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.*;
import com.video.manager.service.AppService;
import com.video.manager.service.UserService;
import com.video.manager.service.WithdrawalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: video-manger
 * @description: user service impl
 * @author: yifan
 * @create: 2020-09-04 10:24
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    WithdrawalMapper withdrawalMapper;

    @Autowired
    WithdrawalService withdrawalService;

    @Autowired
    AppService appService;

    @Autowired
    UserAuthMapper userAuthMapper;

    @Autowired
    UserExtMapper userExtMapper;

    @Autowired
    UserClockInMapper userClockInMapper;

    @Autowired
    UserLoginMapper userLoginMapper;

    @Autowired
    TaskLogMapper taskLogMapper;

    @Autowired
    GoldCoinMapper goldCoinMapper;

    @Autowired
    SignInMapper signInMapper;

    @Autowired
    ScoreMapper scoreMapper;

    @Autowired
    AdRewardTaskLogMapper adRewardTaskLogMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    private static final String STRING_USER_AUTH_DTO_BY_OPENID_KEY_PREFIX = "spredis:userauthdto:id:%s";
    private static final String STRING_USER_AUTH_DTO_BY_ACCSEE_TOKEN_KEY_PREFIX = "spredis:userauthdto:accesstoken:%s";
    private static final String STRING_USER_AUTH_DTO_BY_TOKEN_AND_APPID_KEY_PREFIX = "spredis:userauthdto:token:%s:aid:%s";

    private String buildUserAuthDTOByOpenIdKey(String openId) {
        return String.format(STRING_USER_AUTH_DTO_BY_OPENID_KEY_PREFIX, openId);
    }

    private String buildUserAuthDTOByAccessTokenKey(String accessToken) {
        return String.format(STRING_USER_AUTH_DTO_BY_ACCSEE_TOKEN_KEY_PREFIX, accessToken);
    }

    private String buildUserAuthDTOByTokenAndAppIdKey(String token, Long appId) {
        return String.format(STRING_USER_AUTH_DTO_BY_TOKEN_AND_APPID_KEY_PREFIX, token, appId);
    }

    @Override
    public PageResult<List<UserDTO>> find(PageQuery<UserQueryDTO> pageQuery) {
        return get(pageQuery, false);
    }

    public PageResult<List<UserDTO>> get(PageQuery<UserQueryDTO> pageQuery, boolean excel) {

        UserDO query = new UserDO();
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

        queryWrapper.groupBy("a.id,name,sex");

        String orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageQuery.getOrderBy());
        if (Constants.ASCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByAsc(orderBy);
        }

        if (Constants.DESCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByDesc(orderBy);
        }

        if (pageQuery.getQuery().getStartDate() != null && !"".equals(pageQuery.getQuery().getStartDate())) {
            queryWrapper.between("a.create_time",
                    pageQuery.getQuery().getStartDate() + " 00:00:00", pageQuery.getQuery().getEndDate() + " 23:59:59");
        }

        Page page = new Page(pageQuery.getPageNo(), pageQuery.getPageSize());
        if (excel) {
            page = new Page(pageQuery.getPageNo(), 100000000);
        }

        IPage<UserDTO> userDOIPage = userMapper.getUserDto(queryWrapper, page);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(userDOIPage.getTotal());

        pageResult.setLists(userDOIPage.getRecords());

        return pageResult;
    }

    @Override
    public void excel(HttpServletResponse response, PageQuery<UserQueryDTO> pageQuery) {
        PageResult<List<UserDTO>> pageResult = get(pageQuery, true);
        List<UserDTO> result = pageResult.getLists();
        String fileName = "user.xls";
        String[] headers = {"ID", "Name", "Sex", "Avatar", "Gold", "Score", "Times of Withdrawals", "Withdraw gold"};
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

    @Override
    public void withdrawTimes() {
        List<Map<String, Object>> userWithdrawTimes = withdrawalService.withdrawTimes();
        List<UserDO> userDOList = new ArrayList<>();
        for (Map<String, Object> userWithdrawTime : userWithdrawTimes) {
            UserDO userDO = new UserDO();
            userDO.setId(Long.parseLong(userWithdrawTime.get("user_id").toString()));
            userDO.setWithdrawalTimes(Long.parseLong(userWithdrawTime.get("times").toString()));
            userDOList.add(userDO);
        }
        updateBatchById(userDOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearUser(String accessToken) {
        UserAuthDO userAuthDO = userAuthMapper.selectOne(new QueryWrapper<UserAuthDO>().eq("access_token", accessToken));
        if(Objects.isNull(userAuthDO)){
            throw new BusinessException(ErrorCodeEnum.USER_NOT_FOUND);
        }

        Long userId = userAuthDO.getUserId();

        userMapper.delete(new QueryWrapper<UserDO>().eq("id", userId));
        userAuthMapper.delete(new QueryWrapper<UserAuthDO>().eq("user_id", userId));
        userExtMapper.delete(new QueryWrapper<UserExtDO>().eq("user_id", userId));
        userLoginMapper.delete(new QueryWrapper<UserLoginDO>().eq("user_id", userId));
        userClockInMapper.delete(new QueryWrapper<UserClockInDO>().eq("user_id", userId));
        taskLogMapper.delete(new QueryWrapper<TaskLogDO>().eq("user_id", userId));
        goldCoinMapper.delete(new QueryWrapper<GoldCoinDO>().eq("user_id", userId));
        signInMapper.delete(new QueryWrapper<SignInDO>().eq("user_id", userId));
        scoreMapper.delete(new QueryWrapper<ScoreDO>().eq("user_id", userId));
        adRewardTaskLogMapper.delete(new QueryWrapper<AdRewardTaskLogDO>().eq("user_id", userId));


        List<String> userKeys = Arrays.asList(
                buildUserAuthDTOByOpenIdKey(userAuthDO.getOpenId()),
                buildUserAuthDTOByAccessTokenKey(accessToken),
                buildUserAuthDTOByTokenAndAppIdKey(userAuthDO.getToken(), userAuthDO.getAppId()));

        redisTemplate.delete(userKeys);
    }

}
