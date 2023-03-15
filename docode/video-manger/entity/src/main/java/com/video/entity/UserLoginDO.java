package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_login")
public class UserLoginDO {

    @TableId
    private Long userId;

    /**
     * 冗余数据 appid
     */
    private Long appId;

    /**
     * 累计登录天数
     */
    private Integer accumulatedLoginDays;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近更新时间
     */
    private Date lastModifyTime;

    /**
     * 上一次打卡的时间
     */
    private Date lastLoginTime;
}
