package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: 签到
 * @author: laojiang
 * @create: 2020-08-26 17:56
 **/
@Data
@TableName("sign_in")
public class SignInDO implements Serializable {

    private static final long serialVersionUID = 1030224877605166597L;

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;
    private Long appId;

    /**
     * 任务编码
     */
    private String taskCode;
    /**
     * 第几天
     */
    private Integer day;
    /**
     * 金币数量
     */
    private Long number;

    /**
     * 是否翻倍
     */
    private Boolean doubled;

    private Long userId;

    private Date createTime;

}
