CREATE TABLE `withdrawal_fund` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
                                   `app_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'app_id',
                                   `business_source` varchar(200) DEFAULT '' COMMENT '业务方',
                                   `business_id` varchar(200) DEFAULT '' COMMENT '业务id',
                                   `pay_order_id` varchar(200) DEFAULT '' COMMENT '支付订单id',
                                   `pay_order_fund_id` varchar(200) DEFAULT '' COMMENT '支付流水id',
                                   `pay_business_id` varchar(200) DEFAULT NULL COMMENT '支付交互业务id',
                                   `pay_status` int(11) DEFAULT NULL COMMENT '支付状态 1失败 0成功',
                                   `error_code` varchar(200) DEFAULT NULL COMMENT '错误码',
                                   `error_msg` varchar(200) DEFAULT NULL COMMENT '错误信息',
                                   `channel` int(11) DEFAULT NULL COMMENT '流水渠道 0微信 1支付宝',
                                   `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '钱',
                                   `pay_time` varchar(100) DEFAULT NULL COMMENT '支付时间',
                                   `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='提现流水表';