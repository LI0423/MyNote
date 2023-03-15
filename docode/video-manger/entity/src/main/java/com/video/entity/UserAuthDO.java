package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: video-manger
 * @description: 用户授权
 * @author: laojiang
 * @create: 2020-08-26 15:08
 **/
@Data
@TableName("user_auth")
public class UserAuthDO implements Serializable {

    private static final long serialVersionUID = 84173015414787980L;

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    private Long appId;
    private Long userId;
    private String openId;
    private String oauthName;
    private String thirdPartyAccessToken;
    private String accessToken;
    private String token;
    private Long createTime;
    private Long lastModifyTime;

}
