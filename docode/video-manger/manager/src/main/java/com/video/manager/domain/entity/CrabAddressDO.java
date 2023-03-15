package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.video.manager.domain.common.CrabAddressStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: api
 * @description: 抓取地址
 * @author: laojiang
 * @create: 2020-08-22 14:19
 **/
@Data
@TableName("crab_address")
public class CrabAddressDO implements Serializable {

    private static final long serialVersionUID = -5435752898409255484L;

    @TableId(type= IdType.AUTO)
    private Long id;

    /**
     * 抓取的渠道
     */
    private String source;
    /**
     * 抓取的地址
     */
    private String url;

    /**
     * 状态
     */
    private CrabAddressStatusEnum status;
    /**
     * 失败原因
     */
    private String reason;

    /**
     * 尝试次数
     */
    private Integer attemptNum;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastModifyTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastModifyBy;

}
