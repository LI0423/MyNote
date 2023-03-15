create table `google_user_auth` (
                             `id` bigint not null,
                             `app_id` bigint not null,
                             `user_id` bigint not null,
                             `google_user_id` varchar(127) not null,
                             `access_token` varchar(127),
                             `create_time` datetime not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `modify_time` datetime not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             primary key (`id`),
    -- "用户登录"接口，需要判断是不是新用户，判断方法是是否存在googleUserID对应的用户记录
                             unique `GOOGLE_USER_ID_TASK_CODE_INDEX` (`google_user_id`),
    -- 需要用户登录才能使用的接口，cache失效/丢失时，需要判断accessToken的合法性，是否存在accessToken对应的用户记录
    -- 需要用户登录才能使用的接口，cache失效/丢失时，需要判断accessToken查询对应的用户信息
                             key `ACCESS_TOKEN_INDEX` (`access_token`)
) ENGINE=InnoDB CHARSET=utf8;