CREATE TABLE `user_ext` (
  `user_id` bigint(20) NOT NULL,
  `app_id` bigint(20) NOT NULL,
  `level` int DEFAULT 0,
  `view_ad_count` bigint DEFAULT 0,
  `is_new` bit DEFAULT b'1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `user_ext` ADD COLUMN `ali_pay_account` varchar(255) COMMENT '支付宝账户电话号码';

ALTER TABLE `user_ext` ADD COLUMN `ali_pay_name` varchar(255) COMMENT '支付宝账户用户真实姓名';