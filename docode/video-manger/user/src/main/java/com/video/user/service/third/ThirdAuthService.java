package com.video.user.service.third;

import com.video.user.domain.dto.third.AuthDTO;
import com.video.user.domain.dto.third.UserInfoDTO;

/**
 * @program: mango
 * @description: 第三方认证的接口
 * @author: laojiang
 * @create: 2020-09-11 11:51
 **/
public interface ThirdAuthService {


    /**
     * 第三方验证
     * @param appId
     * @param secret
     * @param code
     * @return
     */
    AuthDTO auth(String appId, String secret, String code);


    /**
     * 获取用户个人信息
     * @param accessToken 第三方accessToken
     * @param openId
     * @return
     */
    UserInfoDTO getUserInfo(String accessToken, String openId);
}
