CREATE TABLE `pay_record` (
      `id` bigint COMMENT '主键',
      `pay_order_id` bigint COMMENT '支付订单id',
      `result_status` int COMMENT '支付结果, 1-支付成功，2-支付失败',
      `third_error_code` varchar(255) COMMENT '第三方支付错误码',
      `third_error_msg` varchar(255) COMMENT '第三方支付错误信息',
      `event_type` varchar(255) COMMENT '事件类型',
      `summary` varchar(255) COMMENT '回调摘要',
      `result` text COLLATE utf8mb4_unicode_ci COMMENT '结果详情',
      `create_time` datetime not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `modify_time` datetime not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      PRIMARY KEY (`id`),
      KEY `PAY_ORDER_ID_INDEX` (`pay_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '支付记录表';