/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author zhangchao 2022-01-14
 */
@Data
@TableName("user_ad_diverse_config")
public class UserAdDiverseConfigDO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String app;

    private String aid;

    private Long intervalTime;

    private String source;

    /**
     * 添加时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 添加人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 最近修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastModifyTime;
    /**
     * 最近修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastModifyBy;
}
