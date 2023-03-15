/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author zhangchao
 */
@Data
@TableName("user_ad_limit_config")
public class UserAdLimitConfigDO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String app;

    /**
     * 类型
     */
    private UserAdRiskTypeEnum type;

    private UserAdLimitConfigTypeEnum configType;

    /**
     * 型号
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String model;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String token;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String source;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String aid;

    /**
     * h5的展示次数最小值
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer minValue;

    /**
     * h5展示次数最大值
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer maxValue;

    /**
     * h5在总广告展示中的占比
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Double ratio;

    /**
     * 最小点击次数
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer minClick;

    /**
     * 警告ctr
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Double warnCtr;
    /**
     * 最大ctr
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Double maxCtr;

    /**
     * 填充次数，h5占比达到后，没有apk，那aid当日不再请求，出现apk则重置计数。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer fillValue;

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
