create table `withdrawal` (
  `id` bigint not null,
  `app_id` bigint not null,
  `method` varchar(63),
  `create_time` datetime,
  `amount` bigint,
  `number` bigint,
  `status` int,
  `user_id` bigint,
  `we_chat_payment_no` varchar(127),
  `err_code` varchar(63),
  `err_desc` varchar(255),
  primary key(`id`),
  key `USER_ID_INDEX` (`user_id`)
) ENGINE=InnoDB CHARSET=utf8;

ALTER TABLE `withdrawal` ADD KEY `USER_ID_STATUS_INDEX` (user_id, status);