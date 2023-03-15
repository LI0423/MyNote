create table `user` (
  `id` bigint not null,
  `name` varchar(127) CHARACTER SET utf8mb4,
  `sex` varchar(15),
  `avatar` varchar(255),
  `nick_name` varchar(127),
  `number` bigint default 0,
  `score` bigint default 0,
  `app_id` bigint not null,
  `withdrawal_times` datetime,
  `create_time` datetime default now(),
  `max_withdrawal_continuous_sign_in_days` bigint default 0,
  `current_withdrawal_continuous_sign_in_days` bigint default 0,
  primary key (`id`)
) ENGINE=InnoDB CHARSET=utf8;


ALTER TABLE `video_task`.`user` ADD COLUMN `tomorrow_number` bigint(20) NOT NULL DEFAULT 0 COMMENT '第二天可领金币';