package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("clock_in_task_log")
public class ClockInTaskLogDO {

    @TableId
    private Long id;

    private String taskCode;

    /**
     * 任务类型
     */
    private TaskTypeEnum taskType;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 金币数量
     */
    private Long number;

    /**
     * 金豆数量
     */
    private Long goldBean;

    /**
     * 现金数量
     */
    private Long amountExchRatio;

    /**
     * 奖励行为
     */
    private Integer action;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建时间
     */
    private Date lastModifyTime;
}
