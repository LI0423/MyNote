create table `sign_in` (
  `id` bigint not null,
  `app_id` bigint not null,
  `task_code` varchar(31) not null,
  `day` int not null ,
  `number` bigint not null,
  `doubled` bit not null default 0,
  `user_id` bigint not null,
  `create_time` datetime default now(),
  primary key (`id`),
  -- 签到接口要查询最近一次签到时间以及连续签到天数
  -- select day, createTime from sign_in where userId='?' order by createTime desc limit 1
  key `USER_ID_INDEX` (`user_id`),
  key `USER_ID_CREATE_TIME_INDEX` (`user_id`, `create_time`),
  key `USER_ID_DAY_CREATE_TIME_INDEX` (`user_id`, `day`, `create_time`)
) ENGINE=InnoDB CHARSET=utf8;