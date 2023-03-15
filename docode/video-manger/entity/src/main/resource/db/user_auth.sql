create table `user_auth` (
  `id` bigint not null,
  `app_id` bigint not null,
  `user_id` bigint not null,
  `open_id` varchar(127) not null,
  `oauth_name` varchar(31),
  `third_party_access_token` varchar(127),
  `access_token` varchar(127),
  `token` varchar(127),
  `create_time` bigint,
  `last_modify_time` bigint,
  primary key (`id`),
  -- "用户登录"接口，需要判断是不是新用户，判断方法是是否存在openId对应的用户记录
  unique `OPEN_ID_TASK_CODE_INDEX` (`open_id`),
  -- 需要用户登录才能使用的接口，cache失效/丢失时，需要判断accessToken的合法性，是否存在accessToken对应的用户记录
  -- 需要用户登录才能使用的接口，cache失效/丢失时，需要判断accessToken查询对应的用户信息
  key `ACCESS_TOKEN_INDEX` (`access_token`)
) ENGINE=InnoDB CHARSET=utf8;