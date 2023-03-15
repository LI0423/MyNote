package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.video.manager.domain.common.CrabDataOriginStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: api
 * @description: 抓取的原始数据
 * @author: laojiang
 * @create: 2020-08-22 14:41
 **/
@Data
@TableName("crab_data_origin")
public class CrabDataOriginDO implements Serializable {

    private static final long serialVersionUID = 1920414106364527338L;

    @TableId(type= IdType.AUTO)
    private Long id;

    /**
     * 抓取的地址主键
     */
    private Long crabAddressId;

    /**
     * 来源
     */
    private String source;

    /**
     * 抓取的数据
     */
    private String data;

    /**
     * 状态
     */
    private CrabDataOriginStatusEnum status;

    private String createBy;
    private Date createTime;

}
