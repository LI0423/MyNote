package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ad_reward_task_log")
public class AdRewardTaskLogDO {

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private Long appId;

    /**
     * 渠道广告位id
     */
    private String adPid;

    /**
     * 奖励类型
     */
    private AdRewardTypeEnum rewardType;

    /**
     * 任务编码
     */
    private String taskCode;

    /**
     * 广告展示金币奖励数量
     */
    private Long impNumber;

    /**
     * 产生点击金币奖励数量
     */
    private Long clickNumber;

    /**
     * 安装金币奖励数量
     */
    private Long installNumber;

    /**
     * 任务类型
     */
    private TaskTypeEnum taskType;

    /**
     * 完成任务时的app version
     */
    private String appVersion;

    /**
     * 客户端请求时所带的tk
     */
    private String token;

    /**
     * 用户标识
     */
    private String ctoken;

    /**
     * 买量渠道
     */
    private String channel;

    /**
     * 奖励底价
     */
    private Double ecpm;

    /**
     * 业务id
     */
    private String tid;

    /**
     * 额外奖励标志
     */
    private Boolean hasClick;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 切割出来的奖励部分/ ecpm按照百分比切割
     */
    private Long tomorrowPrice;
    /**
     * 首次领奖 符合条件 白给奖励
     */
    private Long tomorrowAward;
}
