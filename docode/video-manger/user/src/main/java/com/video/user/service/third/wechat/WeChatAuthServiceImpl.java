package com.video.user.service.third.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.user.config.prop.WeChatAuthProperties;
import com.video.user.domain.dto.third.AuthDTO;
import com.video.user.domain.dto.third.UserInfoDTO;
import com.video.user.exception.BusinessException;
import com.video.user.exception.ErrorCodeEnum;
import com.video.user.service.third.ThirdAuthService;
import com.video.user.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: mango
 * @description: 微信的认证实现类
 * @author: laojiang
 * @create: 2020-09-11 11:54
 **/
@Slf4j
@Component("WE_CHAT_AUTH")
public class WeChatAuthServiceImpl implements ThirdAuthService {

    @Autowired
    private HttpClientBuilder httpClientBuilder;

    @Autowired
    private WeChatAuthProperties weChatAuthProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public AuthDTO auth(String appId, String secret, String code) {
        String originGetAccessTokenUrl = weChatAuthProperties.getGetAccessTokenUrl();
        if (StringUtils.isBlank(originGetAccessTokenUrl)) {
            throw new BusinessException(ErrorCodeEnum.BUSINESS_DATA_NOT_FOUND, "access token url is bank.");
        }
        // TODO: 后面改成宏操作
        String getAccessTokenUrl = String.format(originGetAccessTokenUrl, appId,
                secret, code);
        try {
            String result = HttpClientUtils.doGet(httpClientBuilder, getAccessTokenUrl);
            AuthDTO authDTO = objectMapper.readValue(result, AuthDTO.class);
            if (authDTO.getAccessToken() == null || authDTO.getOpenId()== null) {
                log.warn("get WeChat Auth, missing data, result:{}", result);
                return null;
            }
            return authDTO;
        } catch (IOException e) {
            log.error("access token GET error", e);
        }
        return null;
    }

    @Override
    public UserInfoDTO getUserInfo(String accessToken, String openId) {
        String originGetUserInfoUrl = weChatAuthProperties.getGetUserInfoUrl();

        // TODO: 后面改成宏操作
        String getUserInfoUrl = String.format(originGetUserInfoUrl, accessToken, openId);

        try {
            String result = HttpClientUtils.doGet(httpClientBuilder, getUserInfoUrl);
            UserInfoDTO userInfoDTO = objectMapper.readValue(result, UserInfoDTO.class);
            if (userInfoDTO.getOpenId() == null || userInfoDTO.getUnionId() == null) {
                log.warn("get WeChat user info, missing data, result:{}", result);
                return null;
            }
            return userInfoDTO;
        } catch (IOException e) {
            log.error("user info GET error", e);
        }
        return null;
    }
}
