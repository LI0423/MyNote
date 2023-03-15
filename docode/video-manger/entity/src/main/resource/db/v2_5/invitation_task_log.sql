CREATE TABLE `invitation_task_log` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `task_code` varchar(31) NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `number` bigint(20) DEFAULT NULL,
  `amount` bigint(20) DEFAULT NULL,
  `task_type` int(11) DEFAULT NULL,
  `app_id` bigint(20) NOT NULL,
  `invit_rewards` varchar(255) DEFAULT NULL COMMENT '开始任务时的奖励配置列表',
  `amount_exch_ratio` bigint(20) DEFAULT NULL COMMENT '开始任务时的金币与现金的兑换率',
  `invited_user_id` bigint(20) DEFAULT NULL COMMENT '被邀请人的id',
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `app_version` varchar(63) DEFAULT NULL COMMENT '完成任务时的版本',
  `invit_reward_gived_count` int(63) DEFAULT '0' COMMENT '邀请好友任务已经给予奖励的次数',
  `invit_task_completed` bit(1) DEFAULT b'0' COMMENT '邀请好友任务奖励是否全都发放完成了',
  `interrupt_status` int DEFAULT 2 COMMENT '邀请好友任务奖励中断状态, 2:中间状态, 1:中断了, 0:可以发放最后奖励',
  `already_give_today_extra_reward` bit(1) DEFAULT b'0' COMMENT '是否已经给过今天的额外奖励',
  `token` varchar(63) DEFAULT NULL COMMENT '请求时携带的token',
  PRIMARY KEY (`id`),
  KEY `USER_ID_TASK_CODE_INDEX` (`user_id`,`task_code`),
  KEY `USER_ID_TASK_CODE_CREATE_TIME` (`user_id`,`task_code`,`create_time`),
  KEY `USER_ID_TASK_CODE_CREATE_TIME_NUMBER` (`user_id`,`task_code`,`create_time`,`number`),
  KEY `USER_ID_INVIT_UID_CREATE_TIME_INDEX` (`user_id`,`invited_user_id`,`create_time`),
  KEY `USER_ID_INVIT_UID_TCODE_MTIME_INVTRWGVCNT_INDEX` (`user_id`,`invited_user_id`,`task_code`,`modify_time`,`invit_reward_gived_count`),
  KEY `USER_ID_INVTTKCOMP_CREATE_TIME_NUMBER` (`user_id`,`invit_task_completed`,`create_time`,`number`),
  KEY `USER_ID_CREATE_TIME_INDEx` (`user_id`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8