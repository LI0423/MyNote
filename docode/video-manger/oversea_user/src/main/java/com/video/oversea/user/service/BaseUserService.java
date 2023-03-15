package com.video.oversea.user.service;

import com.video.entity.UserSexEnum;
import com.video.oversea.user.domain.dto.user.BaseUserDTO;

/**
 * @author admin
 */
public interface BaseUserService {

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    BaseUserDTO find(Long userId);

    /**
     * 通过第三方的信息创建用户
     * @param appId app
     * @param appVersion
     * @param googleUserId
     * @param name
     * @param picture
     */
    void googleGenerate(Long appId, String appVersion, String googleUserId, String name, String picture);
}
