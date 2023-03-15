package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: 用户表
 * @author: laojiang
 * @create: 2020-08-26 14:54
 **/
@Data
@TableName("user")
public class UserDO extends BaseUserDO implements Serializable {

    private static final long serialVersionUID = 7671585319471674530L;

    /**
     * 总的金币数
     */
    private Long number;

    /**
     * 总金豆数
     */
    private Long goldBean;

    /**
     * 总的积分数
     */
    private Long score;

    /**
     * 提现次数
     */
    private Long withdrawalTimes;

    private Long maxWithdrawalContinuousSignInDays;

    private Long currentWithdrawalContinuousSignInDays;

    /**
     * 是不是被邀请来的
     */
    private Boolean invited;

    /**
     * 邀请码
     */
    private String invitationCode;

    /**
     * 邀请人id
     */
    private Long invitationUserId;

    /**
     * 被邀请者没断签前的最大连续签到天数
     */
    private Long maxInvitedNotBrokenContinuousSignInDays;

    /**
     * 被邀请者是否断签过
     */
    private Boolean invitedHasSignInBroken;

    /**
     * 用户当前连续签到数
     */
    private Integer currentMedalContinuousSignInDays;

    /**
     * ibu 标签 0非ibu用户， 1 ibu用户
     */
    private Integer ibuTag;
    /**
     * 第二天可领取金币， 可累计
     */
    private Long tomorrowNumber;
}
