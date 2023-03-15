CREATE TABLE `app_payment_switch_config` (
  `app_id` bigint(20) NOT NULL,
  `we_chat_withdrawal_switch` int DEFAULT 0 COMMENT '微信提现开关',
  `ali_withdrawal_switch` int DEFAULT 0 COMMENT '支付宝提现开关',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(127) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_by` varchar(127) DEFAULT NULL,
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8