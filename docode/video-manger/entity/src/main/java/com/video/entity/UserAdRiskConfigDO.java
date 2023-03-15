package com.video.entity;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;

import java.util.Date;

/**
 * @author laojiang
 */
@Data
@TableName("user_ad_risk_config")
public class UserAdRiskConfigDO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String app;

    /**
     * 类型
     */
    private UserAdRiskTypeEnum type;

    /**
     * 型号
     */
    private String model;

    private String token;

    private String source;

    /**
     * h5的展示次数最小值
     */
    private Integer minValue;
    /**
     * h5展示次数最大值
     */
    private Integer maxValue;
    /**
     * h5在总广告展示中的占比
     */
    private Double ratio;

    /**
     * 最小点击次数
     */
    private Integer minClick;

    /**
     * 警告ctr
     */
    private Double warnCtr;
    /**
     * 最大ctr
     */
    private Double maxCtr;

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
