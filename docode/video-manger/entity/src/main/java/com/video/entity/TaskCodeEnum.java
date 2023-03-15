package com.video.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskCodeEnum {

    SIGNIN_TASK_CODE("S00001", "签到任务"),
    CLOCKIN_TASK_CODE("S00002", "打卡任务"),

    DURATION_TASK_CODE("V00001", "时长任务"),
    TIMING_ROUND_TASK_CODE("V00002", "时长圈圈任务"),
    SMASH_GOLD_EGG_TASK_CODE("V00003", "砸金蛋任务"),
    NEW_HAND_SHORT_VIDEO_TASK_CODE("N00001", "新手短视频任务"),
    NEW_HAND_SMALL_VIDEO_TASK_CODE("N00002", "新手小视频任务"),
    NEW_HAND_TURN_ON_LOCK_SCREEN_TASK_CODE("N00003", "开启锁屏奖励"),
    NEW_HAND_WITHDRAW_TASK_CODE("N00004", "提现任务"),

    /* 版本2 start*/
    NEW_HAND_SIGNIN_REMIND_TASK_CODE("N00005", "开启签到提醒"),
    /* 版本2 end*/

    /* 版本2.1 start*/
    NEW_HAND_MANGO_APP_DOWNLOAD_TASK_CODE("N00006", "芒果视频下载任务"),
    /* 版本2.1 end*/

    NEW_HAND_GAME_FIRST_CAN_SUB_TASK_CODE("N10001", "新手游戏任务(可配置子任务)1"),
    NEW_HAND_GAME_SECOND_CAN_SUB_TASK_CODE("N10002", "新手游戏任务(可配置子任务)2"),
    NEW_HAND_GAME_THIRD_CAN_SUB_TASK_CODE("N10003", "新手游戏任务(可配置子任务)3"),
    NEW_HAND_GAME_FOURTH_CAN_SUB_TASK_CODE("N10004", "新手游戏任务(可配置子任务)4"),
    NEW_HAND_GAME_FIFTH_CAN_SUB_TASK_CODE("N10005", "新手游戏任务(可配置子任务)5"),
    NEW_HAND_GAME_SIXTH_CAN_SUB_TASK_CODE("N10006", "新手游戏任务(可配置子任务)6"),
    NEW_HAND_GAME_SEVENTH_CAN_SUB_TASK_CODE("N10007", "新手游戏任务(可配置子任务)7"),


    LOG_IN_TASK_CODE("L00001", "新用户红包"),
    DAILY_CHARGE_TASK_CODE("D00001", "充电任务"),
    DAILY_SHORT_VIDEO_TASK_CODE("D00002", "日常短视频任务"),
    DAILY_SMALL_VIDEO_TASK_CODE("D00003", "日常小视频任务"),
    DAILY_READ_NOTIFICATON_BAR_TASK_CODE("D00004", "阅读通知栏任务"),

    /* 版本2 start*/
    DAILY_VIEW_VIDEO_AD_TASK_CODE("D00005", "激励视频任务"),
    DAILY_INVITATION_FRIENDS_TASK_CODE("D00006", "邀请好友任务"),
    DAILY_MEDAL_TASK_CODE("D00007", "勋章任务"),
    DAILY_SPIN_TASK_CODE("D00008", "大转盘任务"),
    /* 版本2 end*/

    /* 版本2.4 start*/
    DAILY_DOWNLOAD_TASK_CODE("D00009", "下载领金币任务"),
    /* 版本2.4 end*/

    /* 游戏日常任务 start */
    DAILY_GAME_FIRST_TASK_TASK_CODE("D10001", "日常游戏任务1"),

    DAILY_GAME_SECOND_TASK_TASK_CODE("D10002", "日常游戏任务2"),

    DAILY_GAME_THIRD_TASK_TASK_CODE("D10003", "日常游戏任务3"),

    DAILY_GAME_FOURTH_TASK_TASK_CODE("D10004", "日常游戏任务4"),

    DAILY_GAME_FIFTH_TASK_TASK_CODE("D10005", "日常游戏任务5"),

    DAILY_GAME_SIXTH_TASK_TASK_CODE("D10006", "日常游戏任务6"),

    DAILY_GAME_SEVENTH_TASK_TASK_CODE("D10007", "日常游戏任务7"),

    /* 广告奖励任务 */
    DAILY_AD_REWARD_TASK_CODE("D20001", "广告奖励任务"),

    /* 游戏日常任务 end */

    /* 版本2.3 start*/
    COMMON_BANNER_TASK_CODE("C00001", "banner展示"),
    /* 版本2.3 end*/

    /* 版本2.4 start*/
    COMMON_APP_UPDATE_CODE("C00002", "app更新"),
    /* 版本2.4 end*/

    /* 版本2.5 start*/
    HIDE_TASK_CODE("H00001","安装应用弹窗"),
    /* 版本2.5 end*/



    /* 视频日常任务 start */
    CIRCLE_FIRST_TASK_TASK_CODE("VC0001", "转圈红包1圈"),
    CIRCLE_THIRD_TASK_TASK_CODE("VC0002", "转圈红包3圈"),


    DAILY_VIDEO_RED_FIRST_TASK_TASK_CODE("DVR10001", "领取转圈红包10个"),
    DAILY_VIDEO_RED_SECOND_TASK_TASK_CODE("DVR10002", "领取转圈红包20个"),
    DAILY_VIDEO_RED_THIRD_TASK_TASK_CODE("DVR10003", "领取转圈红包30个"),
    DAILY_VIDEO_RED_FOURTH_TASK_TASK_CODE("DVR10004", "领取转圈红包50个"),
    DAILY_VIDEO_RED_FIFTH_TASK_TASK_CODE("DVR10005", "领取转圈红包100个"),

    DAILY_VIDEO_CLICK_FIRST_TASK_TASK_CODE("DVC10001", "转圈红包打开后点击'全都要'按钮1次"),
    DAILY_VIDEO_CLICK_SECOND_TASK_TASK_CODE("DVC10002", "转圈红包打开后点击'全都要'按钮3次"),
    DAILY_VIDEO_CLICK_THIRD_TASK_TASK_CODE("DVC10003", "转圈红包打开后点击'全都要'按钮5次"),
    DAILY_VIDEO_CLICK_FOURTH_TASK_TASK_CODE("DVC10004", "转圈红包打开后点击'全都要'按钮8次"),
    DAILY_VIDEO_CLICK_FIFTH_TASK_TASK_CODE("DVC10005", "转圈红包打开后点击'全都要'按钮10次"),

    DAILY_VIDEO_GOLD_FIRST_TASK_TASK_CODE("DVG10001", "看金豆视频1次"),
    DAILY_VIDEO_GOLD_SECOND_TASK_TASK_CODE("DVG10002", "看金豆视频3次"),
    DAILY_VIDEO_GOLD_THIRD_TASK_TASK_CODE("DVG10003", "看金豆视频5次"),
    DAILY_VIDEO_GOLD_FOURTH_TASK_TASK_CODE("DVG10004", "看金豆视频8次"),
    DAILY_VIDEO_GOLD_FIFTH_TASK_TASK_CODE("DVG10005", "看金豆视频10次"),

    GOLD_VIDEO_TASK_CODE("DVG0001", "观看金豆视频最多可领20次"),
    BOX_VIDEO_TASK_CODE("DBG0001", "观看宝箱视频最多可领20次"),
    RED_VIDEO_TASK_CODE("DVR0001", "在首页观看视频最多可领20次"),
    RAIN_RED_VIDEO_TASK_CODE("DVT0001", "红包雨"),
    QUESTION_VIDEO_TASK_CODE("DVQ0001","视频答题"),
//    TIMING_RED_VIDEO_TASK_CODE("DVT0002", "整点领红包"),


    /**
     * 邀请事件任务
     */
    INVITATION_INCENTIVE_VIDEO_CODE_1("iiv001", "邀请事件, 第一阶段"),
    INVITATION_INCENTIVE_VIDEO_CODE_2("iiv002", "邀请事件，第二阶段"),
    INVITATION_INCENTIVE_VIDEO_CODE_3("iiv003", "邀请事件，第三阶段"),


    ;

    private final String code;

    private final String description;
}
