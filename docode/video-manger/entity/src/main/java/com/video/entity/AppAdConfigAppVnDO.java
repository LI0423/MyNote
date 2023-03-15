package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: mango
 * @description: 应用版本号
 * @author: laojiang
 * @create: 2020-10-20 19:20
 **/
@Data
@TableName("app_ad_config_app_vn")
public class AppAdConfigAppVnDO implements Serializable {

    private static final long serialVersionUID = -1544691000615300712L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 应用版本
     */
    private String vn;

    private Long appAdConfigId;
}
