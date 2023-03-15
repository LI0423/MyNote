package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 手机厂商型号黑名单表
 */
@Data
@TableName("black_vendor_model")
public class BlackVendorModelDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 包名
     */
    private String pkg;

    /**
     * 手机厂商
     */
    private String vendor;

    /**
     * 手机型号
     */
    private String model;

    /**
     * 拉黑原因
     */
    private String desc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次修改时间
     */
    private Date lastModifyTime;
}
