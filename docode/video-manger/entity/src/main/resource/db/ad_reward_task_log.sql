CREATE TABLE `ad_reward_task_log` (
    `id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    `app_id` bigint(20) NOT NULL,
    `task_code` varchar(31) NOT NULL,
    `task_type` int(11) DEFAULT NULL COMMENT '任务类型',
    `reward_type` int(11) DEFAULT NULL COMMENT '奖励类型',
    `imp_number` bigint(20) NOT NULL COMMENT '展示奖励',
    `click_number` bigint(20) NOT NULL COMMENT '点击奖励',
    `app_version` varchar(63) DEFAULT NULL COMMENT '完成任务时的版本',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `token` varchar(63) DEFAULT NULL COMMENT '请求时携带的token',
    PRIMARY KEY (`id`),
    KEY `USER_ID_CREATE_TIME_INDEX` (`user_id`, `create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '广告奖励任务日志';

ALTER TABLE `ad_reward_task_log` ADD COLUMN `install_number` bigint(20) NOT NULL DEFAULT 0 COMMENT '安装奖励';

ALTER TABLE `ad_reward_task_log` ADD COLUMN `ad_pid` varchar(127) NOT NULL DEFAULT 0 COMMENT '渠道广告位id';

ALTER TABLE `ad_reward_task_log` ADD COLUMN `ctoken` varchar(63) DEFAULT NULL COMMENT '用户标识';

ALTER TABLE `ad_reward_task_log` ADD COLUMN `channel` varchar(63) DEFAULT NULL COMMENT '买量渠道';

ALTER TABLE `ad_reward_task_log` ADD COLUMN `ecpm` varchar(63) DEFAULT NULL COMMENT '奖励底价';

ALTER TABLE `ad_reward_task_log` ADD COLUMN `tid` varchar(63) DEFAULT NULL COMMENT '业务id';

ALTER TABLE `ad_reward_task_log` ADD COLUMN `has_click` bit DEFAULT NULL COMMENT '额外奖励标志';

ALTER TABLE `video_task`.`ad_reward_task_log` ADD COLUMN `tomorrow_price` bigint(20) NULL DEFAULT 0 COMMENT '第二天可领取金额' ;

ALTER TABLE `ad_reward_task_log` ADD COLUMN `tomorrow_award` bigint(20) NULL DEFAULT 0 COMMENT '奖励第二天可领';