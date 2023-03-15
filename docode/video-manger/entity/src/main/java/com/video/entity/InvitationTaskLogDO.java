package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: 任务日志
 * @author: wangwei
 * @create: 2020-08-26 17:59
 **/
@Data
@TableName("invitation_task_log")
public class InvitationTaskLogDO implements Serializable {

    private static final long serialVersionUID = -6110003192698675857L;

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private Long appId;

    /**
     * 任务编码
     */
    private String taskCode;

    private Date createTime;

    /**
     * 金币数量
     */
    private Long number;
    /**
     * 可提现的金额
     */
    private Long amount;

    /**
     * 任务类型
     */
    private TaskTypeEnum taskType;

    /**
     * 邀请好友任务奖励列表
     */
    private String invitRewards;

    /**
     * 邀请好友任务已经给予奖励的次数
     */
    private Integer invitRewardGivedCount;

    /**
     * 邀请好友任务是否已经完成了
     */
    private Boolean invitTaskCompleted;

    /**
     * 开始任务时的金币与现金的兑换率
     */
    private Long amountExchRatio;

    /**
     * 被邀请人的id
     */
    private Long invitedUserId;

    /**
     * 日志最近一次修正时间
     */
    private Date modifyTime;

    /**
     * 完成任务时的app version
     */
    private String appVersion;

    /**
     * 客户端请求时所带的tk
     */
    private String token;

    /**
     * 任务是否被中断
     */
    private Integer interruptStatus;

    /**
     * 是否已经给过今天的额外奖励
     */
    private Boolean alreadyGiveTodayExtraReward;
}
