package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: 金币
 * @author: laojiang
 * @create: 2020-08-26 17:46
 **/
@Data
@TableName("gold_coin")
public class /**/GoldCoinDO implements Serializable {

    private static final long serialVersionUID = -6245399773053071054L;

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    private Long appId;
    private Long userId;
    private String taskCode;
    private Long number;
    private Long goldBean;

    /**
     * 金币来源
     */
    private GoldCoinSourceEnum goldCoinSource;

    /**
     * 任务日志流水号
     */
    private Long taskLogId;

    private Date createTime;

    /**
     * 客户端请求时所带的tk
     */
    private String token;
}
