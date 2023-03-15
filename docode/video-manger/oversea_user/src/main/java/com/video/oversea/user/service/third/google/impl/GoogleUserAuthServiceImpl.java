package com.video.oversea.user.service.third.google.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.video.entity.GoogleUserAuthDO;
import com.video.oversea.user.cache.GoogleUserAuthCache;
import com.video.oversea.user.domain.dto.user.GoogleUserAuthDTO;
import com.video.oversea.user.exception.BusinessException;
import com.video.oversea.user.exception.ErrorCodeEnum;
import com.video.oversea.user.mapper.GoogleUserAuthMapper;
import com.video.oversea.user.service.third.google.GoogleUserAuthService;
import com.video.oversea.user.util.IdentifierGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GoogleUserAuthServiceImpl implements GoogleUserAuthService {

    private static final String ACCESS_TOKEN_PREFIX = "MGTSK";

    @Autowired
    private GoogleUserAuthMapper googleUserAuthMapper;

    @Autowired
    private GoogleUserAuthCache googleUserAuthCache;

    @Override
    public GoogleUserAuthDTO findByGoogleUserId(String googleUserId) {
        GoogleUserAuthDTO googleUserAuthDTO = googleUserAuthCache.getGoogleUserAuthDtoByGoogleUserId(googleUserId);
        if (googleUserAuthDTO == null) {
            List<GoogleUserAuthDO> googleUserAuthDOList = googleUserAuthMapper.selectListByGoogleUserId(googleUserId);
            if (CollectionUtils.isEmpty(googleUserAuthDOList)) {
                googleUserAuthCache.setGoogleUserAuthDtoByGoogleUserId(googleUserId, null);
                log.debug("GoogleUserAuthDO object cannot be obtained through googleUserId, googleUserId:{}", googleUserId);
                return null;
            }
            if (googleUserAuthDOList.size() > 1) {
                log.warn("More than one GoogleUserAuthDO object obtained through googleUserId, googleUserId:{}", googleUserId);
            }
            GoogleUserAuthDO googleUserAuthDO = googleUserAuthDOList.get(0);
            googleUserAuthDTO = new GoogleUserAuthDTO();
            BeanUtils.copyProperties(googleUserAuthDO, googleUserAuthDTO);
            googleUserAuthCache.setGoogleUserAuthDtoByGoogleUserId(googleUserId, googleUserAuthDTO);
        } else {
            if (googleUserAuthDTO.getGoogleUserId() == null) {
                log.debug("UserAuthDO object cannot be obtained through googleUserId from cache, googleUserId:{}", googleUserId);
                return null;
            }
        }
        return googleUserAuthDTO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createByGoogleUserId(Long userId, String googleUserId, Long appId) {
        if (userId == null || googleUserId == null) {
            throw new IllegalArgumentException("required parameters are missing");
        }

        String accessToken = generatAccessToken();
        Date now = new Date();

        GoogleUserAuthDO googleUserAuthDO = new GoogleUserAuthDO();
        googleUserAuthDO.setUserId(userId);
        googleUserAuthDO.setAppId(appId);
        googleUserAuthDO.setGoogleUserId(googleUserId);
        googleUserAuthDO.setAccessToken(accessToken);
        googleUserAuthDO.setCreateTime(now);

        googleUserAuthMapper.insert(googleUserAuthDO);

        // 清空缓存
        googleUserAuthCache.delGoogleUserAuthDtoByGoogleUserId(googleUserId);
        googleUserAuthCache.delGoogleUserAuthDtoByAccessToken(accessToken);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String updateAccessToken(String googleUserId) {

        GoogleUserAuthDTO byGoogleUserId = findByGoogleUserId(googleUserId);
        googleUserAuthCache.delGoogleUserAuthDtoByGoogleUserId(byGoogleUserId.getGoogleUserId());
        googleUserAuthCache.delGoogleUserAuthDtoByAccessToken(byGoogleUserId.getAccessToken());

        String accessToken = generatAccessToken();
        GoogleUserAuthDO googleUserAuthDO = new GoogleUserAuthDO();
        googleUserAuthDO.setAccessToken(accessToken);
        googleUserAuthDO.setModifyTime(new Date());
        int rows = googleUserAuthMapper.update(googleUserAuthDO, new UpdateWrapper<GoogleUserAuthDO>().eq("google_user_id", googleUserId));
        if (rows <= 0) {
            throw new BusinessException(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND);
        }
        return accessToken;
    }

    @Override
    public GoogleUserAuthDTO findByAccessToken(String accessToken) {
        GoogleUserAuthDTO googleUserAuthDTO = googleUserAuthCache.getGoogleUserAuthDtoByAccessToken(accessToken);
        if (googleUserAuthDTO == null) {
            List<GoogleUserAuthDO> googleUserAuthDOList = googleUserAuthMapper.selectListByAccessToken(accessToken);
            if (googleUserAuthDOList.isEmpty()) {
                googleUserAuthCache.setGoogleUserAuthDtoByAccessToken(accessToken, null);
                log.warn("GoogleUserAuthDO object cannot be obtained through access token, access token:{}", accessToken);
                return null;
            }
            if (googleUserAuthDOList.size() > 1) {
                log.warn("More than one GoogleUserAuthDO object obtained through access token, access token:{}", accessToken);
            }
            GoogleUserAuthDO googleUserAuthDO = googleUserAuthDOList.get(0);
            googleUserAuthDTO = new GoogleUserAuthDTO();
            BeanUtils.copyProperties(googleUserAuthDO, googleUserAuthDTO);
            googleUserAuthCache.setGoogleUserAuthDtoByAccessToken(accessToken, googleUserAuthDTO);
        } else {
            if (googleUserAuthDTO.getAccessToken() == null) {
                log.warn("GoogleUserAuthDO object cannot be obtained through access token, access token:{}", accessToken);
                return null;
            }
        }
        return googleUserAuthDTO;
    }

    @Override
    public void logout(String accessToken) {
        // todo：啥都不做，等用户调用登陆接口就刷新accessToken
        GoogleUserAuthDTO byAccessToken = findByAccessToken(accessToken);
        // 清除缓存
        googleUserAuthCache.delGoogleUserAuthDtoByAccessToken(accessToken);
        googleUserAuthCache.delGoogleUserAuthDtoByGoogleUserId(byAccessToken.getGoogleUserId());
    }

    private String generatAccessToken() {
        return ACCESS_TOKEN_PREFIX + IdentifierGeneratorUtils.nextId();
    }
}
