package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("user_income_withdraw_day_summary")
@Data
public class UserIncomeWithdrawalDO {

    private Date dataDate;

    private Long appId;

    private String pkg;

    private Long userId;

    //是否ibu
    private Integer isIbu;

    //是否买量用户
    private Integer isBuy;

    //新增时间
    private Date newDate;

    private String name;

    //来源渠道
    private String channel;

    private String avatar;

    private String oaid;

    private String imei;

    private String anid;

    private String token;

    //提现金额，单位：分
    private Long withdrawAmount;

    private Long showPv;

    private Long click;

    //激励广告展示次数
    private Long rewardShowPv;

    //激励广告点击次数
    private Long rewardClick;

    //展示预估收入
    private Long showIncomeAmount;

    //点击预估收入，单位：分
    private Long clickIncomeAmount;

    //留存天数
    private Long remainDays;

    //提现金额，单位：分
    private Long totalWithdrawAmount;

    //展示预估收入，单位：分
    private Long totalShowIncomeAmount;

    //点击预估收入，单位：分
    private Long totalClickIncomeAmount;

    private Long totalShowPv;

    private Long totalClick;

    //总计激励广告展示次数
    private Long totalRewardShowPv;

    //总计激励广告点击次数
    private Long totalRewardClick;

}
