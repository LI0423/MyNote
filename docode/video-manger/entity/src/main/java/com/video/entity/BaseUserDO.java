package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.video.entity.UserSexEnum;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class BaseUserDO {

    @TableId(type= IdType.ASSIGN_ID)
    protected Long id;

    /**
     * app id
     */
    protected Long appId;

    /**
     * 昵称
     */
    protected String name;

    /**
     * 性别
     */
    protected UserSexEnum sex;

    /**
     * 头像url
     */
    protected String avatar;

    /**
     * 绑定的手机号码
     */
    protected String phoneNumber;

    /**
     * 创建时间
     */
    protected Date createTime;
}
