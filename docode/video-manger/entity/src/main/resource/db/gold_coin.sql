create table `gold_coin` (
  `id` bigint not null,
  `user_id` bigint not null,
  `app_id` bigint not null,
  `task_code` varchar(31),
  `number` bigint not null,
  `gold_coin_source` int not null,
  -- task_log.id 积分兑换时这个字段可以没有值
  `task_log_id` bigint,
  `create_time` datetime,
  primary key (`id`),
  key `USER_ID_INDEX` (`user_id`),
   -- 可能需要以taskLogId为条件进行sum(number)查询
  key `TASK_LOG_ID_UNIQUE_INDEX` (`task_log_id`),
  -- 提现时要查询一段时间的总number
  key `USER_ID_CREATE_TIME_INDEX` (`user_id`, `create_time`)
) ENGINE=InnoDB CHARSET=utf8;