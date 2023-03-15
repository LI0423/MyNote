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
 * @author: laojiang
 * @create: 2020-08-26 17:59
 **/
@Data
@TableName("task_log")
public class TaskLogDO implements Serializable {

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
     * 轮数
     */
    private Integer round;
    /**
     * 开始时间
     */
    private Long beginTime;
    /**
     * 时长，单位秒
     */
    private Long duration;

    /**
     * 视频编号
     */
    private String objectId;

    /**
     * 金币数量
     */
    private Long number;
    /**
     * 可提现的金额
     */
    private Long amount;

    /**
     * 积分
     */
    private Long score;

    /**
     * 任务类型
     */
    private TaskTypeEnum taskType;

    /**
     * 勋章任务子任务标识
     */
    private Integer activeDays;

    /**
     * 大转盘倍数
     */
    private Double spinMult;

    /**
     * 关联的task log id
     */
    private Long linkId;

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
     * 是否已经翻倍
     */
    private Boolean doubled;

    /**
     * 完成任务时的app version
     */
    private String appVersion;

    /**
     * 勋章任务类型
     */
    private MedalTypeEnum medalType;

    /**
     * 勋章任务奖励条件值
     */
    private Integer medalConditionValue;

    /**
     * 客户端请求时所带的tk
     */
    private String token;

    /**
     * 金豆数量
     */
    private Long goldBeanNumber;
    /**
     * 金豆可提现的金额
     */
    private Long goldBeanAmount;
}
