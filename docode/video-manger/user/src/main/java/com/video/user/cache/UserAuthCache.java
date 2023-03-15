package com.video.user.cache;

import com.video.user.domain.dto.user.UserAuthDTO;

public interface UserAuthCache {

    /**
     * 获取用户认证信息
     * @param openId
     * @return
     */
    UserAuthDTO getUserAuthDtoByOpenId(String openId);

    void setUserAuthDtoByOpenId(String openId, UserAuthDTO userAuthDTO);

    void setUserAuthDtoByOpenIdExpire(String openId, UserAuthDTO userAuthDTO);

    void delUserAuthDtoByOpenId(String openId);

    UserAuthDTO getUserAuthDtoByAccessToken(String accessToken);

    void setUserAuthDtoByAccessTokenExpire(String accessToken, UserAuthDTO userAuthDTO);

    void setUserAuthDtoByAccessToken(String accessToken, UserAuthDTO userAuthDTO);

    void delUserAuthDtoByAccessToken(String accessToken);

    UserAuthDTO getUserAuthDtoByTokenAndAppId(String token, Long appId);

    void setUserAuthDtoByTokenAndAppIdExpire(String token, Long appId, UserAuthDTO userAuthDTO);

    void delUserAuthDtoByTokenAndAppId(String token, Long appId);

    Integer getUserIdTokenMapping(String pkg, Long userId, String token);

    void setUserIdTokenMapping(String pkg, Long userId, String token, Integer count);

    void delUserIdTokenMapping(String pkg, Long userId, String token);

    String getLatestToken(String pkg, Long userId);

    void setLatestToken(String pkg, Long userId, String token);

    void delLatestToken(String pkg, Long userId);
}
