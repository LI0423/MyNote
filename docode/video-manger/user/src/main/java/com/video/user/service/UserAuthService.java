package com.video.user.service;

import com.video.entity.UserAuthDO;
import com.video.user.domain.dto.user.UserAuthDTO;

public interface UserAuthService {

    UserAuthDTO findByAccessToken(String accessToken);

    UserAuthDTO findByOpenId(String openId);

    UserAuthDTO findByTokenAndAppId(String token, Long appId);

    /**
     * 创建新user auth对象
     * @param userId
     * @param token
     * @param appId
     */
    void create(Long userId, String token, Long appId);

    /**
     * 创建新user auth对象
     * @param userId 用户id
     * @param openId 第三方账号唯一id
     * @param appId 应用id
     * @param thirdPartyAccessToken 第三方access token
     */
    void createByOpenId(Long userId, String openId, Long appId, String thirdPartyAccessToken);

    /**
     * 退出登录
     * @param accessToken
     */
    void logout(String accessToken);

    /**
     * 绑定第三方账号
     * @param userId
     * @param appId
     * @param token
     * @param openId
     * @param thirdPartyAccessToken
     */
    void bind(Long userId,
              Long appId,
              String token,
              String openId,
              String thirdPartyAccessToken);

    /**
     *更新accessToken
     * @param openId
     * @return
     */
    String updateAccessToken(String openId);

    /**
     * 记录userId token 映射关系
     * @param pkg
     * @param userId
     * @param token
     * @return
     */
    void saveUserIdTokenMapping(String pkg, Long userId, String token);

    /**
     * 获取userId token 映射中最新token
     * @param pkg
     * @param userId
     * @return
     */
    String getLatestToken(String pkg, Long userId);

    /**
     * 创建
     * @param userId
     * @param token
     * @param appId
     * @return
     */
    UserAuthDO createAuth(Long userId, String token, Long appId);

    /**
     * 更新用户id
     * @param userId
     * @return
     */
    UserAuthDTO updateAccessTokenByUserId(Long userId);

}
