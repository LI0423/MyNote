package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("google_user_auth")
public class GoogleUserAuthDO implements Serializable {

    private static final long serialVersionUID = 4751767178329203473L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long appId;

    /**
     * 自己系统生成的userId
     */
    private Long userId;

    /**
     * 谷歌用户id
     */
    private String googleUserId;

    /**
     * 自己生成的accessToken
     */
    private String accessToken;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}
