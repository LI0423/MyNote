package com.video.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: mango
 * @description: 应用广告配置
 * @author: laojiang
 * @create: 2020-10-20 19:10
 **/
@Data
@TableName("app_ad_config")
public class AppAdConfigDO implements Serializable {

    private static final long serialVersionUID = 4776222315781015736L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 应用包名
     */
    private String pkg;

    /**
     * 应用编号
     */
    private Long appId;

    /**
     * 应用的名字
     */
    private String name;

    /**
     * 省的名字
     */
    private String provinceName;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 城市的名字
     */
    private String cityName;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 省和城市
     */
    private String provinceAndCityName;

    /**
     * 配置的json信息，从配置明细中生成
     */
    private String configJson;

    /**
     * 配置的状态
     */
    private AppAdConfigStatusEnum status;

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
