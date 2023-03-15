package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: 积分
 * @author: laojiang
 * @create: 2020-08-26 17:49
 **/
@Data
@TableName("score")
public class ScoreDO implements Serializable {

    private static final long serialVersionUID = -8021121751545370907L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long appId;

    private Long userId;
    private String taskCode;
    /**
     * 积分数
     */
    private Long score;
    private Long taskLogId;

    /**
     * 积分来源
     */
    private ScoreSourceEnum scoreSource;

    /**
     * 积分失效时间
     */
    private Date invalidTime;

    private Date createTime;
}
