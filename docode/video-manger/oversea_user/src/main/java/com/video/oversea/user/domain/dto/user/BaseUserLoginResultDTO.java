package com.video.oversea.user.domain.dto.user;

import com.video.entity.UserSexEnum;
import lombok.Data;

/**
 * 用户登录返回DTO对象
 * @author wangwei
 */
@Data
public class BaseUserLoginResultDTO {

    /**
     * 我们的登录成功标识
     */
    private String accessToken;

    private String name;
    private UserSexEnum sex;
    private String avatar;
}
