package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: mango
 * @description: 应用广告位
 * @author: laojiang
 * @create: 2020-10-20 19:21
 **/
@Data
@TableName("app_ad_config_sid")
public class AppAdConfigSidDO implements Serializable {

    private static final long serialVersionUID = -8890366633911532396L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 广告位
     */
    private String sid;

    private Long appAdConfigId;
}
