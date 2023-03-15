create table `score` (
  `id` bigint not null,
  `app_id` bigint not null,
  `user_id` bigint not null,
  `task_code` varchar(31) not null,
  `score` bigint not null,
  `score_source` int not null,
  `task_log_id` bigint, -- task_log.id
  `create_time` datetime not null,
  `invalid_time` datetime not null,
  primary key (`id`),
  key `USER_ID_TASK_CODE_CREATE_TIME_INDEX` (`user_id`, `task_code`, `create_time`),
  -- 可能需要以taskLogId为条件进行sum(score)查询
  unique `TASK_LOG_ID_UNIQUE_INDEX` (`task_log_id`)
) ENGINE=InnoDB CHARSET=utf8;