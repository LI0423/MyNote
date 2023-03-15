CREATE TABLE `clock_in_task_log` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `task_code` varchar(31) NOT NULL,
  `number` bigint(20) DEFAULT NULL,
  `gold_bean` bigint(20) DEFAULT NULL,
  `task_type` int(11) DEFAULT NULL,
  `app_id` bigint(20) NOT NULL,
  `amount_exch_ratio` bigint(20) DEFAULT NULL COMMENT '开始任务时的金币与现金的兑换率',
  `action` int DEFAULT NULL COMMENT '奖励行为',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `USER_ID_TASK_CODE_INDEX` (`user_id`, `task_code`, `action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;