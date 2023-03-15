package com.video.oversea.user.cache.impl;

import com.video.oversea.user.cache.GoogleUserAuthCache;
import com.video.oversea.user.domain.dto.user.GoogleUserAuthDTO;
import com.video.oversea.user.service.third.google.GoogleUserAuthService;
import org.springframework.stereotype.Service;

@Service
public class GoogleRedisUserAuthCache extends BaseRedisCache implements GoogleUserAuthCache {

    private static final String STRING_USER_AUTH_DTO_BY_GOOGLE_USER_ID_KEY_PREFIX = "spredis:userauthdto:id:%s";
    private static final String STRING_USER_AUTH_DTO_BY_ACCSEE_TOKEN_KEY_PREFIX = "spredis:userauthdto:accesstoken:%s";
    private static final String STRING_USER_AUTH_DTO_BY_TOKEN_AND_APPID_KEY_PREFIX = "spredis:userauthdto:token:%s:aid:%s";
    private static final String STRING_USERID_TK_COUNT_BY_PKG_AND_USERID_AND_TK = "spredis:userauthdto:pkg:%s:userid:%s:tk:%s";
    private static final String STRING_LATEST_TK_BY_PKG_AND_USERID = "spredis:userauthdto:pkg:%s:userid:%s";

    private String buildUserAuthDTOByGoogleUserIdKey(String googleUserId) {
        return String.format(STRING_USER_AUTH_DTO_BY_GOOGLE_USER_ID_KEY_PREFIX, googleUserId);
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
    public GoogleUserAuthDTO getGoogleUserAuthDtoByGoogleUserId(String googleUserId) {
        String cacheKey = buildUserAuthDTOByGoogleUserIdKey(googleUserId);
        return get(cacheKey, GoogleUserAuthDTO.class);
    }

    @Override
    public void setGoogleUserAuthDtoByGoogleUserId(String googleUserId, GoogleUserAuthDTO googleUserAuthDTO) {
        String cacheKey = buildUserAuthDTOByGoogleUserIdKey(googleUserId);
        set(cacheKey, googleUserAuthDTO);
    }

    @Override
    public void delGoogleUserAuthDtoByGoogleUserId(String googleUserId) {
        String cacheKey = buildUserAuthDTOByGoogleUserIdKey(googleUserId);
        del(cacheKey);
    }

    @Override
    public GoogleUserAuthDTO getGoogleUserAuthDtoByAccessToken(String accessToken) {
        String cacheKey = buildUserAuthDTOByAccessTokenKey(accessToken);
        return get(cacheKey, GoogleUserAuthDTO.class);
    }

    @Override
    public void setGoogleUserAuthDtoByAccessToken(String accessToken, GoogleUserAuthDTO googleUserAuthDTO) {
        String cacheKey = buildUserAuthDTOByAccessTokenKey(accessToken);
        set(cacheKey, googleUserAuthDTO);
    }

    @Override
    public void delGoogleUserAuthDtoByAccessToken(String accessToken) {
        String cacheKey = buildUserAuthDTOByAccessTokenKey(accessToken);
        del(cacheKey);
    }
}
