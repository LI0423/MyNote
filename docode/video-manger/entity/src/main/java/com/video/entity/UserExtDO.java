package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_ext")
public class UserExtDO {

    @TableId
    private Long userId;

    /**
     * 冗余数据 appid
     */
    private Long appId;

    /**
     * 用户等级
     */
    private Integer level;

    /**
     * 是否新用户
     */
    private Boolean isNew;

    /**
     * 看广告数量
     */
    private Long viewAdCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近更新时间
     */
    private Date lastModifyTime;

    /**
     * 支付宝电话
     */
    private String aliPayAccount;
    /**
     * 支付宝名称
     */
    private String aliPayName;

}
