CREATE TABLE `user_clock_in` (
  `user_id` bigint(20) NOT NULL,
  `app_id` bigint(20) NOT NULL,
  `continued_clock_in_days` int DEFAULT 0,
  `accumulated_clock_in_days` int DEFAULT 0,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_clock_in_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;