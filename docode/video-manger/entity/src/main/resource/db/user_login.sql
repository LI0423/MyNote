CREATE TABLE `user_login` (
  `user_id` bigint(20) NOT NULL,
  `app_id` bigint(20) NOT NULL,
  `accumulated_login_days` int DEFAULT 0,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_login_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;