package com.video.user.service;

import com.video.entity.BaseUserDO;
import com.video.entity.UserAuthDO;
import com.video.entity.UserSexEnum;
import com.video.user.domain.dto.app.AppDTO;
import com.video.user.domain.dto.user.BaseUserDTO;
import com.video.user.domain.dto.user.UserAuthDTO;

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
     * 将第三方用户信息转换成匿名用户信息
     * @param baseUserDTO
     * @return
     */
    BaseUserDTO convertToAnonymous(BaseUserDTO baseUserDTO);

    /**
     * 保证同一个token+appid只会产生一个用户
     * @param token
     * @param appId
     * @param appVersion
     */
    void generate(String token, Long appId, String appVersion);

    /**
     * 直接通过第三方用户数据创建用户, 不与token绑定
     * @param appId 应用id
     * @param appVersion 应用版本
     * @param name 用户昵称
     * @param sex 性别
     * @param avatar 头像url
     * @param openId 第三方账号唯一标识
     * @param thirdPartyAccessToken 第三方access token
     */
    void generate(Long appId, String appVersion, String name, UserSexEnum sex,
                  String avatar, String openId, String thirdPartyAccessToken);

    /**
     * 游客绑定微信账户
     * @param userId
     * @param token
     * @param name
     * @param sex
     * @param avatar
     * @param openId
     * @param thirdPartyAccessToken
     * @param appId
     */
    void bind(Long userId,
              String token,
              String name,
              UserSexEnum sex,
              String avatar,
              String openId,
              String thirdPartyAccessToken,
              Long appId);

    /**
     * 更新用户信息
     * @param name
     * @param sex
     * @param avatar
     * @return
     */
    boolean update(Long userId, String name, UserSexEnum sex, String avatar);

    /**
     * 手机号在某个app上是否已经被绑定
     * @param appId app id
     * @param phoneNumber 电话号码
     * @return 是否已经被绑定
     */
    boolean phoneNumberAlreadyBind(Long appId, String phoneNumber);

    /**
     * 绑定手机号
     * @param userId 用户id
     * @param phoneNumber 电话号码
     */
    void bindPhone(Long userId, String phoneNumber);

    /**
     * 注册账户
     * @param appDTO
     * @param tk
     * @param phone
     * @return
     */
    UserAuthDTO loginPhone(AppDTO appDTO, String tk, String phone);

    /**
     * 手机号获取用户
     * @param appId
     * @param phoneNumber
     * @return
     */
    BaseUserDO userByPhoneNumber(Long appId, String phoneNumber);
}
