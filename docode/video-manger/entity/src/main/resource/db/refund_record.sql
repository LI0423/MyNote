CREATE TABLE `refund_record` (
      `id` bigint COMMENT '主键',
      `refund_order_id` bigint COMMENT '退款订单id',
      `result_status` int COMMENT '退款结果, 1-退款成功，2-退款失败',
      `third_error_code` varchar(255) COMMENT '第三方退款错误码',
      `third_error_msg` varchar(255) COMMENT '第三方退款错误信息',
      `event_type` varchar(255) COMMENT '事件类型',
      `summary` varchar(255) COMMENT '回调摘要',
      `result` text COLLATE utf8mb4_unicode_ci COMMENT '结果详情',
      `create_time` datetime not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `modify_time` datetime not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      PRIMARY KEY (`id`),
      KEY `REFUND_ORDER_ID_INDEX` (`refund_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '退款记录表';