create table `task_log` (
  `id` bigint not null,
  `user_id` bigint not null,
  `app_id` bigint not null,
  `task_code` varchar(31) not null,
  `create_time` datetime default now(),
  `round` int,
  `begin_time` bigint,
  `duration` bigint,
  `object_id` varchar(1022),
  `number` bigint,
  `amount` bigint,
  `score` bigint,
  `task_type` int,
  primary key(`id`),
  -- "日常任务奖励"接口，需要检查此任务是否已经做过了，判断方式是通过userId和taskCode查询
  key `USER_ID_TASK_CODE_INDEX` (`user_id`, `task_code`),
  key `USER_ID_TASK_CODE_ROUND_DURATION` (`user_id`, `task_code`, `round`, `duration`),
  key `USER_ID_TASK_CODE_CREATE_TIME` (`user_id`, `task_code`, `create_time`)
) ENGINE=InnoDB CHARSET=utf8;