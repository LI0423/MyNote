package com.video.user.cache.impl;

import com.video.user.cache.UserAuthCache;
import com.video.user.domain.dto.user.UserAuthDTO;
import lombok.val;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedisUserAuthCache extends BaseRedisCache implements UserAuthCache {

    private static final String STRING_USER_AUTH_DTO_BY_OPENID_KEY_PREFIX = "spredis:userauthdto:id:%s";
    private static final String STRING_USER_AUTH_DTO_BY_ACCSEE_TOKEN_KEY_PREFIX = "spredis:userauthdto:accesstoken:%s";
    private static final String STRING_USER_AUTH_DTO_BY_TOKEN_AND_APPID_KEY_PREFIX = "spredis:userauthdto:token:%s:aid:%s";
    private static final String STRING_USERID_TK_COUNT_BY_PKG_AND_USERID_AND_TK = "spredis:userauthdto:pkg:%s:userid:%s:tk:%s";
    private static final String STRING_LATEST_TK_BY_PKG_AND_USERID = "spredis:userauthdto:pkg:%s:userid:%s";

    private String buildUserAuthDTOByOpenIdKey(String openId) {
        return String.format(STRING_USER_AUTH_DTO_BY_OPENID_KEY_PREFIX, openId);
    }

    private String buildUserAuthDTOByAccessTokenKey(String accessToken) {
        return String.format(STRING_USER_AUTH_DTO_BY_ACCSEE_TOKEN_KEY_PREFIX, accessToken);
    }

    private String buildUserAuthDTOByTokenAndAppIdKey(String token, Long appId) {
        return String.format(STRING_USER_AUTH_DTO_BY_TOKEN_AND_APPID_KEY_PREFIX, token, appId);
    }

    private String buildUserAuthDTOByUserIdAndPkgAndTkKey(String pkg, Long userId, String token) {
        return String.format(STRING_USERID_TK_COUNT_BY_PKG_AND_USERID_AND_TK, pkg, userId, token);
    }

    private String buildLatestTokenByUserIdAndPkgKey(String pkg, Long userId) {
        return String.format(STRING_LATEST_TK_BY_PKG_AND_USERID, pkg, userId);
    }

    @Override
    public UserAuthDTO getUserAuthDtoByOpenId(String openId) {
        String cacheKey = buildUserAuthDTOByOpenIdKey(openId);
        return get(cacheKey, UserAuthDTO.class);
    }

    @Override
    public void setUserAuthDtoByOpenId(String openId, UserAuthDTO userAuthDTO) {
        String cacheKey = buildUserAuthDTOByOpenIdKey(openId);
        set(cacheKey, userAuthDTO);
    }

    @Override
    public void setUserAuthDtoByOpenIdExpire(String openId, UserAuthDTO userAuthDTO) {
        String cacheKey = buildUserAuthDTOByOpenIdKey(openId);
        setAndExpire(cacheKey, userAuthDTO);
    }

    @Override
    public void delUserAuthDtoByOpenId(String openId) {
        String cacheKey = buildUserAuthDTOByOpenIdKey(openId);
        del(cacheKey);
    }

    @Override
    public UserAuthDTO getUserAuthDtoByAccessToken(String accessToken) {
        String cacheKey = buildUserAuthDTOByAccessTokenKey(accessToken);
        return get(cacheKey, UserAuthDTO.class);
    }

    @Override
    public void setUserAuthDtoByAccessTokenExpire(String accessToken, UserAuthDTO userAuthDTO) {
        String cacheKey = buildUserAuthDTOByAccessTokenKey(accessToken);
        setAndExpire(cacheKey, userAuthDTO);
    }

    @Override
    public void setUserAuthDtoByAccessToken(String accessToken, UserAuthDTO userAuthDTO) {
        String cacheKey = buildUserAuthDTOByAccessTokenKey(accessToken);
        set(cacheKey, userAuthDTO);
    }

    @Override
    public void delUserAuthDtoByAccessToken(String accessToken) {
        String cacheKey = buildUserAuthDTOByAccessTokenKey(accessToken);
        del(cacheKey);
    }

    @Override
    public UserAuthDTO getUserAuthDtoByTokenAndAppId(String token, Long appId) {
        String cacheKey = buildUserAuthDTOByTokenAndAppIdKey(token, appId);
        return get(cacheKey, UserAuthDTO.class);
    }

    @Override
    public void setUserAuthDtoByTokenAndAppIdExpire(String token, Long appId, UserAuthDTO userAuthDTO) {
        String cacheKey = buildUserAuthDTOByTokenAndAppIdKey(token, appId);
        setAndExpire(cacheKey, userAuthDTO);
    }

    @Override
    public void delUserAuthDtoByTokenAndAppId(String token, Long appId) {
        String cacheKey = buildUserAuthDTOByTokenAndAppIdKey(token, appId);
        del(cacheKey);
    }

    @Override
    public Integer getUserIdTokenMapping(String pkg, Long userId, String token) {
        String cacheKey = buildUserAuthDTOByUserIdAndPkgAndTkKey(pkg, userId, token);
        String count = get(cacheKey);
        return Strings.isBlank(count) ? null : Integer.parseInt(count);
    }

    @Override
    public void setUserIdTokenMapping(String pkg, Long userId, String token, Integer count) {
        String cacheKey = buildUserAuthDTOByUserIdAndPkgAndTkKey(pkg, userId, token);
        setAndExpire(cacheKey, count);
    }

    @Override
    public void delUserIdTokenMapping(String pkg, Long userId, String token) {
        String cacheKey = buildUserAuthDTOByUserIdAndPkgAndTkKey(pkg, userId, token);
        del(cacheKey);
    }

    @Override
    public String getLatestToken(String pkg, Long userId) {
        String cacheKey = buildLatestTokenByUserIdAndPkgKey(pkg, userId);
        return get(cacheKey);
    }

    @Override
    public void setLatestToken(String pkg, Long userId, String token) {
        String cacheKey = buildLatestTokenByUserIdAndPkgKey(pkg, userId);
        setAndExpire(cacheKey, token);
    }

    @Override
    public void delLatestToken(String pkg, Long userId) {
        String cacheKey = buildLatestTokenByUserIdAndPkgKey(pkg, userId);
        del(cacheKey);
    }
}
