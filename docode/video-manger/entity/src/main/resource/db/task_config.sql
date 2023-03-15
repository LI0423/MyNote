create table `task_config` (
  `id` bigint not null,
  `task_id` bigint not null,
  `c_key` varchar(127) not null,
  `value` varchar(127) not null,
  `create_time` datetime default now(),
  `create_by` varchar(63) ,
  `last_modify_time` datetime default now(),
  `last_modify_by` varchar(63) ,
  primary key (`id`),
  -- "视频观看"接口需要一次性查询taskCode对应的任务的所有配置信息，如：都有哪些时长配置
  key `TASK_ID_INDEX` (`task_id`),
  -- "视频观看奖励"接口需要通过taskCode和分钟数获取相应的金币奖励配置
  key `TASK_ID_KEY_INDEX` (`task_id`, `c_key`)
) ENGINE=InnoDB CHARSET=utf8;