create table `task` (
  `id` bigint not null,
  `app_id` bigint not null,
  `code` varchar(31) not null,
  `name` varchar(127) ,
  `remark` varchar(255),
  `max_gold_coin` bigint,
  `min_gold_coin` bigint,
  `button_text_before` varchar(127),
  `button_text_after` varchar(127),
  `sort` int,
  `task_group` int ,
  `task_type` int ,
  `create_time` datetime default now(),
  `create_by` varchar(63) ,
  `last_modify_time`datetime default now() ,
  `last_modify_by` varchar(63) ,
  primary key (`id`),
  -- 每个任务奖励接口都需要通过code查询对应task实例的信息
  key `CODE_INDEX` (`code`),
  key `APP_ID_TASK_GROUP_SORT_INDEX` (`app_id`, `task_group`, `sort`),
  key `APPID_CODE_INDEX` (`app_id`, `code`)
) ENGINE=InnoDB CHARSET=utf8;