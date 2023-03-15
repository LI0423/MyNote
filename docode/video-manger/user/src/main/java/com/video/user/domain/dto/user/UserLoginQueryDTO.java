package com.video.user.domain.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户登录请求DTO对象
 * @author wangwei
 */
@Data
public class UserLoginQueryDTO {
    /**
     * 用户第三方登录后返回的唯一标识
     */
    @NotNull(message = "code 参数不能为空")
    private String code;

    /**
     * 用户登录类型, WeChat/QQ
     */
    @NotNull(message = "type 参数不能为空")
    private  String type;
}
