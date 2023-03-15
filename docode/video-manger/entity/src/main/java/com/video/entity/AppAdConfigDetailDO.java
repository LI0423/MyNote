package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: mango
 * @description: 配置明细
 * @author: laojiang
 * @create: 2020-10-20 19:32
 **/
@Data
@TableName("app_ad_config_detail")
public class AppAdConfigDetailDO implements Serializable {

    private static final long serialVersionUID = -7449036063186009542L;

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    /**
     * 配置的建
     */
    private String dataKey;

    /**
     * 配置的值
     */
    private String value;

    /**
     * 属于那个配置
     */
    private Long appAdConfigId;
}
